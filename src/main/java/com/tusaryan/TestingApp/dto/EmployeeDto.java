package com.tusaryan.TestingApp.dto;


import lombok.*;

//L7.4

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    private Long id;
    private String email;
    private String name;
    private Long salary;
}
