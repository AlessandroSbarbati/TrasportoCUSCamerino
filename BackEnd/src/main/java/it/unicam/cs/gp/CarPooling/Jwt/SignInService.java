package it.unicam.cs.gp.CarPooling.Jwt;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface SignInService {
    UserDetailsService userDetailsService();
}
