package com.auth.uber_authservice.filters;

import com.auth.uber_authservice.services.JwtService;
import com.auth.uber_authservice.services.PassengerDetailServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final PassengerDetailServiceImpl userDetailService;

    private final JwtService jwtService;



    public JwtAuthFilter(PassengerDetailServiceImpl userDetailService, JwtService jwtService) {
        this.userDetailService = userDetailService;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if("/api/v1/auth/validate".equals(request.getRequestURI()))
        {
            if (request.getCookies() != null) {
                Optional<String> jwtToken = Arrays.stream(request.getCookies())
                        .filter(cookie -> "JwtToken".equals(cookie.getName()))
                        .map(Cookie::getValue)
                        .findFirst();
                System.out.println("**************Forwarding request...************");

                jwtToken.ifPresentOrElse(token -> {
                    String email = jwtService.extractEmail(token);

                    if (email != null) {
                        UserDetails userDetails = userDetailService.loadUserByUsername(email);

                        if (jwtService.validateToken(token, userDetails.getUsername())) {
                            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
                                    new UsernamePasswordAuthenticationToken(userDetails,null,null);
                            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                        }
                    }
                },()->{
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                });
            }
        }

        System.out.println("Forwarding request...");
        filterChain.doFilter(request,response);
    }
}
