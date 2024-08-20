package com.auth.uber_authservice.dto;

import com.auth.uber_authservice.models.Passanger;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PassangerDto {
    private String id;

    private String name;

    private String email;

    private String password;//encrypted password

    private String phoneNumber;

    private Date createdAt;
    public static PassangerDto from(Passanger passanger)
    {
       return  PassangerDto.builder()
                .id(passanger.getId().toString())
                .createdAt(passanger.getCreatedAt())
                .email(passanger.getEmail())
                .password(passanger.getPassword())
                .phoneNumber(passanger.getPhoneNumber())
                .name(passanger.getName())
                .build();
    }
}
