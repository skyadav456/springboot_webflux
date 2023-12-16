package com.sharad.springboot_webflux.service.impl;

import com.sharad.springboot_webflux.dto.EmployeeDto;
import com.sharad.springboot_webflux.entity.Employee;
import com.sharad.springboot_webflux.mapper.EmployeeMapper;
import com.sharad.springboot_webflux.repository.EmployeeRepository;
import com.sharad.springboot_webflux.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepository employeeRepository;


    @Override
    public Mono<EmployeeDto> saveEmployee(EmployeeDto employeeDto) {
        // convert employeeDto to employee entity
        Employee employee = EmployeeMapper.mapToEmployee(employeeDto);
        Mono<Employee> savedEmployee = employeeRepository.save(employee);

        return savedEmployee.map((employeeEntity)->EmployeeMapper.mapToEmployeeDto(employeeEntity ));
    }

    @Override
    public Mono<EmployeeDto> getEmployee(String id) {
        Mono<Employee> savedEmployee = employeeRepository.findById(id);

        return savedEmployee.map((employee)-> EmployeeMapper.mapToEmployeeDto(employee));
    }

    @Override
    public Flux<EmployeeDto> getAllEmployee() {
        Flux<Employee> savedEmployee = employeeRepository.findAll();

        return savedEmployee.map((employee )-> EmployeeMapper.mapToEmployeeDto(employee))
                .switchIfEmpty(Flux.empty());
    }

    @Override
    public Mono<EmployeeDto> updateEmployee(EmployeeDto employeeDto ,String id) {
        Mono<Employee> employeeMono = employeeRepository.findById(id);
        Mono<Employee> updatedEmployee = employeeMono.flatMap((existingEmployee) -> {
            existingEmployee.setFirstName(employeeDto.getFirstName());
            existingEmployee.setLastName(employeeDto.getLastName());
            existingEmployee.setEmail(employeeDto.getEmail());
            return employeeRepository.save(existingEmployee);
        });

        return updatedEmployee.map((employee)->EmployeeMapper.mapToEmployeeDto(employee));
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return employeeRepository.deleteById(id);

    }

    @Override
    public Mono<Void> deleteAll() {
        return  employeeRepository.deleteAll();
    }
}
