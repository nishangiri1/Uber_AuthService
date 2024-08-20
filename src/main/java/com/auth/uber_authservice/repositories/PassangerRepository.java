package com.auth.uber_authservice.repositories;

import com.auth.uber_authservice.models.Passanger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassangerRepository extends JpaRepository<Passanger, Long> {
}
