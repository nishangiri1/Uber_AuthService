package com.auth.uber_authservice.controllers;

import com.auth.uber_authservice.dto.*;
import com.auth.uber_authservice.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/passenger/signup")
    public ResponseEntity<PassengerDto> signUp(@RequestBody PassengerSignUpDtp requestDto)
    {
        PassengerDto response=authService.signupPassanger(requestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/passenger/signing")
    public ResponseEntity<?> signInPassenger(@RequestBody AuthRequestDto authRequestDto, HttpServletResponse response)
    {
        System.out.println("Requested credentials : "+ authRequestDto.getEmail()+" "+ authRequestDto.getPassword());
        try{
        return new ResponseEntity<>(authService.authenticateCreateTokenAndSetCookiePassenger(authRequestDto,response),HttpStatus.OK);
        }catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid email or password", HttpStatus.FORBIDDEN);
        }
    }
    @PostMapping("/driver/signing")
    public ResponseEntity<?> signInDriver(@RequestBody AuthRequestDto authRequestDto, HttpServletResponse response)
    {
        System.out.println("Requested credentials : "+ authRequestDto.getEmail()+" "+ authRequestDto.getPassword());
        try{
            return new ResponseEntity<>(authService.authenticateCreateTokenAndSetCookieDriver(authRequestDto,response),HttpStatus.OK);
        }catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid email or password", HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/driver/signup")
    public ResponseEntity<DriverDto> signUp(@RequestBody DriverSignupDTO driverSignupDTO)
    {
        DriverDto response=authService.signupDriver(driverSignupDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping("/validate")
    public ResponseEntity<?> validate(HttpServletRequest request)
    {
        request.getCookies();
        return new ResponseEntity<>(request.getCookies(),HttpStatus.OK);
    }


}
