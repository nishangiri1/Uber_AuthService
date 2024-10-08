package com.auth.uber_authservice.services;

import com.auth.uber_authservice.dto.*;
import com.auth.uber_authservice.exceptions.InvalidCredentialsException;
import com.auth.uber_authservice.repositories.DriverRepository;
import com.auth.uber_authservice.repositories.PassangerRepository;
import com.auth.uber_authservice.repositories.RoleRepository;
import com.entity.uberprojectentityservice.models.Driver;
import com.entity.uberprojectentityservice.models.Passenger;
import com.entity.uberprojectentityservice.models.Role;
import com.entity.uberprojectentityservice.models.RoleType;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    @Value("${cookie.expiry}")
    private Integer expiryCookie;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;
    private final UserDetailServiceImpl userDetailService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PassangerRepository passangerRepository;
    private final DriverRepository driverRepository;
    private final RoleRepository roleRepository;


    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtService jwtService, UserDetailServiceImpl userDetailService, BCryptPasswordEncoder bCryptPasswordEncoder, PassangerRepository passangerRepository,
                           DriverRepository driverRepository, RoleRepository roleRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailService = userDetailService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.passangerRepository = passangerRepository;
        this.driverRepository = driverRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public PassengerDto signupPassanger(PassengerSignUpDtp passangerSignUpRequestDto)
    {
        Role passengerRole= roleRepository.findByRoleType(RoleType.ROLE_PASSENGER)
                .orElseGet(()->roleRepository.save(
                        Role.builder()
                                .roleType(RoleType.ROLE_PASSENGER)
                                .build())
                );


        Passenger passanger=Passenger.builder()
                .email(passangerSignUpRequestDto.getEmail())
                .fullName(passangerSignUpRequestDto.getName())
                .password(bCryptPasswordEncoder.encode(passangerSignUpRequestDto.getPassword())) //todo: Entrypt the password
                .phoneNumber(passangerSignUpRequestDto.getPhoneNumber())
                .role(passengerRole)
                .build();
        Passenger newPassenger =passangerRepository.save(passanger);
        return PassengerDto.from(newPassenger);
    }
    @Override
    public AuthResponseDto authenticateCreateTokenAndSetCookiePassenger(AuthRequestDto authRequestDto, HttpServletResponse response)
    {
        return getAuthResponseDto(authRequestDto, response);
    }

    @Override
    public AuthResponseDto authenticateCreateTokenAndSetCookieDriver(AuthRequestDto authRequestDto, HttpServletResponse response) {
        return getAuthResponseDto(authRequestDto, response);
    }

    private AuthResponseDto getAuthResponseDto(AuthRequestDto authRequestDto, HttpServletResponse response) {
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequestDto.getEmail(),authRequestDto.getPassword()
                )
        );
        if (authentication.isAuthenticated()) {
            final UserDetails userDetails= userDetailService.loadUserByUsername(authRequestDto.getEmail());
            String jwtToken = jwtService.createToken(userDetails);
            ResponseCookie cookie = ResponseCookie.from("JwtToken",jwtToken)
                    .httpOnly(false)
                    .secure(false)
                    .path("/")
                    .maxAge(expiryCookie)
                    .build();
            response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            return AuthResponseDto.builder().success(true)
                    .jwt("Bearer "+jwtToken)
                    .build();
        } else {
            throw new InvalidCredentialsException("Invalid email or password");
        }
    }

    @Override
    @Transactional
    public DriverDto signupDriver(DriverSignupDTO driverSignupDTO) {
       Role driverRole= roleRepository.findByRoleType(RoleType.ROLE_DRIVER)
                .orElseGet(()->roleRepository.save(
                        Role.builder()
                                .roleType(RoleType.ROLE_DRIVER)
                                .build())
                );

        Driver driver=Driver.builder()
                .fullName(driverSignupDTO.getUsername())
                .licenseNumber(driverSignupDTO.getLicenseNumber())
                .phoneNumber(driverSignupDTO.getPhoneNumber())
                .isAvailable(true)
                .email(driverSignupDTO.getEmail())
                .NID(driverSignupDTO.getNID())
                .role(driverRole)
                .password(bCryptPasswordEncoder.encode(driverSignupDTO.getPassword()))
                .build();

        Driver newDriver=driverRepository.save(driver);
        return DriverDto.from(newDriver);
    }

}
