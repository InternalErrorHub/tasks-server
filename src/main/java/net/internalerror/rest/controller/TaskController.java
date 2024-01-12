package net.internalerror.rest.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.internalerror.data.entity.User;
import net.internalerror.data.repository.TaskRepository;
import net.internalerror.rest.Messages;
import net.internalerror.rest.definition.TaskControllerDefinition;
import net.internalerror.rest.exception.ValidationException;
import net.internalerror.rest.request.task.*;
import net.internalerror.rest.response.task.*;
import net.internalerror.rest.security.SecurityService;
import net.internalerror.rest.service.TaskService;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class TaskController implements TaskControllerDefinition {

    private final TaskService taskService;

    private final SecurityService securityService;

    private final TaskRepository taskRepository;

    @Override
    public CreateTaskResponse create(CreateTaskRequest request) {
        securityService.checkToken(request);
        User user = securityService.getUser(request);
        if (taskRepository.existsByUserAndNameIgnoreCase(user, request.getName())) {
            throw new ValidationException(Messages.NAME_IS_OCCUPIED);
        }
        return taskService.create(request);
    }

    @Override
    public ReadTaskResponse read(ReadTaskRequest request) {
        securityService.checkToken(request);
        User user = securityService.getUser(request);
        if (!taskRepository.existsByUserAndNameIgnoreCase(user, request.getName())) {
            throw new ValidationException(Messages.TASK_DOES_NOT_EXIST);
        }
        return taskService.read(request);
    }

    @Override
    public ReadAllTaskResponse readAll(ReadAllTaskRequest request) {
        securityService.checkToken(request);
        return taskService.readAll(request);
    }

    @Override
    public ReadDueTaskResponse readDue(ReadDueTaskRequest request) {
        securityService.checkToken(request);
        return taskService.readDue(request);
    }

    @Override
    public UpdateTaskResponse update(UpdateTaskRequest request) {
        securityService.checkToken(request);
        User user = securityService.getUser(request);
        if (!taskRepository.existsByUserAndNameIgnoreCase(user, request.getName())) {
            throw new ValidationException(Messages.TASK_DOES_NOT_EXIST);
        }
        if (taskRepository.existsByUserAndNameIgnoreCase(user, request.getNewName())) {
            throw new ValidationException(Messages.NAME_IS_OCCUPIED);
        }
        return taskService.update(request);
    }

    @Override
    public DeleteTaskResponse delete(DeleteTaskRequest request) {
        securityService.checkToken(request);
        User user = securityService.getUser(request);
        if (!taskRepository.existsByUserAndNameIgnoreCase(user, request.getName())) {
            throw new ValidationException(Messages.TASK_DOES_NOT_EXIST);
        }
        return taskService.delete(request);
    }

}
