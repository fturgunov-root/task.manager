package com.example.task.manager.controller.task;

import com.example.task.manager.CommonIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Return list of tasks ( GET /api/public/task )")
class ListAllTasksTest extends CommonIntegrationTest {

    @Test
    @DisplayName("Should list tasks")
    void shouldListTasks() throws Exception {
        //GIVEN
        testDataHelperTask.createTask(payload -> payload.put("title", "name1"));
        testDataHelperTask.createTask(payload -> payload.put("createTask", "name2"));
        RequestBuilder request = testDataHelperTask.listTasks();
        //WHEN
        ResultActions resultActions = mockMvc.perform(request);
        //THEN
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)));
    }

}
