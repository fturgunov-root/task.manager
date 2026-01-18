package com.example.task.manager;


import com.example.task.manager.common.JsonConverter;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.lifecycle.Startables;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public abstract class CommonIntegrationTest {
    private static final String DB_IMAGE_NAME = "postgres:14.11";
    public static final PostgreSQLContainer<?> postgreSQLContainer;

    static {
        postgreSQLContainer = (PostgreSQLContainer<?>) new PostgreSQLContainer(DB_IMAGE_NAME)
                .withDatabaseName("integration-tests-db")
                .withUsername("sa")
                .withPassword("sa")
                .withReuse(true);

        Startables.deepStart(postgreSQLContainer).join();
    }

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected JsonConverter jsonConverter;
    @Autowired
    protected TestDataHelperTask testDataHelperTask;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @DynamicPropertySource
    static void setupProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.driver-class-name", postgreSQLContainer::getDriverClassName);
    }

    @BeforeEach
    void testDataCleanUp() {
        jdbcTemplate.execute("TRUNCATE TABLE task_item");
    }
}
