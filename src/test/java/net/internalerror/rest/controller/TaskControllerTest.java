package net.internalerror.rest.controller;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import net.internalerror.rest.Messages;
import net.internalerror.rest.request.task.CreateTaskRequest;
import net.internalerror.rest.request.task.DeleteTaskRequest;
import net.internalerror.rest.request.task.ReadAllTaskRequest;
import net.internalerror.rest.request.task.ReadDueTaskRequest;
import net.internalerror.rest.request.task.ReadTaskRequest;
import net.internalerror.rest.request.task.UpdateTaskRequest;
import net.internalerror.test.ControllerTestBase;
import net.internalerror.test.DataUtil;
import org.junit.jupiter.api.RepeatedTest;

class TaskControllerTest extends ControllerTestBase {

  @RepeatedTest(TEST_RUNS)
  void create() {
    DataUtil.TestCredentials testCredentials = dataUtil.createTestCredentials();

    CreateTaskRequest createTaskRequest = new CreateTaskRequest();
    createTaskRequest.setName("Do the dishes");
    createTaskRequest.setDetails("Some shitty task");
    createTaskRequest.setDue(Instant.now());
    createTaskRequest.setToken(testCredentials.token());

    assertDoesNotThrow(() -> taskController.create(createTaskRequest));
  }

  @RepeatedTest(TEST_RUNS)
  void createNAME_IS_OCCUPIED() {
    DataUtil.TestCredentials testCredentials = dataUtil.createTestCredentials();

    CreateTaskRequest createTaskRequest = new CreateTaskRequest();
    createTaskRequest.setName("Do the dishes");
    createTaskRequest.setDetails("Some shitty task");
    createTaskRequest.setDue(Instant.now());
    createTaskRequest.setToken(testCredentials.token());

    assertDoesNotThrow(() -> taskController.create(createTaskRequest));
    assertThrowsValidationException(Messages.NAME_IS_OCCUPIED, () -> taskController.create(createTaskRequest));
  }

  @RepeatedTest(TEST_RUNS)
  void read() {
    DataUtil.TestCredentials testCredentials = dataUtil.createTestCredentials();
    DataUtil.TestTask testTask = dataUtil.createTestTask(testCredentials);

    ReadTaskRequest readTaskRequest = new ReadTaskRequest();
    readTaskRequest.setName(testTask.name());
    readTaskRequest.setToken(testCredentials.token());

    assertDoesNotThrow(() -> taskController.read(readTaskRequest));
  }

  @RepeatedTest(TEST_RUNS)
  void readTASK_DOES_NOT_EXIST() {
    DataUtil.TestCredentials testCredentials = dataUtil.createTestCredentials();

    ReadTaskRequest readTaskRequest = new ReadTaskRequest();
    readTaskRequest.setName("Some Random name");
    readTaskRequest.setToken(testCredentials.token());

    assertThrowsValidationException(Messages.TASK_DOES_NOT_EXIST, () -> taskController.read(readTaskRequest));
  }

  @RepeatedTest(TEST_RUNS)
  void readAll() {
    DataUtil.TestCredentials testCredentials = dataUtil.createTestCredentials();

    for (int i = 0; i < 50; i++) {
      dataUtil.createTestTask(testCredentials);
    }

    ReadAllTaskRequest readAllTaskRequest = new ReadAllTaskRequest();
    readAllTaskRequest.setToken(testCredentials.token());

    assertDoesNotThrow(() -> taskController.readAll(readAllTaskRequest));
  }

