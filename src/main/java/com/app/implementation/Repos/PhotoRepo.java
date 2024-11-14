package com.app.implementation.Repos;

import com.app.implementation.Entity.Photos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepo extends JpaRepository<Photos, Long> {
}
