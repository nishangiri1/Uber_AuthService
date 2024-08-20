package com.auth.uber_authservice.services;

import com.auth.uber_authservice.dto.PassangerDto;
import com.auth.uber_authservice.dto.PassangerSignUpRequestDto;
import com.auth.uber_authservice.models.Passanger;
import com.auth.uber_authservice.repositories.PassangerRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PassangerRepository passangerRepository;

    public AuthServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, PassangerRepository passangerRepository) {
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
}
