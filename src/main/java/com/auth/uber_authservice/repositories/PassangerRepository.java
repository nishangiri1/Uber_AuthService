package com.auth.uber_authservice.repositories;



import com.entity.uberprojectentityservice.models.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PassangerRepository extends JpaRepository<Passenger, Long> {
    Optional<Passenger> findPassangerByEmail(String email);
    Optional<Passenger> findPassengerByPhoneNumber(String number);
}