  @RepeatedTest(TEST_RUNS)
  void readDue() {
    DataUtil.TestCredentials testCredentials = dataUtil.createTestCredentials();

    for (int i = 0; i < 25; i++) {
      dataUtil.createTestTask(Instant.now().plus(100, ChronoUnit.DAYS), testCredentials);
    }
    for (int i = 0; i < 25; i++) {
      dataUtil.createTestTask(Instant.now().plus(2, ChronoUnit.DAYS), testCredentials);
    }

    ReadDueTaskRequest readDueTaskRequest = new ReadDueTaskRequest();
    readDueTaskRequest.setToken(testCredentials.token());
    readDueTaskRequest.setDueInfo(new ReadDueTaskRequest.DueInfo(ChronoUnit.DAYS, 2));

    assertDoesNotThrow(() -> taskController.readDue(readDueTaskRequest));
  }

  @RepeatedTest(TEST_RUNS)
  void update() {
    DataUtil.TestCredentials testCredentials = dataUtil.createTestCredentials();
    DataUtil.TestTask testTask = dataUtil.createTestTask(testCredentials);

    UpdateTaskRequest updateTaskRequest = new UpdateTaskRequest();
    updateTaskRequest.setName(testTask.name());
    updateTaskRequest.setNewName("Do the dishes");
    updateTaskRequest.setDetails("Some shitty task");
    updateTaskRequest.setDue(Instant.now());
    updateTaskRequest.setToken(testCredentials.token());

    assertDoesNotThrow(() -> taskController.update(updateTaskRequest));
  }

  @RepeatedTest(TEST_RUNS)
  void updateNAME_IS_OCCUPIED() {
    DataUtil.TestCredentials testCredentials = dataUtil.createTestCredentials();
    DataUtil.TestTask testTask1 = dataUtil.createTestTask(testCredentials);
    DataUtil.TestTask testTask2 = dataUtil.createTestTask(testCredentials);

    UpdateTaskRequest updateTaskRequest = new UpdateTaskRequest();
    updateTaskRequest.setName(testTask1.name());
    updateTaskRequest.setNewName(testTask2.name());
    updateTaskRequest.setDetails("Some shitty task");
    updateTaskRequest.setDue(Instant.now());
    updateTaskRequest.setToken(testCredentials.token());

    assertThrowsValidationException(Messages.NAME_IS_OCCUPIED, () -> taskController.update(updateTaskRequest));
  }

  @RepeatedTest(TEST_RUNS)
  void updateTASK_DOES_NOT_EXIST() {
    DataUtil.TestCredentials testCredentials = dataUtil.createTestCredentials();

    UpdateTaskRequest updateTaskRequest = new UpdateTaskRequest();
    updateTaskRequest.setName("Some random name");
    updateTaskRequest.setNewName("Do the dishes");
    updateTaskRequest.setDetails("Some shitty task");
    updateTaskRequest.setDue(Instant.now());
    updateTaskRequest.setToken(testCredentials.token());

    assertThrowsValidationException(Messages.TASK_DOES_NOT_EXIST, () -> taskController.update(updateTaskRequest));
  }

  @RepeatedTest(TEST_RUNS)
  void delete() {
    DataUtil.TestCredentials testCredentials = dataUtil.createTestCredentials();
    DataUtil.TestTask testTask = dataUtil.createTestTask(testCredentials);

    DeleteTaskRequest deleteTaskRequest = new DeleteTaskRequest();
    deleteTaskRequest.setName(testTask.name());
    deleteTaskRequest.setToken(testCredentials.token());

    assertDoesNotThrow(() -> taskController.delete(deleteTaskRequest));
  }

  @RepeatedTest(TEST_RUNS)
  void deleteTASK_DOES_NOT_EXIST() {
    DataUtil.TestCredentials testCredentials = dataUtil.createTestCredentials();
    DataUtil.TestTask testTask = dataUtil.createTestTask(testCredentials);

    DeleteTaskRequest deleteTaskRequest = new DeleteTaskRequest();
    deleteTaskRequest.setName(testTask.name());
    deleteTaskRequest.setToken(testCredentials.token());

    assertDoesNotThrow(() -> taskController.delete(deleteTaskRequest));
    assertThrowsValidationException(Messages.TASK_DOES_NOT_EXIST, () -> taskController.delete(deleteTaskRequest));
  }

}