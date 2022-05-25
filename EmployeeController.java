package com.example.firstspringboot.comntroller;
import com.example.firstspringboot.Employee;
import com.example.firstspringboot.Park;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    List<Employee> employees = new ArrayList<>();
    public EmployeeController() {
        employees.addAll(
                List.of(
                        new Employee("101","Abdullah", 29,false,"1990", 6),
                        new Employee("102","Ahmed", 39,false,"2010", 12),
                        new Employee("103","Ali", 49,false,"1200", 24),
                        new Employee("104","Saad", 59,false,"2020", 30)
                ));
    }
    @GetMapping()
    ResponseEntity<Object> getEmployee(){
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("{id}")
    ResponseEntity<Object> getEmployeeByID(@PathVariable String id){
        int checkForWork = -1;
        Employee employee = getById(employees, id);
        if (employee != null){
            checkForWork = Integer.parseInt(id);
        }
        return (checkForWork == -1) ? new ResponseEntity<>("Not Found, no employee with that id", HttpStatus.BAD_REQUEST) :   new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PostMapping()
    ResponseEntity<Object> addEmployee(@RequestBody @Valid Employee e, Errors errors){
        if (errors.hasErrors()){
            String er = errors.getFieldError().getDefaultMessage();
            System.out.println(er);
            return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
        }
        employees.add(e);
        return new ResponseEntity<>(employees, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> putEmployee(@RequestBody @Valid Employee e, Errors errors, @PathVariable String id){
        if (errors.hasErrors()) {
            String er = errors.getFieldError().getDefaultMessage();
            return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
        }
        Employee employee = getById(employees, id);
        if (employee != null){
            employee.setAge(e.getAge());
            employee.setEmploymentYear(e.getEmploymentYear());
            employee.setName(e.getName());
            employee.setAnnualLeave(e.getAnnualLeave());
            employee.setOnLeave(e.isOnLeave());
            return new ResponseEntity<>(employee, HttpStatus.OK);
        }else
            return addEmployee(e, errors);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable String id){
        int checkForWork = -1;
        Employee employee = getById(employees, id);
        if (employee != null){
            checkForWork = Integer.parseInt(id);
            employees.remove(employee);
        }
        return (checkForWork == -1) ? new ResponseEntity<>("Not Found, no employee with that id", HttpStatus.BAD_REQUEST)
                :   new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PutMapping("annual-leave/{id}")
    public ResponseEntity<Object> putEmployeeAnnualLeave(@PathVariable String id){
        Employee employee = getById(employees, id);
        if (employee != null){
          if (employee.isOnLeave()){
              return new ResponseEntity<>("You are on a leave already!", HttpStatus.BAD_REQUEST);
          } else {
              if (employee.getAnnualLeave()<=0){
                  return new ResponseEntity<>("You have no days for leave", HttpStatus.BAD_REQUEST);
              }
              employee.setOnLeave(true);
              employee.setAnnualLeave(employee.getAnnualLeave()-1);
          }
            return new ResponseEntity<>(employee, HttpStatus.OK);
        }else
            return new ResponseEntity<>("Not Found, no employee with that id", HttpStatus.BAD_REQUEST);
    }



    public static Employee getById(List<Employee> e, String id){
        for (Employee employee: e) {
            if (employee.getID().equals(id)){
                return employee;
            }
        }
        return null;
    }
}
