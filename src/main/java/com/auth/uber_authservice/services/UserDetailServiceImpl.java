package com.auth.uber_authservice.services;

import com.auth.uber_authservice.helper.AuthUserDetail;
import com.auth.uber_authservice.repositories.PassangerRepository;
import com.auth.uber_authservice.repositories.UserRepository;
import com.entity.uberprojectentityservice.models.Passenger;
import com.entity.uberprojectentityservice.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Primary
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user=userRepository.findByEmail(username);
        if(user.isPresent())
            return new AuthUserDetail(user.get());
        else
            throw new UsernameNotFoundException("Email with "+username+" email not found");
    }
}
