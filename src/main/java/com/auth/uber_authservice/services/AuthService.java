package com.auth.uber_authservice.services;

import com.auth.uber_authservice.dto.PassangerDto;
import com.auth.uber_authservice.dto.PassangerSignUpRequestDto;
import com.auth.uber_authservice.models.Passanger;
import com.auth.uber_authservice.repositories.PassangerRepository;
import org.springframework.stereotype.Service;

public interface AuthService {
    public PassangerDto signupPassanger(PassangerSignUpRequestDto passangerSignUpRequestDto);
}
