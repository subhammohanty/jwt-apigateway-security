package com.javatechie.filter;

import com.javatechie.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (((exchange, chain) -> {
            ServerHttpRequest loggedInUserToken = null;
            if(validator.isSecured.test(exchange.getRequest())){
                //check if header contains token or not
                if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                    throw new RuntimeException("Missing Token");
                }
                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if(authHeader != null && authHeader.startsWith("Bearer ")){
                    authHeader = authHeader.substring(7);
                }
                try{
                    //REST cal to identity service
//                    String forObject = restTemplate.getForObject("http://localhost:9898/auth/validate?token" + authHeader, String.class);
//                    template.getForObject("http://IDENTITY-SERVICE//validate?token" + authHeader, String.class);
                    jwtUtil.validateToken(authHeader);

                     loggedInUserToken = exchange.getRequest()
                            .mutate()
                            .header("loggedInUserToken", authHeader)
                            .build();

                }catch (Exception e){
                    System.out.println("Invalid Token !!");
                    throw new RuntimeException("Invalid Token !!");
                }
            }
            return chain.filter(exchange.mutate().request(loggedInUserToken).build());
        }));
    }

    public static class Config{

    }
}
