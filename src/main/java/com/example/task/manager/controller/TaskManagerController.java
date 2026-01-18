package com.example.task.manager.controller;

import com.example.task.manager.service.TaskService;
import com.example.task.manager.controller.converters.TaskConverter;
import com.example.task.manager.controller.dto.TaskCreateRequest;
import com.example.task.manager.controller.dto.TaskResponse;
import com.example.task.manager.persistence.model.Task;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Task manager")
@AllArgsConstructor
@RestController
@RequestMapping("/api/public/task")
public class TaskManagerController {

    private final TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creates a new task")
    public TaskResponse createTask(@RequestBody TaskCreateRequest request) {
        Task task = taskService.createTask(TaskConverter.convertToEntity(request));
        return TaskConverter.convertToResponse(task);
    }

    @GetMapping
    @Operation(summary = "Return list of tasks")
    public List<TaskResponse> listRestaurants() {
        List<Task> taskList = taskService.getAllTasks();
        return TaskConverter.from(taskList);
    }

}
