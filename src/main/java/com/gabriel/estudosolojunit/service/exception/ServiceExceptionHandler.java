package com.gabriel.estudosolojunit.service.exception;

import com.gabriel.estudosolojunit.model.StandardError;
import com.gabriel.estudosolojunit.model.exceptions.JaCadastradoException;
import com.gabriel.estudosolojunit.model.exceptions.NaoEncontradoException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class ServiceExceptionHandler {

  @ExceptionHandler(NaoEncontradoException.class)
  public ResponseEntity<StandardError> produtoNaoEncontrado(NaoEncontradoException ex, HttpServletRequest request) {
    StandardError error = new StandardError(LocalDateTime.now(),
            HttpStatus.NOT_FOUND.value(),
            ex.getMessage(),
            request.getRequestURI());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<StandardError> campoVazio(ConstraintViolationException ex, HttpServletRequest request) {
    StandardError error = new StandardError(LocalDateTime.now(),
            HttpStatus.UNPROCESSABLE_ENTITY.value(),
            ex.getMessage(),
            request.getRequestURI());
    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
  }

  @ExceptionHandler(JaCadastradoException.class)
  public ResponseEntity<StandardError> jaCadastrado(JaCadastradoException ex, HttpServletRequest request) {
    StandardError error = new StandardError(LocalDateTime.now(),
            HttpStatus.UNPROCESSABLE_ENTITY.value(),
            ex.getMessage(),
            request.getRequestURI());
    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<StandardError> campoVazio(MethodArgumentNotValidException ex, HttpServletRequest request) {
    StandardError error = new StandardError(LocalDateTime.now(),
            HttpStatus.UNPROCESSABLE_ENTITY.value(),
            ex.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                    .collect(Collectors.joining("; ")),
            request.getRequestURI());
    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
  }
}
