package com.javatechie.advice;

import com.javatechie.dto.CustomErrorResponse;
import com.javatechie.dto.GlobalErrorCodes;
import com.javatechie.exception.OrderNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class RestaurantServiceGlobalExceptionHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<?> handleOrderNotFoundException(OrderNotFoundException ex){
        CustomErrorResponse errorResponse = CustomErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .errorCode(GlobalErrorCodes.ERROR_ORDER_NOT_FOUND)
                .errorMessage(ex.getMessage())
                .build();
        log.error("RestaurantServiceGlobalExceptionHandler.handleOrderNotFoundException exception caught {}" ,ex.getMessage());
        return ResponseEntity.internalServerError().body(errorResponse);
    }

    //For Handling Global Exception
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<?> handleGlobalException(Exception ex){
//        CustomErrorResponse errorResponse = CustomErrorResponse.builder()
//                .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .errorCode(GlobalErrorCodes.GENERIC_ERROR)
//                .errorMessage(ex.getMessage())
//                .build();
//        log.error("RestaurantServiceGlobalExceptionHandler.handleGlobalException exception caught {}" ,ex.getMessage());
//        return ResponseEntity.internalServerError().body(errorResponse);
//    }
}
