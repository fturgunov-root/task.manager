package com.example.task.manager.controller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskCreateRequest {

    @NotNull(message = "The field 'title' should not be empty")
    @Size(min = 1, max = 255, message = "The field 'title' should not be empty or exceed 255 symbols in length")
    private String title;
    private String description;
    private Boolean finished;

}
