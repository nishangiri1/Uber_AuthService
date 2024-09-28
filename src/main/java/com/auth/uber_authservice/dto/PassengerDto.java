package com.auth.uber_authservice.dto;

import com.entity.uberprojectentityservice.models.Passenger;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PassengerDto {
    private String id;

    private String name;

    private String email;

    private String password;//encrypted password

    private String phoneNumber;

    private Date createdAt;
    public static PassengerDto from(Passenger passanger)
    {
       return  PassengerDto.builder()
                .id(passanger.getId().toString())
                .createdAt(passanger.getCreatedAt())
                .email(passanger.getEmail())
                .password(passanger.getPassword())
                .phoneNumber(passanger.getPhoneNumber())
                .name(passanger.getName())
                .build();
    }
}
