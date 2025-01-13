package com.tusaryan.TestingApp.services;

import com.tusaryan.TestingApp.dto.EmployeeDto;

//L7.4

public interface EmployeeService {

    //CRUD related methods
    EmployeeDto getEmployeeById(Long id);
    EmployeeDto createNewEmployee(EmployeeDto employeeDto);
    EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto);
    void deleteEmployee(Long id);
}
