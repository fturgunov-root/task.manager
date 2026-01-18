package com.example.task.manager;

import com.example.task.manager.common.JsonConverter;
import com.example.task.manager.controller.dto.TaskResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.Nullable;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Component
@Profile("test")
@RequiredArgsConstructor
public class TestDataHelperTask {
    public static final String TEST_TITLE = "test title";
    public static final String TEST_DESCRIPTION = "test_description";
    public static final Boolean TEST_FINISHED = false;

    private static final String BASE_PATH = "/api/public/task";

    private final JsonConverter jsonConverter;
    private final MockMvc mockMvc;

    public RequestBuilder createTaskRequest(@Nullable Consumer<Map<String, Object>> payloadModifier) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("title", TEST_TITLE);
        payload.put("description", TEST_DESCRIPTION);
        payload.put("finished", TEST_FINISHED);
        if (payloadModifier != null) {
            payloadModifier.accept(payload);
        }
        return post(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonConverter.convertToString(payload));
    }

    public RequestBuilder createTaskRequest() {
        return createTaskRequest(null);
    }

    @SneakyThrows
    public TaskResponse createTask(@Nullable Consumer<Map<String, Object>> payloadModifier) {
        RequestBuilder request = createTaskRequest(payloadModifier);
        String jsonResponse = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse().getContentAsString();
        return jsonConverter.convertFromString(jsonResponse, TaskResponse.class);
    }

    public TaskResponse createTask() {
        return createTask(null);
    }

    public RequestBuilder listTasks() {
        return get(BASE_PATH);
    }

}
