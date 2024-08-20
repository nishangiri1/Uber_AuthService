package com.auth.uber_authservice.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Passanger extends BaseModel {

    private String name;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @OneToMany(mappedBy = "passanger")
    private List<Booking> bookings=new ArrayList<>();

    public Long getId()
    {
        return this.id;
    }

    public Date getCreatedAt() {
        return this.createdAt;
    }
}
