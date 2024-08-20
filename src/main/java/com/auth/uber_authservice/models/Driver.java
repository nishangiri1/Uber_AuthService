package com.auth.uber_authservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Driver extends BaseModel{

    private String name;


    @Column(nullable = false,unique = true)
    private String license_number;


    @OneToMany(mappedBy = "driver")
    @JsonIgnore
    private List<Booking> bookings=new ArrayList<>();


    @Column(nullable = false)
    private Long phoneNumber;


    @Column(nullable = false)
    private String address;
}
