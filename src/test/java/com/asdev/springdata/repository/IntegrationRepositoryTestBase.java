package com.asdev.springdata.repository;

import com.asdev.springdata.initializer.Postgres;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@Sql("/sql/repository_data.sql")
@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(initializers = {
        Postgres.Initializer.class
})
@Transactional
public abstract class IntegrationRepositoryTestBase {

    @BeforeAll
    static void init() {
        Postgres.container.start();
    }
}
