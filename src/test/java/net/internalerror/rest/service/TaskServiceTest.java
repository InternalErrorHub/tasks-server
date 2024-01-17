package net.internalerror.rest.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.internalerror.data.entity.User;
import net.internalerror.rest.request.task.CreateTaskRequest;
import net.internalerror.rest.request.task.DeleteTaskRequest;
import net.internalerror.rest.request.task.ReadAllTaskRequest;
import net.internalerror.rest.request.task.ReadDueTaskRequest;
import net.internalerror.rest.request.task.ReadTaskRequest;
import net.internalerror.rest.request.task.UpdateTaskRequest;
import net.internalerror.rest.response.task.CreateTaskResponse;
import net.internalerror.rest.response.task.DeleteTaskResponse;
import net.internalerror.rest.response.task.ReadAllTaskResponse;
import net.internalerror.rest.response.task.ReadDueTaskResponse;
import net.internalerror.rest.response.task.ReadTaskResponse;
import net.internalerror.rest.response.task.UpdateTaskResponse;
import net.internalerror.test.DataUtil;
import net.internalerror.test.ServiceTestBase;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;

class TaskServiceTest extends ServiceTestBase {

  @Autowired
  private DataUtil dataUtil;

  @RepeatedTest(TEST_RUNS)
  void create() {
    DataUtil.TestCredentials credentials = dataUtil.createTestCredentials();

    CreateTaskRequest createTaskRequest = new CreateTaskRequest();
    createTaskRequest.setName("Do the dishes");
    createTaskRequest.setDetails("Clean those forks brother");
    createTaskRequest.setDue(Instant.now());
    createTaskRequest.setToken(credentials.token());

    CreateTaskResponse createTaskResponse = taskService.create(createTaskRequest);
    assertNotNull(createTaskResponse);
    assertEquals(createTaskRequest.getName(), createTaskResponse.name());
    assertEqualsInstant(createTaskRequest.getDue(), createTaskResponse.due());
  }

  @RepeatedTest(TEST_RUNS)
  void read() {
    DataUtil.TestCredentials credentials = dataUtil.createTestCredentials();
    DataUtil.TestTask testTask = dataUtil.createTestTask(credentials);

    ReadTaskRequest readTaskRequest = new ReadTaskRequest();
    readTaskRequest.setName(testTask.name());
    readTaskRequest.setToken(credentials.token());

    ReadTaskResponse readTaskResponse = taskService.read(readTaskRequest);
    assertNotNull(readTaskResponse);
    assertEquals(testTask.name(), readTaskResponse.name());
    assertEquals(testTask.details(), readTaskResponse.details());
    assertEqualsInstant(testTask.due(), readTaskResponse.due());
  }

  @RepeatedTest(TEST_RUNS)
  void readAll() {
    DataUtil.TestCredentials credentials = dataUtil.createTestCredentials();
    List<DataUtil.TestTask> testTaskList = new ArrayList<>();
    for (int i = 0; i < 50; i++) {
      testTaskList.add(dataUtil.createTestTask(credentials));
    }

    ReadAllTaskRequest readAllTaskRequest = new ReadAllTaskRequest();
    readAllTaskRequest.setToken(credentials.token());

    ReadAllTaskResponse readAllTaskResponse = taskService.readAll(readAllTaskRequest);

    for (DataUtil.TestTask testTask : testTaskList) {
      boolean present = readAllTaskResponse.list().stream().anyMatch(taskInfo -> Objects.equals(testTask.name(), taskInfo.name()) && testTask.due().getEpochSecond() == taskInfo.due().getEpochSecond());
      assertTrue(present);
    }
  }

  @RepeatedTest(TEST_RUNS)
  void readDue() {
    DataUtil.TestCredentials credentials = dataUtil.createTestCredentials();

    for (int i = 0; i < 25; i++) {
      dataUtil.createTestTask(Instant.now().plus(100, ChronoUnit.DAYS), credentials);
    }
    for (int i = 0; i < 25; i++) {
      dataUtil.createTestTask(Instant.now().plus(2, ChronoUnit.DAYS), credentials);
    }

    ReadDueTaskRequest readDueTaskRequest = new ReadDueTaskRequest();
    readDueTaskRequest.setToken(credentials.token());
    readDueTaskRequest.setDueInfo(new ReadDueTaskRequest.DueInfo(ChronoUnit.DAYS, 5));

    ReadDueTaskResponse readDueTaskResponse = taskService.readDue(readDueTaskRequest);

    assertEquals(25, readDueTaskResponse.list().size());
    for (ReadAllTaskResponse.TaskInfo taskInfo : readDueTaskResponse.list()) {
      assertTrue(taskInfo.due().isBefore(Instant.now().plus(5, ChronoUnit.DAYS)));
    }
  }

  @RepeatedTest(TEST_RUNS)
  void update() {
    DataUtil.TestCredentials credentials = dataUtil.createTestCredentials();
    DataUtil.TestTask testTask = dataUtil.createTestTask(credentials);

    UpdateTaskRequest updateTaskRequest = new UpdateTaskRequest();
    updateTaskRequest.setName(testTask.name());
    updateTaskRequest.setNewName("Storytelling");
    updateTaskRequest.setDetails("Tell a story to the children");
    updateTaskRequest.setDue(Instant.now());
    updateTaskRequest.setToken(credentials.token());

    UpdateTaskResponse updateTaskResponse = taskService.update(updateTaskRequest);
    assertNotNull(updateTaskResponse);
    assertEquals(updateTaskResponse.due(), updateTaskRequest.getDue());
    assertEquals(updateTaskResponse.name(), updateTaskRequest.getNewName());
    assertEquals(updateTaskResponse.details(), updateTaskRequest.getDetails());
    //TODO Check db
  }

  @RepeatedTest(TEST_RUNS)
  void delete() {
    DataUtil.TestCredentials credentials = dataUtil.createTestCredentials();
    DataUtil.TestTask testTask = dataUtil.createTestTask(credentials);

    DeleteTaskRequest deleteTaskRequest = new DeleteTaskRequest();
    deleteTaskRequest.setName(testTask.name());
    deleteTaskRequest.setToken(credentials.token());

    DeleteTaskResponse deleteTaskResponse = taskService.delete(deleteTaskRequest);
    assertNotNull(deleteTaskResponse);
    assertEquals(deleteTaskRequest.getName(), deleteTaskResponse.name());

    User user = userRepository.findByEmail(credentials.email());
    assertFalse(taskRepository.existsByUserAndNameIgnoreCase(user, deleteTaskRequest.getName()));

  }

}
