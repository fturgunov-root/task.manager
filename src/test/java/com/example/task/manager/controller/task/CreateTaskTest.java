package com.example.task.manager.controller.task;

import com.example.task.manager.CommonIntegrationTest;
import com.example.task.manager.controller.dto.TaskResponse;
import com.example.task.manager.persistence.dao.TaskRepository;
import com.example.task.manager.persistence.model.Task;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;

import java.util.stream.Stream;

import static com.example.task.manager.TestDataHelperTask.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Create a new task ( POST /api/public/task )")
class CreateTaskTest extends CommonIntegrationTest {

    @Autowired
    TaskRepository taskRepository;

    @Test
    @DisplayName("Should create a task")
    void shouldCreateTask() throws Exception {
        //GIVEN
        RequestBuilder request = testDataHelperTask.createTaskRequest();
        //WHEN
        ResultActions resultActions = mockMvc.perform(request);
        //THEN
        String jsonResponse = resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.title").value(TEST_TITLE))
                .andExpect(jsonPath("$.description").value(TEST_DESCRIPTION))
                .andExpect(jsonPath("$.finished").value(TEST_FINISHED))
                .andReturn().getResponse().getContentAsString();

        TaskResponse response = jsonConverter.convertFromString(jsonResponse, TaskResponse.class);
        Task task = taskRepository.findById(response.getId()).orElseThrow(Exception::new);
        assertThat(task.getTitle()).isEqualTo(TEST_TITLE);
        assertThat(task.getDescription()).isEqualTo(TEST_DESCRIPTION);
        assertThat(task.getFinished()).isEqualTo(TEST_FINISHED);
    }

    @DisplayName("Should fail with 400 code when title is empty or null")
    @ParameterizedTest(name = "title is {0}")
    @MethodSource({"invalidRequestDataForRequiredParameters"})
    void shouldReturn400WhenTitleEmptyOrNull(String title) throws Exception {
        //GIVEN
        RequestBuilder request = testDataHelperTask.createTaskRequest(payload -> {
            payload.put("title", title);
            payload.put("description", "test description");
        });
        //WHEN
        ResultActions resultActions = mockMvc.perform(request);
        //THEN
        resultActions.andExpect(status().isBadRequest());
    }


    private static Stream<Arguments> invalidRequestDataForRequiredParameters() {
        return Stream.of(                Arguments.of( (String) null),
                Arguments.of(Named.of("empty", StringUtils.EMPTY))
        );
    }

}
