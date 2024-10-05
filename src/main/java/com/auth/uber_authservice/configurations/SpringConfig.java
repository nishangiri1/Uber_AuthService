package com.auth.uber_authservice.configurations;

import com.auth.uber_authservice.filters.JwtAuthFilter;
import com.auth.uber_authservice.services.DriverDetailServiceImpl;
import com.auth.uber_authservice.services.PassengerDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SpringConfig implements WebMvcConfigurer {

    @Autowired
    private JwtAuthFilter authFilter;

    @Bean
    public UserDetailsService passengerDetailsService()
    {
        return new PassengerDetailServiceImpl();
    }

    @Bean
    public UserDetailsService driverDetailService()
    {
        return new DriverDetailServiceImpl();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception
    {
        return http.csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth->auth
                        .requestMatchers("/api/v1/auth/driver/**").permitAll()
                        .requestMatchers("/api/v1/auth/passenger/**").permitAll()
                        .requestMatchers("/api/v1/auth/validate").authenticated()
                )
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity security) throws Exception{
       AuthenticationManagerBuilder authenticationManagerBuilder= security.getSharedObject(AuthenticationManagerBuilder.class);
             return authenticationManagerBuilder.authenticationProvider(passengerAuthenticationProvider())
                      .authenticationProvider(driverAuthenticationProvider()).build();
    }


    @Bean
    public AuthenticationProvider passengerAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(passengerDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationProvider driverAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(driverDetailService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}
