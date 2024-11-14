package com.app.implementation.Repos;

import com.app.implementation.Entity.Empolyee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepo extends JpaRepository<Empolyee, Integer> {
}


