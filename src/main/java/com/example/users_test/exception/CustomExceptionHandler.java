package com.example.users_test.exception;

import com.example.users_test.dto.ExceptionAnswer;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = {SearchException.class, AgeRestrictionException.class})
    public ResponseEntity<ExceptionAnswer> handleBadRequestExceptions(RuntimeException ex, HttpServletRequest request) {

        log.error("Search error at {}: {}", request.getContextPath(), ex.getMessage());

        ExceptionAnswer answer = ExceptionAnswer.builder()
                .timestamp(DateTimeFormatter.ISO_INSTANT.format(Instant.now()))
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.badRequest().body(answer);
    }
}
