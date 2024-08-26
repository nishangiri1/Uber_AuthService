package com.auth.uber_authservice.services;

import com.auth.uber_authservice.dto.AuthRequestDto;
import com.auth.uber_authservice.dto.AuthResponseDto;
import com.auth.uber_authservice.dto.PassangerDto;
import com.auth.uber_authservice.dto.PassangerSignUpRequestDto;
import com.auth.uber_authservice.exceptions.InvalidCredentialsException;
import com.auth.uber_authservice.models.Passanger;
import com.auth.uber_authservice.repositories.PassangerRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    public PassangerDto signupPassanger(PassangerSignUpRequestDto passangerSignUpRequestDto)
    {
        Passanger passanger=Passanger.builder()
                .email(passangerSignUpRequestDto.getEmail())
                .name(passangerSignUpRequestDto.getName())
                .password(bCryptPasswordEncoder.encode(passangerSignUpRequestDto.getPassword()))
                .phoneNumber(passangerSignUpRequestDto.getPhoneNumber()) //todo: Entrypt the password
                .build();
        Passanger newPassanger =passangerRepository.save(passanger);
        return PassangerDto.from(newPassanger);
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
                        .httpOnly(true)
                        .secure(false)
                        .path("/")
                        .maxAge(expiryCookie)
                        .build();
                response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
                return AuthResponseDto.builder().success(true).build();
            } else
                throw new UsernameNotFoundException("user with email " + authRequestDto.getEmail() + " not found");
    }
}
