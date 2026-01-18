package com.example.task.manager.controller.converters;

import com.example.task.manager.controller.dto.TaskCreateRequest;
import com.example.task.manager.controller.dto.TaskResponse;
import com.example.task.manager.persistence.model.Task;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class TaskConverter {

    public static Task convertToEntity(TaskCreateRequest request) {
        return Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .finished(request.getFinished())
                .build();
    }

    public static TaskResponse convertToResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .finished(task.getFinished())
                .build();
    }

    public static List<TaskResponse> from(List<Task> taskList) {
        return taskList.stream()
                .map(TaskConverter::convertToResponse)
                .toList();
    }

}
