package com.example.task.manager.controller.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TaskResponse {

    private int id;
    private String title;
    private String description;
    private Boolean finished;

}
