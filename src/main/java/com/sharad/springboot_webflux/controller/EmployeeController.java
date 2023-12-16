package com.sharad.springboot_webflux.controller;


import com.sharad.springboot_webflux.dto.EmployeeDto;
import com.sharad.springboot_webflux.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor   // TO CREATE PARAMETRIC CONSTRUCTOR
@RequestMapping("/api/employees")
public class EmployeeController {

    private EmployeeService employeeService;

    //Build reactive save employee Rest Api

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<EmployeeDto> saveEmployee(@RequestBody EmployeeDto employeeDto){
        return employeeService.saveEmployee(employeeDto);
    }
    //Build reactive getEmployee rest Api
    @GetMapping("{id}")
   // @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<EmployeeDto> getEmployee( @PathVariable("id") String id){
        return  employeeService.getEmployee(id);

    }

    // Build Reactive GetAllEmployee rest Api
    @GetMapping
    public Flux<EmployeeDto> getAllEmployee(){
        return  employeeService.getAllEmployee();
    }

    // build reactive updateEmployee rest Api
    @PutMapping("{id}")
    public  Mono<EmployeeDto> updateEmployee(@RequestBody EmployeeDto employeeDto ,@PathVariable("id") String id){
         return employeeService.updateEmployee(employeeDto,id);
    }


    // build reactive DeleByIdEmployee rest API
    @DeleteMapping("{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public  Mono<Void> deleteEmployee(@PathVariable("id") String id){
        return employeeService.deleteById(id);
    }

    @DeleteMapping()
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public  Mono<Void> deleteAllEmployee(){
        return employeeService.deleteAll();
    }
}
