package com.auth.uber_authservice.repositories;

import com.entity.uberprojectentityservice.models.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
//    List<Driver> findByAvailableIs(boolean isAvailable);
    Optional<Driver> findDriverByEmail(String email);
}

