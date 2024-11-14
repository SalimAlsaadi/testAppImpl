package com.app.implementation.Service;

import com.app.implementation.Entity.Empolyee;
import com.app.implementation.Repos.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private EmployeeRepo employeeRepo;

    @Autowired
    public EmployeeService(EmployeeRepo employeeRepo) {
        //this.repository = repository;
        this.employeeRepo=employeeRepo;
    }


    public String postEmp(@RequestBody Empolyee empolyee){
        employeeRepo.save(empolyee);
        return null;
    }

    public List<Empolyee> getAllEmp(){
        return employeeRepo.findAll();
    }

    public ResponseEntity<String> delete(Integer id){
        Optional<Empolyee> empolyee=employeeRepo.findById(id);
        if(empolyee.isPresent()){
            employeeRepo.delete(empolyee.get());
            return new ResponseEntity<>("Employee deleted successfully", HttpStatus.OK);

        }
        else{
            return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
        }



    }

    public ResponseEntity<String> update(Empolyee employee){
        Optional<Empolyee> emp=employeeRepo.findById(employee.getId());
        if (emp.isPresent()){
            Empolyee existEmp=emp.get();
            existEmp.setFirstName(employee.getFirstName());
            existEmp.setLastName(employee.getLastName());
            existEmp.setEmail(employee.getEmail());
            employeeRepo.save(existEmp);
            return new ResponseEntity<>("Update Has Been Done",HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Employee Not Found",HttpStatus.NOT_FOUND);
        }
    }




}
