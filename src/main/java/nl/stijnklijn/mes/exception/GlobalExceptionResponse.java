package nl.stijnklijn.mes.exception;

import org.springframework.http.HttpStatusCode;

public record GlobalExceptionResponse(HttpStatusCode code, String message) {}
