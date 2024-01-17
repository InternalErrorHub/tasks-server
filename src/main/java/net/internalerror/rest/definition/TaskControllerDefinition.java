package net.internalerror.rest.definition;

import jakarta.validation.Valid;
import net.internalerror.rest.Routes;
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
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface TaskControllerDefinition {

  @PostMapping(name = "Create task", value = Routes.TASK_CREATE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  CreateTaskResponse create(@RequestBody @Valid CreateTaskRequest request);

  @PostMapping(name = "Read task", value = Routes.TASK_READ, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  ReadTaskResponse read(@RequestBody @Valid ReadTaskRequest request);

  @PostMapping(name = "Read all task", value = Routes.TASK_READ_ALL, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  ReadAllTaskResponse readAll(@RequestBody @Valid ReadAllTaskRequest request);

  @PostMapping(name = "Read due tasks", value = Routes.TASK_READ_DUE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  ReadDueTaskResponse readDue(@RequestBody @Valid ReadDueTaskRequest request);

  @PostMapping(name = "Update task", value = Routes.TASK_UPDATE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  UpdateTaskResponse update(@RequestBody @Valid UpdateTaskRequest request);

  @PostMapping(name = "Delete task", value = Routes.TASK_DELETE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  DeleteTaskResponse delete(@RequestBody @Valid DeleteTaskRequest request);

}
