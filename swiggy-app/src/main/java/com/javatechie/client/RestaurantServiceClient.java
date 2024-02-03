package com.javatechie.client;

import com.javatechie.dto.OrderResponseDTO;
import com.javatechie.exception.SwiggyServiceException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class RestaurantServiceClient {
    @Autowired
    private RestTemplate template;

    public OrderResponseDTO fetchOrderStatus(String orderId) {
        OrderResponseDTO orderResponseDTO = null;
        try{
            template.getForObject("http://RESTAURANT-SERVICE/restaurant/orders/status/" + orderId, OrderResponseDTO.class);
        }catch (HttpServerErrorException exception){
            log.error("RestaurantServiceClient.fetchOrderStatus exception caught {}" , exception.getMessage());
            throw new SwiggyServiceException(exception.getResponseBodyAsString());
        }catch (Exception ex){
            log.error("RestaurantServiceClient.fetchOrderStatus global exception caught {}" , ex.getMessage());
        }
        return orderResponseDTO;
    }

}
