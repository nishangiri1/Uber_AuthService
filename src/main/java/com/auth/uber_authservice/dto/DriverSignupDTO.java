package com.auth.uber_authservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DriverSignupDTO {
    private String username;
    private String password;
    private String phoneNumber;
    private String NID;
    private String licenseNumber;
}

