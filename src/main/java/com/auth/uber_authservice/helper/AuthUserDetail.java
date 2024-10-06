package com.auth.uber_authservice.helper;

import com.entity.uberprojectentityservice.models.Passenger;
import com.entity.uberprojectentityservice.models.Role;
import com.entity.uberprojectentityservice.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class AuthUserDetail  implements UserDetails {

    private Long id;
    private String email;
    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public AuthUserDetail(User user)
    {
        this.id= user.getId();
        this.email=user.getEmail();
        this.password=user.getPassword();
        this.authorities=Collections.singleton(new SimpleGrantedAuthority(user.getRole().getRoleType().name()));
    }




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       return this.authorities;
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
