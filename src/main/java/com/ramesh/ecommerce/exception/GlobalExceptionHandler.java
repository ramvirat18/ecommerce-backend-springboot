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

//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<ErrorResponseDTO> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest req)
//    {
//        ErrorResponseDTO responseDTO = new ErrorResponseDTO(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),"NOT_FOUND", ex.getMessage(), req.getRequestURI());
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex,HttpServletRequest req)
    {
        String message = ex.getBindingResult().getFieldError().getDefaultMessage();

        ErrorResponseDTO responseDTO = new ErrorResponseDTO(LocalDateTime.now(),HttpStatus.BAD_REQUEST.value(),"BAD_REQUEST",message,req.getRequestURI());
        return ResponseEntity.badRequest().body(responseDTO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleException (Exception ex, HttpServletRequest req)
    {
        ErrorResponseDTO responseDTO = new ErrorResponseDTO(LocalDateTime.now(),500,"INTERNAL_SERVER_ERROR",ex.getMessage(),req.getRequestURI());
        return ResponseEntity.status(500).body(responseDTO);
    }

    @ExceptionHandler( ProductNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleProductNotFoundException( ProductNotFoundException ex, HttpServletRequest req)
    {
        ErrorResponseDTO responseDTO = ErrorResponseDTO.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error("NOT_FOUND")
                .message(ex.getMessage())
                .path(req.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleCategoryNotFoundException(CategoryNotFoundException ex,HttpServletRequest req)
    {
        ErrorResponseDTO responseDTO = ErrorResponseDTO.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error("NOT_FOUND")
                .message(ex.getMessage())
                .path(req.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
    }

}
