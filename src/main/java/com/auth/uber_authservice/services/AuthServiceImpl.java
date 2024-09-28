package com.auth.uber_authservice.services;

import com.auth.uber_authservice.dto.AuthRequestDto;
import com.auth.uber_authservice.dto.AuthResponseDto;
import com.auth.uber_authservice.dto.PassengerSignUpRequestDto;
import com.auth.uber_authservice.dto.PassengerDto;
import com.auth.uber_authservice.exceptions.InvalidCredentialsException;
import com.auth.uber_authservice.repositories.PassangerRepository;
import com.entity.uberprojectentityservice.models.Passenger;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Value("${cookie.expiry}")
    private Integer expiryCookie;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PassangerRepository passangerRepository;


    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtService jwtService, BCryptPasswordEncoder bCryptPasswordEncoder, PassangerRepository passangerRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.passangerRepository = passangerRepository;
    }

    public PassengerDto signupPassanger(PassengerSignUpRequestDto passangerSignUpRequestDto)
    {
        Passenger passanger=Passenger.builder()
                .email(passangerSignUpRequestDto.getEmail())
                .name(passangerSignUpRequestDto.getName())
                .password(bCryptPasswordEncoder.encode(passangerSignUpRequestDto.getPassword()))
                .phoneNumber(passangerSignUpRequestDto.getPhoneNumber()) //todo: Entrypt the password
                .build();
        Passenger newPassenger =passangerRepository.save(passanger);
        return PassengerDto.from(newPassenger);
    }
    public AuthResponseDto authenticateCreateTokenAndSetCookie(AuthRequestDto authRequestDto, HttpServletResponse response)
    {
            Authentication authentication = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    authRequestDto.getEmail(), authRequestDto.getPassword()));
            if (authentication.isAuthenticated()) {
                String jwtToken = jwtService.createToken(authRequestDto.getEmail());
                ResponseCookie cookie = ResponseCookie.from("JwtToken", jwtToken)
                        .httpOnly(false)
                        .secure(false)
                        .path("/")
                        .maxAge(expiryCookie)
                        .build();
                response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
                return AuthResponseDto.builder().success(true).build();
            } else {
                throw new InvalidCredentialsException("Invalid email or password");
            }
    }
}
