package com.auth.uber_authservice.repositories;



import com.entity.uberprojectentityservice.models.Passanger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PassangerRepository extends JpaRepository<Passanger, Long> {
    Optional<Passanger> findPassangerByEmail(String email);
}
