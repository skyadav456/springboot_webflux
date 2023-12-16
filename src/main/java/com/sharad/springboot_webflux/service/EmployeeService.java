package com.sharad.springboot_webflux.service;

import com.sharad.springboot_webflux.dto.EmployeeDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EmployeeService {
    Mono<EmployeeDto> saveEmployee(EmployeeDto employeeDto);
    Mono<EmployeeDto> getEmployee(String id);
    Flux<EmployeeDto> getAllEmployee();

    Mono<EmployeeDto> updateEmployee(EmployeeDto employeeDto,String id);

    Mono<Void> deleteById(String id);
    Mono<Void> deleteAll();
}
