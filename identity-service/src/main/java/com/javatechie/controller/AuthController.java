package com.javatechie.controller;

import com.javatechie.dto.AuthRequest;
import com.javatechie.entity.UserCredentials;
import com.javatechie.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping("/register")
    public String addUser(@RequestBody UserCredentials userCredentials){
        return service.saveUser(userCredentials);
    }

    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest){
        return service.generateToken(authRequest);
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token){
        service.validateToken(token);
        return "Validated Successfully !!";
    }
}
