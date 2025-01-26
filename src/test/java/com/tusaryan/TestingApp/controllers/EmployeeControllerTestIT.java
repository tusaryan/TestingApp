package com.tusaryan.TestingApp.controllers;

import com.tusaryan.TestingApp.dto.EmployeeDto;
import com.tusaryan.TestingApp.entities.Employee;
import com.tusaryan.TestingApp.repositories.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;


class EmployeeControllerTestIT extends AbstractIntegrationTest{

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {
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
                //here jackson is working to convert expected response json data to EmployeeDto class type.
//                .expectBody(EmployeeDto.class)
                //only use if want to check for equals and if you implemented equals and hashcode in method
//                .isEqualTo(testEmployeeDto);

                //to fix the error of using different id of employees-> testEmployeeDto and savedEmployee,
                //by directly check the response data with actual data that we saved earlier (savedEmployee).
                .expectBody()
                .jsonPath("$.id").isEqualTo(savedEmployee.getId())
                .jsonPath("$.email").isEqualTo(savedEmployee.getEmail())
                .jsonPath("$.name").isEqualTo(savedEmployee.getName());

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

    @Test
    void testCreateNewEmployee_whenEmployeeDoesNotExists_thenCreateEmployee() {
        //making a post-request to create an employee
        webTestClient.post()
                .uri("/employees")
                .bodyValue(testEmployeeDto)
                .exchange()
                .expectStatus().isCreated()
                //.expectBody(EmployeeDto.class)

                //jackson will not be used since we'll not be converting json data to (POJO) Java Class
                //this will return something
                .expectBody()
                //testing upon raw serialized data/json data
                //since we're creating a user so we cannot check the id as it is assigned by db
                .jsonPath("$.email").isEqualTo(testEmployeeDto.getEmail())
                .jsonPath("$.name").isEqualTo(testEmployeeDto.getName());

    }
    @Test
    void testUpdateEmployee_whenEmployeeDoesNotExist_thenThrowException() {
        //Since we already want that employee does not exist, So no need to save the employee
        webTestClient.put()
                //intentionally used random id that we know does not exist
                .uri("employees/999")
                .bodyValue(testEmployeeDto)
                .exchange()
                //we're getting not found as a result because function throws ResourceNotFound Exception,
                // which is handled by GlobalExceptionHandler, and this handler returns this error
                // with response status as notFound.
                .expectStatus().isNotFound();
    }

    //make sure to save an employee to update its email
    @Test
    void testUpdateEmployee_whenAttemptingToUpdateTheEmail_thenThrowException() {
        Employee savedEmployee = employeeRepository.save(testEmployee);
        testEmployeeDto.setName("Tunu");
        testEmployeeDto.setEmail("tunuak@gmail.com");

        webTestClient.put()
                .uri("employees/{id}", savedEmployee.getId())
                .bodyValue(testEmployeeDto)
                .exchange()
                //to check if we're getting RuntimeException
                .expectStatus().is5xxServerError();
    }

    @Test
    void testUpdateEmployee_whenEmployeeIsValid_thenUpdateEmployee () {
        Employee savedEmployee = employeeRepository.save(testEmployee);
        testEmployeeDto.setName("Tunu");
        testEmployeeDto.setSalary(250L);

        webTestClient.put()
                .uri("employees/{id}", savedEmployee.getId())
                .bodyValue(testEmployeeDto)
                .exchange()
                //to check if we're getting RuntimeException
                .expectStatus().isOk()
                .expectBody(EmployeeDto.class)
                .isEqualTo(testEmployeeDto);
    }

    @Test
    void testDeleteEmployee_whenEmployeeDoesNotExists_thenThrowException() {
        //not saving any employee since we want this behaviour, i.e., this employee does not exist for deletion
        webTestClient.delete()
                .uri("employees/1")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testDeleteEmployee_whenEmployeeExists_thenDeleteEmployee() {
        Employee savedEmployee = employeeRepository.save(testEmployee);

        webTestClient.delete()
                .uri("employees/{id}", savedEmployee.getId())
                .exchange()
                .expectStatus().isNoContent()
                //since return/response type of method in EmployeeController is Void
                .expectBody(Void.class);

        //if we run the same method again and if it get an exception,
        // then that would mean that we have successfully deleted the employee from the DB
        // and the test case is working correctly
        webTestClient.delete()
                .uri("employees/{id}", savedEmployee.getId())
                .exchange()
                .expectStatus().isNotFound();
    }

}