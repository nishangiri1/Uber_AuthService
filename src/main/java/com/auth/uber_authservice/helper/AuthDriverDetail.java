package com.auth.uber_authservice.helper;

import com.entity.uberprojectentityservice.models.Driver;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class AuthDriverDetail extends Driver implements UserDetails {

    private String phoneNumber;
    private String password;

    public AuthDriverDetail(Driver driver)
    {
        this.phoneNumber=driver.getPhoneNumber();
        this.password=driver.getPassword();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.phoneNumber;
    }

    @Override
    public String getPassword()
    {
        return this.password;
    }
}
