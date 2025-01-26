package com.tusaryan.TestingApp.controllers;

import com.tusaryan.TestingApp.TestContainerConfiguration;
import com.tusaryan.TestingApp.dto.EmployeeDto;
import com.tusaryan.TestingApp.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

//default timeout time = 5sec
@AutoConfigureWebTestClient(timeout = "100000")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestContainerConfiguration.class)
//since we are not using @DataJpaTest, so we don't need to configure the database, since
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AbstractIntegrationTest {

    @Autowired
    WebTestClient webTestClient;

    Employee testEmployee = Employee.builder()
            .id(1L)
                .email("tusak@gmail.com")
                .name("Tus")
                .salary(200L)
                .build();
    EmployeeDto testEmployeeDto = EmployeeDto.builder()
            .id(1L)
                .email("tusak@gmail.com")
                .name("Tus")
                .salary(200L)
                .build();
}
