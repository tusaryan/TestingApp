package com.tusaryan.TestingApp.controllers;

import com.tusaryan.TestingApp.TestContainerConfiguration;
import com.tusaryan.TestingApp.dto.EmployeeDto;
import com.tusaryan.TestingApp.entities.Employee;
import com.tusaryan.TestingApp.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;

//default timeout time = 5sec
@AutoConfigureWebTestClient(timeout = "100000")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestContainerConfiguration.class)
//since we are not using @DataJpaTest, so we don't need to configure the database, since
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmployeeControllerTestIT {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private EmployeeRepository employeeRepository;


    private Employee testEmployee;

    private EmployeeDto testEmployeeDto;

    @BeforeEach
    void setUp() {
        testEmployee = Employee.builder()
                .id(1L)
                .email("tusak@gmail.com")
                .name("Tus")
                .salary(200L)
                .build();
        testEmployeeDto = EmployeeDto.builder()
                .id(1L)
                .email("tusak@gmail.com")
                .name("Tus")
                .salary(200L)
                .build();
        //delete all the data from the database in every test case
        employeeRepository.deleteAll();
    }

    @Test
    void testGetEmployeeById_success() {
        //base path is defined by default i.e., localhost:8080
        Employee savedEmployee = employeeRepository.save(testEmployee);
        webTestClient.get()
                .uri("/employees/{id}", savedEmployee.getId())
                //to call the api and make api call
                .exchange()
                //response
                .expectStatus().isOk()
                //to convert expected response json data to EmployeeDto class type.
                .expectBody(EmployeeDto.class)
                //only use if want to check for equals and if you implemented equals and hashcode in method
                .isEqualTo(testEmployeeDto);

//                .value(employeeDto -> {
//                    //static import from Assertions.assertThat
//                    //we can also assert upon multiple things
//                    assertThat(employeeDto.getEmail()).isEqualTo(savedEmployee.getEmail());
//                    assertThat(employeeDto.getId()).isEqualTo(savedEmployee.getId());
//                });

    }

    @Test
    void testGetEmployeeById_Failure() {
        webTestClient.get()
                .uri("/employees/1")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testCreateNewEmployee_whenEmployeeAlreadyExists_thenThrowException() {
        //saving an employee
        Employee savedEmployee = employeeRepository.save(testEmployee);
        
        //making a post request to create an employee
        webTestClient.post()
                .uri("/employees")
                //try to another employee with the same email id
                .bodyValue(testEmployeeDto)
                //to make api call
                .exchange()
                //to check for InternalServerError
                .expectStatus().is5xxServerError();
    }

}