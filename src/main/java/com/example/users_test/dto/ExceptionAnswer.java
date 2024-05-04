package com.example.users_test.dto;

import lombok.Builder;

@Builder
public record ExceptionAnswer(
        String timestamp,
        int status,
        String error,
        String message,
        String path
) {
}
