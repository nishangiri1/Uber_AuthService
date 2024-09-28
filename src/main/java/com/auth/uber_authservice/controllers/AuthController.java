package com.auth.uber_authservice.controllers;

import com.auth.uber_authservice.dto.AuthRequestDto;
import com.auth.uber_authservice.dto.PassengerDto;
import com.auth.uber_authservice.dto.PassengerSignUpRequestDto;
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

    @PostMapping("/signup/passenger")
    public ResponseEntity<PassengerDto> signUp(@RequestBody PassengerSignUpRequestDto requestDto)
    {
        PassengerDto response=authService.signupPassanger(requestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/signin/passenger")
    public ResponseEntity<?> signIn(@RequestBody AuthRequestDto authRequestDto,HttpServletResponse response)
    {
        System.out.println("Requested credentials : "+authRequestDto.getEmail()+" "+authRequestDto.getPassword());
        try{
        return new ResponseEntity<>(authService.authenticateCreateTokenAndSetCookie(authRequestDto,response),HttpStatus.OK);
        }catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid email or password", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validate(HttpServletRequest request)
    {
        request.getCookies();
        return new ResponseEntity<>(request.getCookies(),HttpStatus.OK);
    }

}
