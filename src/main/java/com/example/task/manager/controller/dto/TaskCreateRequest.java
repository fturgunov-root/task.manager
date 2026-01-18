package com.example.task.manager.controller.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskCreateRequest {

    @Size(min = 1, max = 255, message = "The field 'name' should not be empty or exceed 255 symbols in length")
    private String title;
    private String description;
    private Boolean finished;

}
