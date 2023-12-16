package com.sharad.springboot_webflux.mapper;

import com.sharad.springboot_webflux.dto.EmployeeDto;
import com.sharad.springboot_webflux.entity.Employee;

public class EmployeeMapper {
    // map employee to employeedto

    public  static EmployeeDto mapToEmployeeDto(Employee employee){
        return new EmployeeDto(employee.getId(),
                                employee.getFirstName(),
                                employee.getLastName(),
                                employee.getEmail()

        );
    }

    // map employee dto to employee
    public static Employee mapToEmployee(EmployeeDto employeeDto){
        return  new Employee(
                employeeDto.getId(),
                employeeDto.getFirstName(),
                employeeDto.getLastName(),
                employeeDto.getEmail()
        );
    }
}
