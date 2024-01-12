package net.internalerror.rest.controller;

import net.internalerror.rest.Messages;
import net.internalerror.rest.request.task.*;
import net.internalerror.test.ControllerTestBase;
import net.internalerror.test.DataUtil;
import org.junit.jupiter.api.RepeatedTest;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

class TaskControllerTest extends ControllerTestBase {

    @RepeatedTest(TEST_RUNS)
    void create() {
        DataUtil.TestCredentials testCredentials = getDataUtil().createTestCredentials();

        CreateTaskRequest createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setName("Do the dishes");
        createTaskRequest.setDetails("Some shitty task");
        createTaskRequest.setDue(Instant.now());
        createTaskRequest.setToken(testCredentials.token());

        assertDoesNotThrow(() -> getTaskController().create(createTaskRequest));
    }

    @RepeatedTest(TEST_RUNS)
    void createNAME_IS_OCCUPIED() {
        DataUtil.TestCredentials testCredentials = getDataUtil().createTestCredentials();

        CreateTaskRequest createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setName("Do the dishes");
        createTaskRequest.setDetails("Some shitty task");
        createTaskRequest.setDue(Instant.now());
        createTaskRequest.setToken(testCredentials.token());

        assertDoesNotThrow(() -> getTaskController().create(createTaskRequest));
        assertThrowsValidationException(Messages.NAME_IS_OCCUPIED, () -> getTaskController().create(createTaskRequest));
    }

    @RepeatedTest(TEST_RUNS)
    void read() {
        DataUtil.TestCredentials testCredentials = getDataUtil().createTestCredentials();
        DataUtil.TestTask testTask = getDataUtil().createTestTask(testCredentials);

        ReadTaskRequest readTaskRequest = new ReadTaskRequest();
        readTaskRequest.setName(testTask.name());
        readTaskRequest.setToken(testCredentials.token());

        assertDoesNotThrow(() -> getTaskController().read(readTaskRequest));
    }

    @RepeatedTest(TEST_RUNS)
    void readTASK_DOES_NOT_EXIST() {
        DataUtil.TestCredentials testCredentials = getDataUtil().createTestCredentials();

        ReadTaskRequest readTaskRequest = new ReadTaskRequest();
        readTaskRequest.setName("Some Random name");
        readTaskRequest.setToken(testCredentials.token());

        assertThrowsValidationException(Messages.TASK_DOES_NOT_EXIST, () -> getTaskController().read(readTaskRequest));
    }

    @RepeatedTest(TEST_RUNS)
    void readAll() {
        DataUtil.TestCredentials testCredentials = getDataUtil().createTestCredentials();

        for (int i = 0; i < 50; i++) {
            getDataUtil().createTestTask(testCredentials);
        }

        ReadAllTaskRequest readAllTaskRequest = new ReadAllTaskRequest();
        readAllTaskRequest.setToken(testCredentials.token());

        assertDoesNotThrow(() -> getTaskController().readAll(readAllTaskRequest));
    }

    @RepeatedTest(TEST_RUNS)
    void readDue() {
        DataUtil.TestCredentials testCredentials = getDataUtil().createTestCredentials();

        for (int i = 0; i < 25; i++) {
            getDataUtil().createTestTask(Instant.now().plus(100, ChronoUnit.DAYS), testCredentials);
        }
        for (int i = 0; i < 25; i++) {
            getDataUtil().createTestTask(Instant.now().plus(2, ChronoUnit.DAYS), testCredentials);
        }

        ReadDueTaskRequest readDueTaskRequest = new ReadDueTaskRequest();
        readDueTaskRequest.setToken(testCredentials.token());
        readDueTaskRequest.setDueInfo(new ReadDueTaskRequest.DueInfo(ChronoUnit.DAYS, 2));

        assertDoesNotThrow(() -> getTaskController().readDue(readDueTaskRequest));
    }

    @RepeatedTest(TEST_RUNS)
    void update() {
        DataUtil.TestCredentials testCredentials = getDataUtil().createTestCredentials();
        DataUtil.TestTask testTask = getDataUtil().createTestTask(testCredentials);

        UpdateTaskRequest updateTaskRequest = new UpdateTaskRequest();
        updateTaskRequest.setName(testTask.name());
        updateTaskRequest.setNewName("Do the dishes");
        updateTaskRequest.setDetails("Some shitty task");
        updateTaskRequest.setDue(Instant.now());
        updateTaskRequest.setToken(testCredentials.token());

        assertDoesNotThrow(() -> getTaskController().update(updateTaskRequest));
    }

    @RepeatedTest(TEST_RUNS)
    void updateNAME_IS_OCCUPIED() {
        DataUtil.TestCredentials testCredentials = getDataUtil().createTestCredentials();
        DataUtil.TestTask testTask1 = getDataUtil().createTestTask(testCredentials);
        DataUtil.TestTask testTask2 = getDataUtil().createTestTask(testCredentials);

        UpdateTaskRequest updateTaskRequest = new UpdateTaskRequest();
        updateTaskRequest.setName(testTask1.name());
        updateTaskRequest.setNewName(testTask2.name());
        updateTaskRequest.setDetails("Some shitty task");
        updateTaskRequest.setDue(Instant.now());
        updateTaskRequest.setToken(testCredentials.token());

        assertThrowsValidationException(Messages.NAME_IS_OCCUPIED, () -> getTaskController().update(updateTaskRequest));
    }

    @RepeatedTest(TEST_RUNS)
    void updateTASK_DOES_NOT_EXIST() {
        DataUtil.TestCredentials testCredentials = getDataUtil().createTestCredentials();

        UpdateTaskRequest updateTaskRequest = new UpdateTaskRequest();
        updateTaskRequest.setName("Some random name");
        updateTaskRequest.setNewName("Do the dishes");
        updateTaskRequest.setDetails("Some shitty task");
        updateTaskRequest.setDue(Instant.now());
        updateTaskRequest.setToken(testCredentials.token());

        assertThrowsValidationException(Messages.TASK_DOES_NOT_EXIST, () -> getTaskController().update(updateTaskRequest));
    }

    @RepeatedTest(TEST_RUNS)
    void delete() {
        DataUtil.TestCredentials testCredentials = getDataUtil().createTestCredentials();
        DataUtil.TestTask testTask = getDataUtil().createTestTask(testCredentials);

        DeleteTaskRequest deleteTaskRequest = new DeleteTaskRequest();
        deleteTaskRequest.setName(testTask.name());
        deleteTaskRequest.setToken(testCredentials.token());

        assertDoesNotThrow(() -> getTaskController().delete(deleteTaskRequest));
    }

    @RepeatedTest(TEST_RUNS)
    void deleteTASK_DOES_NOT_EXIST() {
        DataUtil.TestCredentials testCredentials = getDataUtil().createTestCredentials();
        DataUtil.TestTask testTask = getDataUtil().createTestTask(testCredentials);

        DeleteTaskRequest deleteTaskRequest = new DeleteTaskRequest();
        deleteTaskRequest.setName(testTask.name());
        deleteTaskRequest.setToken(testCredentials.token());

        assertDoesNotThrow(() -> getTaskController().delete(deleteTaskRequest));
        assertThrowsValidationException(Messages.TASK_DOES_NOT_EXIST, () -> getTaskController().delete(deleteTaskRequest));
    }

}