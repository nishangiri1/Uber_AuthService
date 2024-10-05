//package com.auth.uber_authservice.services;
//
//import com.auth.uber_authservice.helper.AuthDriverDetail;
//import com.auth.uber_authservice.helper.AuthPassanngerDetail;
//import jakarta.annotation.PostConstruct;
//import lombok.Data;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//@Data
//public class ComposedDetailsService implements UserDetailsService {
//
//    @Autowired
//    private final DriverDetailServiceImpl driverDetailService;
//    @Autowired
//    private final PassengerDetailServiceImpl passengerDetailService;
//
//    private List<UserDetailsService> services;
//
//    @PostConstruct
//    public void setServices() {
//        List<UserDetailsService> new_services = new ArrayList<>();
//        new_services.add(this.passengerDetailService);
//        new_services.add( this.driverDetailService); // injection
//        this.services = new_services;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        for (UserDetailsService service : services) {
//            try {
//                UserDetails user = service.loadUserByUsername(username);
//                return user;
//            } catch (UsernameNotFoundException e) {
//
//            }
//        }
//        throw new UsernameNotFoundException("User Not Found");
//    }
//}