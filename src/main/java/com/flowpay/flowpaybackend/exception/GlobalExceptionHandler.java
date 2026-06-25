package com.flowpay.flowpaybackend.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String SP_ZONE = "America/Sao_Paulo";

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> notFound(
            EntityNotFoundException ex,
            HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        new ApiError(
                                LocalDateTime.now(ZoneId.of(SP_ZONE)),
                                404,
                                "NOT_FOUND",
                                ex.getMessage(),
                                request.getRequestURI()
                        )
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> validation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        String message =
                ex.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(error ->
                                error.getField()
                                        + ": "
                                        + error.getDefaultMessage())
                        .collect(Collectors.joining(", "));

        return ResponseEntity.badRequest()
                .body(
                        new ApiError(
                                LocalDateTime.now(ZoneId.of(SP_ZONE)),
                                400,
                                "VALIDATION_ERROR",
                                message,
                                request.getRequestURI()
                        )
                );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> constraint(
            ConstraintViolationException ex,
            HttpServletRequest request) {

        return ResponseEntity.badRequest()
                .body(
                        new ApiError(
                                LocalDateTime.now(ZoneId.of(SP_ZONE)),
                                400,
                                "CONSTRAINT_ERROR",
                                ex.getMessage(),
                                request.getRequestURI()
                        )
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> generic(
            Exception ex,
            HttpServletRequest request) {

        return ResponseEntity.internalServerError()
                .body(
                        new ApiError(
                                LocalDateTime.now(ZoneId.of(SP_ZONE)),
                                500,
                                "INTERNAL_SERVER_ERROR",
                                ex.getMessage(),
                                request.getRequestURI()
                        )
                );
    }
}