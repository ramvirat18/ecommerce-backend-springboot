package com.ramesh.ecommerce.exception;

import com.ramesh.ecommerce.dto.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest req)
    {
        ErrorResponseDTO responseDTO = new ErrorResponseDTO(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(), req.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,HttpServletRequest req)
    {
        String message = ex.getBindingResult().getFieldError().getDefaultMessage();

        ErrorResponseDTO responseDTO = new ErrorResponseDTO(LocalDateTime.now(),HttpStatus.BAD_REQUEST.value(),message,req.getRequestURI());
        return ResponseEntity.badRequest().body(responseDTO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleException (Exception ex, HttpServletRequest req)
    {
        ErrorResponseDTO responseDTO = new ErrorResponseDTO(LocalDateTime.now(),500,ex.getMessage(),req.getRequestURI());
        return ResponseEntity.status(500).body(responseDTO);
    }
}
