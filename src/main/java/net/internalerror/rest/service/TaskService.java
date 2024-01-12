package net.internalerror.rest.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.internalerror.data.entity.Task;
import net.internalerror.data.entity.User;
import net.internalerror.data.repository.TaskRepository;
import net.internalerror.rest.definition.TaskControllerDefinition;
import net.internalerror.rest.request.task.*;
import net.internalerror.rest.response.task.*;
import net.internalerror.rest.security.SecurityService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService implements TaskControllerDefinition {

    private final SecurityService securityService;

    private final TaskRepository taskRepository;

    @Override
    public CreateTaskResponse create(CreateTaskRequest request) {
        User user = securityService.getUser(request);

        Task task = new Task();
        task.setUser(user);
        task.setName(request.getName());
        task.setDetails(request.getDetails());
        task.setDue(request.getDue());

        taskRepository.save(task);

        return new CreateTaskResponse(task.getName(), task.getDue());
    }

    @Override
    public ReadTaskResponse read(ReadTaskRequest request) {
        User user = securityService.getUser(request);
        Task task = taskRepository.findByUserAndNameIgnoreCase(user, request.getName());

        return new ReadTaskResponse(task.getName(), task.getDetails(), task.getDue());
    }

    @Override
    public ReadAllTaskResponse readAll(ReadAllTaskRequest request) {
        User user = securityService.getUser(request);
        List<Task> tasks = taskRepository.findByUser(user);
        List<ReadAllTaskResponse.TaskInfo> info = tasks.stream().map(task -> new ReadAllTaskResponse.TaskInfo(task.getName(), task.getDue())).toList();

        return new ReadAllTaskResponse(info);
    }

    @Override
    public ReadDueTaskResponse readDue(ReadDueTaskRequest request) {
        User user = securityService.getUser(request);
        List<Task> tasks = taskRepository.findByUser(user);
        List<Task> dueTasks = tasks.stream().filter(task -> task.getDue().isBefore(Instant.now().plus(request.getDueInfo().amount(), request.getDueInfo().unit()))).toList();
        List<ReadAllTaskResponse.TaskInfo> info = dueTasks.stream().map(task -> new ReadAllTaskResponse.TaskInfo(task.getName(), task.getDue())).toList();
        return new ReadDueTaskResponse(info);
    }

    @Override
    public UpdateTaskResponse update(UpdateTaskRequest request) {
        User user = securityService.getUser(request);
        Task task = taskRepository.findByUserAndNameIgnoreCase(user, request.getName());
        task.setName(request.getNewName());
        task.setDue(request.getDue());
        task.setDetails(request.getDetails());

        return new UpdateTaskResponse(task.getName(), task.getDetails(), task.getDue());
    }

    @Override
    public DeleteTaskResponse delete(DeleteTaskRequest request) {
        User user = securityService.getUser(request);
        Task task = taskRepository.findByUserAndNameIgnoreCase(user, request.getName());

        taskRepository.delete(task);
        return new DeleteTaskResponse(task.getName());
    }

}
