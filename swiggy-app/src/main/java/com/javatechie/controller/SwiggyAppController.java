package com.javatechie.controller;

import com.javatechie.dto.OrderResponseDTO;
import com.javatechie.service.SwiggyAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/swiggy")
public class SwiggyAppController {

    @Autowired
    private SwiggyAppService service;

    @GetMapping("/home")
    public String greetingMessage() {
        return service.greeting();
    }

    @GetMapping("/{orderId}")
    public OrderResponseDTO checkOrderStatus(@PathVariable String orderId, @RequestHeader("loggedInUserToken") String token) {
        System.out.println("JWT Token : " + token);
        return service.checkOrderStatus(orderId);
    }
}
