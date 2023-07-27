package com.gabriel.estudosolojunit.service.exception;

import com.gabriel.estudosolojunit.model.StandardError;
import com.gabriel.estudosolojunit.model.exceptions.ProdutoNaoEncontradoException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ServiceExceptionHandler {

  @ExceptionHandler(ProdutoNaoEncontradoException.class)
  public ResponseEntity<StandardError> produtoNaoEncontrado(ProdutoNaoEncontradoException ex, HttpServletRequest request) {
    StandardError error = new StandardError(LocalDateTime.now(),
            HttpStatus.NOT_FOUND.value(),
            ex.getMessage(),
            request.getRequestURI());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }
}
