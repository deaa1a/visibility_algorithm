package com.visibility.infrastructure.api.controller;


import com.visibility.infrastructure.api.dto.exception.ErrorResponseDto;
import com.visibility.domain.exceptions.GetProductsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@RestControllerAdvice
public class RestExceptionAdvice {

    @ExceptionHandler(GetProductsException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(GetProductsException ex) {
        ErrorResponseDto errorResponse = new ErrorResponseDto(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

}
