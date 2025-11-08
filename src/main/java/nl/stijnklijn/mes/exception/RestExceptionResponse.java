package nl.stijnklijn.mes.exception;

import org.springframework.http.HttpStatusCode;

public record RestExceptionResponse(HttpStatusCode code, String message) {}
