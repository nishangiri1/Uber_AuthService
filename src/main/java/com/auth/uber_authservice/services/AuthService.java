package com.auth.uber_authservice.services;

import com.auth.uber_authservice.dto.AuthRequestDto;
import com.auth.uber_authservice.dto.AuthResponseDto;
import com.auth.uber_authservice.dto.PassengerDto;
import com.auth.uber_authservice.dto.PassengerSignUpRequestDto;
import jakarta.servlet.http.HttpServletResponse;


public interface AuthService {
    public PassengerDto signupPassanger(PassengerSignUpRequestDto passangerSignUpRequestDto);
    public AuthResponseDto authenticateCreateTokenAndSetCookie(AuthRequestDto authRequestDto, HttpServletResponse response);

}
