package com.auth.uber_authservice.dto;

import com.entity.uberprojectentityservice.models.Driver;
import com.entity.uberprojectentityservice.models.Passenger;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DriverDto {
    private String id;

    private String name;

    private String password;//encrypted password

    private String phoneNumber;

    private Date createdAt;

    private String licenseNumber;

    private String NID;
    public static DriverDto from(Driver driver)
    {
        return  DriverDto.builder()
                .id(driver.getId().toString())
                .createdAt(driver.getCreatedAt())
                .password(driver.getPassword())
                .phoneNumber(driver.getPhoneNumber())
                .name(driver.getName())
                .NID(driver.getNID())
                .licenseNumber(driver.getLicenseNumber())
                .build();
    }
}