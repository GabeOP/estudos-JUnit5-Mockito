package com.gabriel.estudosolojunit.service.exception;

import com.gabriel.estudosolojunit.model.StandardError;
import com.gabriel.estudosolojunit.model.exceptions.ProdutoNaoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ServiceExceptionHandlerTest {

  @InjectMocks
  ServiceExceptionHandler handler;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void produtoNaoEncontradoException() {
    ResponseEntity<StandardError> response = handler
            .produtoNaoEncontrado
            (new ProdutoNaoEncontradoException("Produto não encontrado"), new MockHttpServletRequest());

    assertNotNull(response);
    assertNotNull(response.getBody());

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals(ResponseEntity.class, response.getClass());
    assertEquals(StandardError.class, response.getBody().getClass());
    assertEquals("Produto não encontrado", response.getBody().getError());
    assertEquals(404, response.getBody().getStatus());
  }

  @Test
  void campoVazio() {
  }

  @Test
  void jaCadastrado() {
  }

  @Test
  void testCampoVazio() {
  }
}