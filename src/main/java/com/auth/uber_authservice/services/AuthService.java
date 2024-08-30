package com.auth.uber_authservice.services;

import com.auth.uber_authservice.dto.AuthRequestDto;
import com.auth.uber_authservice.dto.AuthResponseDto;
import com.auth.uber_authservice.dto.PassangerDto;
import com.auth.uber_authservice.dto.PassangerSignUpRequestDto;
import jakarta.servlet.http.HttpServletResponse;


public interface AuthService {
    public PassangerDto signupPassanger(PassangerSignUpRequestDto passangerSignUpRequestDto);
    public AuthResponseDto authenticateCreateTokenAndSetCookie(AuthRequestDto authRequestDto, HttpServletResponse response);

}
