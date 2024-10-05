package com.auth.uber_authservice.services;

import com.auth.uber_authservice.helper.AuthDriverDetail;
import com.auth.uber_authservice.helper.AuthPassanngerDetail;
import com.auth.uber_authservice.repositories.DriverRepository;
import com.entity.uberprojectentityservice.models.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DriverDetailServiceImpl implements UserDetailsService {

    @Autowired
    private DriverRepository driverRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Driver> driver=driverRepository.findDriverByPhoneNumber(username);
        if(driver.isPresent()){
            return new AuthDriverDetail(driver.get());
        }else{
            throw new UsernameNotFoundException("Driver not found with username "+driver.get().getPhoneNumber());
        }
    }
}
