package com.auth.uber_authservice.services;

import com.auth.uber_authservice.helper.AuthPassanngerDetail;
import com.auth.uber_authservice.models.Passanger;
import com.auth.uber_authservice.repositories.PassangerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private PassangerRepository passangerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Passanger> passanger=passangerRepository.findPassangerByEmail(username);
        if(passanger.isPresent())
            return new AuthPassanngerDetail(passanger.get());
        else
            throw new UsernameNotFoundException("Email with "+passanger.get().getEmail()+" email not found");
    }
}
