package com.auth.uber_authservice.helper;

import com.entity.uberprojectentityservice.models.Passenger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class AuthPassanngerDetail extends Passenger implements UserDetails {

    public String email;
    public String password;

    public AuthPassanngerDetail(Passenger passanger)
    {
        this.email=passanger.getEmail();
        this.password=passanger.getPassword();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
    @Override
    public String getPassword()
    {
        return this.password;
    }
}
