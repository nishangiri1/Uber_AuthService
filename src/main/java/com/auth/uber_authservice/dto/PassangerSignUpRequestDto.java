package com.auth.uber_authservice.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PassangerSignUpRequestDto {

    private String email;

    private String password;

    private String phoneNumber;

    private String name;

}
