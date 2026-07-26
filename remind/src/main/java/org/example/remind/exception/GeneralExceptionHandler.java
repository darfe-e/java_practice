package org.example.remind.exception;

import org.example.remind.dto.error.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@ControllerAdvice
public class GeneralExceptionHandler {

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<ErrorResponseDto> handleResponseStatusException(
      ResponseStatusException ex, WebRequest request){
    String path = request.getDescription(false).replace("uri=", "");
    String error = ex.getReason() != null ? ex.getReason()
        : HttpStatus.valueOf(ex.getStatusCode().value()).getReasonPhrase();
    ErrorResponseDto errorResponse = new ErrorResponseDto(
        LocalDateTime.now(),
        ex.getStatusCode().value(),
        error,
        ex.getMessage(),
        path
    );
    return new ResponseEntity<>(errorResponse, ex.getStatusCode());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponseDto> handleException(
      Exception ex, WebRequest request) {
    String path = request.getDescription(false).replace("uri=", "");
    ErrorResponseDto errorResponse = new ErrorResponseDto(
        LocalDateTime.now(),
        HttpStatus.BAD_REQUEST.value(),
        "Unexpected exception",
        ex.getMessage(),
        path
    );
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

}
