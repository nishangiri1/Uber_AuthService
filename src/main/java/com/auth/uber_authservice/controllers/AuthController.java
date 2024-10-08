package com.auth.uber_authservice.controllers;

import com.auth.uber_authservice.dto.*;
import com.auth.uber_authservice.services.AuthService;
import com.auth.uber_authservice.services.JwtService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup/passenger")
    public ResponseEntity<PassengerDto> signUp(@RequestBody PassengerSignUpDtp requestDto)
    {
        PassengerDto response=authService.signupPassanger(requestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


//    @PreAuthorize("hasRole('ROLE_PASSENGER')")
    @PostMapping("/signing/passenger")
    public ResponseEntity<?> signInPassenger(@RequestBody AuthRequestDto authRequestDto, HttpServletResponse response)
    {
        System.out.println("Requested credentials : "+ authRequestDto.getEmail()+" "+ authRequestDto.getPassword());
        try{
        return new ResponseEntity<>(authService.authenticateCreateTokenAndSetCookiePassenger(authRequestDto,response),HttpStatus.OK);
        }catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid email or password", HttpStatus.FORBIDDEN);
        }
    }

//    @RolesAllowed(value = "hasRole('ROLE_DRIVER')")
    @PostMapping("/signing/driver")
    public ResponseEntity<?> signInDriver(@RequestBody AuthRequestDto authRequestDto, HttpServletResponse response)
    {
        System.out.println("Requested credentials : "+ authRequestDto.getEmail()+" "+ authRequestDto.getPassword());
        try{
            return new ResponseEntity<>(authService.authenticateCreateTokenAndSetCookieDriver(authRequestDto,response),HttpStatus.OK);
        }catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid email or password", HttpStatus.FORBIDDEN);
        }
    }



    @PostMapping("/signup/driver")
    public ResponseEntity<DriverDto> signUp(@RequestBody DriverSignupDTO driverSignupDTO)
    {
        DriverDto response=authService.signupDriver(driverSignupDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/hurry")
    public ResponseEntity<?> hya(){
        return ResponseEntity.ok("Welcome passenger");
    }

    @GetMapping("/furry")
    public ResponseEntity<?> hyaMula(){
        return ResponseEntity.ok("Welcome Driver. ");
    }


    @GetMapping("/validate")
    public ResponseEntity<?> validate(HttpServletRequest request)
    {
        request.getCookies();
        return new ResponseEntity<>(request.getCookies(),HttpStatus.OK);
    }


}
