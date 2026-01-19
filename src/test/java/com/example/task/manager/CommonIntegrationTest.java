package com.example.task.manager;

import com.example.task.manager.common.JsonConverter;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public abstract class CommonIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected JsonConverter jsonConverter;

    @Autowired
    protected TestDataHelperTask testDataHelperTask;

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @BeforeEach
    void testDataCleanUp() {
        // H2-friendly truncate
        jdbcTemplate.execute("DELETE FROM task_item");
    }
}
