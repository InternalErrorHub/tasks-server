package net.internalerror.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.internalerror.rest.request.RequestBase;
import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public abstract class RequestTestBase<R extends RequestBase> extends TestBase {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  static {
    objectMapper.registerModule(new JavaTimeModule());
  }

  private final Map<String, BiConsumer<R, Object>> setters = setters();

  @Autowired
  private MockMvc mockMvc;

  protected abstract Class<R> getRequestClass();

  protected abstract String getRoute();

  @TestFactory
  List<DynamicTest> generatedTests() throws JsonProcessingException {
    List<DynamicTest> tests = new ArrayList<>();
    List<RequestTest> requestTests = getRequestTests();
    MockHttpServletRequestBuilder requestBuilder = post("http://localhost:8080/" + getRoute());

    for (int i = 1; i < TEST_RUNS + 1; i++) {
      for (RequestTest requestTest : requestTests) {
        String json = objectMapper.writeValueAsString(requestTest.request);
        log.debug("Sending request: {}, expecting: {}", json, requestTest.expectedMessage);

        DynamicTest test = DynamicTest.dynamicTest(String.format("repetition %s of %s: %s", i, TEST_RUNS, requestTest.expectedMessage), () -> mockMvc.perform(requestBuilder.content(json).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest()).andDo(print()).andExpect(content().string(new StringContains(requestTest.expectedMessage))));
        tests.add(test);
      }
    }

    return tests;
  }

  protected Map<String, BiConsumer<R, Object>> setters() {
    return setters(new HashMap<>());
  }

  protected abstract Map<String, BiConsumer<R, Object>> setters(Map<String, BiConsumer<R, Object>> setters);

  protected R createRequestWith(String fieldName, Object value) {
    BiConsumer<R, Object> setter = setters.get(fieldName);
    R request = createDefaultRequest();
    setter.accept(request, value);
    return request;
  }

  protected R createDefaultRequest() {
    Constructor<R> constructor = assertDoesNotThrow(() -> getRequestClass().getDeclaredConstructor(), "NoArgsConstructor not found");
    return assertDoesNotThrow(() -> constructor.newInstance(), "Could not instantiate request");
  }

  protected List<RequestTest> getRequestTests() {
    return getRequestTests(new ArrayList<>());
  }

  protected abstract List<RequestTest> getRequestTests(List<RequestTest> requestTests);

  protected final RequestTest expect(R request, String expectedMessage) {
    return new RequestTest(request, expectedMessage);
  }

  @AllArgsConstructor
  protected class RequestTest {

    protected R request;

    protected String expectedMessage;

  }

}
