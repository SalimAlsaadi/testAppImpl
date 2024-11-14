package com.app.implementation.Controller;

import com.app.implementation.Entity.Empolyee;
import com.app.implementation.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Emp")
public class EmpController {

    private EmployeeService employeeService;

    public EmpController(){}

    @Autowired
    public EmpController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // POST method to add a new employee
    @PostMapping
    public ResponseEntity<String> postEmp(@RequestBody Empolyee empolyee) {
        employeeService.postEmp(empolyee);
        return new ResponseEntity<>("Employee has been added", HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Empolyee>> getAllEmp() {
        List<Empolyee> listEmp = employeeService.getAllEmp();
        if (listEmp.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listEmp, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmp(@PathVariable int id){
        return employeeService.delete(id);
    }

    @PutMapping()
    public ResponseEntity<String> update(@RequestBody Empolyee empolyee){
        return employeeService.update(empolyee);
    }

}
