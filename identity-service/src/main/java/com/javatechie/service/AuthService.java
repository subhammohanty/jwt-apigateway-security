package com.javatechie.service;

import com.javatechie.dto.AuthRequest;
import com.javatechie.entity.UserCredentials;
import com.javatechie.repository.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserCredentialRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public String saveUser(UserCredentials credentials){
        credentials.setPassword(passwordEncoder.encode(credentials.getPassword()));
        repository.save(credentials);
        return "user save successfully to system";
    }

    public String generateToken(AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
        if(authenticate.isAuthenticated()){
            return jwtService.generateToken(authRequest.getUserName());
        }else{
            throw new RuntimeException("Invalid Exception");
        }
    }

    public void validateToken(String token){
        jwtService.validateToken(token);
    }
}
