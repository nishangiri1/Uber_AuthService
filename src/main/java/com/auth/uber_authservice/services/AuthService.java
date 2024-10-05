package com.auth.uber_authservice.services;

import com.auth.uber_authservice.dto.*;
import jakarta.servlet.http.HttpServletResponse;


public interface AuthService {
    public PassengerDto signupPassanger(PassengerSignUpDtp passangerSignUpRequestDto);

    public AuthResponseDto authenticateCreateTokenAndSetCookiePassenger(AuthRequestDto authRequestDto, HttpServletResponse response);
    public AuthResponseDto authenticateCreateTokenAndSetCookieDriver(AuthRequestDto authRequestDto, HttpServletResponse response);

    public DriverDto signupDriver(DriverSignupDTO driverSignupDTO);

}
