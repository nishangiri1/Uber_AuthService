package com.auth.uber_authservice.repositories;

import com.auth.uber_authservice.models.Passanger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassangerRepository extends JpaRepository<Passanger, Long> {
}
