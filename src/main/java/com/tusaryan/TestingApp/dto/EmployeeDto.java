package com.tusaryan.TestingApp.dto;


import lombok.*;

import java.util.Objects;

/*@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor*/
//@EqualsAndHashCode // this annotation is used to generate equals and hashcode methods, or alternatively we can override these methods manually
public class EmployeeDto {
    /*private Long id;
    private String email;
    private String name;
    private Long salary;

    *//*public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public Long getSalary() {
        return salary;
    }*//*

    //click on "Shift" two time and search for "equals" and "hashcode" and select the first option and add this
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeeDto that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getEmail(), that.getEmail()) && Objects.equals(getName(), that.getName()) && Objects.equals(getSalary(), that.getSalary());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail(), getName(), getSalary());
    }



    *//*@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeeDto that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getEmail(), that.getEmail()) && Objects.equals(getName(), that.getName()) && Objects.equals(getSalary(), that.getSalary());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail(), getName(), getSalary());
    }*//*

    */
}
