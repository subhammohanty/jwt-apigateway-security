package com.javatechie.config;

import com.javatechie.entity.UserCredentials;
import com.javatechie.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserCredentialRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserCredentials> credentials = repository.findByName(username);
        return credentials.map(CustomUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("User Not Found for the user : " + username));
    }
}
