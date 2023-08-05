package com.gabriel.estudosolojunit.controller;

import com.gabriel.estudosolojunit.model.entities.Produto;
import com.gabriel.estudosolojunit.model.enums.Status;
import com.gabriel.estudosolojunit.service.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProdutoControllerTest {

  private Produto produto;

  @InjectMocks
  ProdutoController produtoController;

  @Mock
  ProdutoService produtoService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    startProduto();
  }

  @Test
  void listarTodos() {
    when(produtoService.listarTodos()).thenReturn(List.of(produto));

    ResponseEntity<List<Produto>> response = produtoController.listarTodos();

    assertNotNull(response);
    assertNotNull(response.getBody());
    assertEquals(ResponseEntity.class, response.getClass());
    assertEquals(HttpStatus.OK, response.getStatusCode());

    assertEquals(1, response.getBody().size());
    assertEquals(1, response.getBody().get(0).getId());
    assertEquals("Computador", response.getBody().get(0).getNome());
    assertEquals("Computador portátil", response.getBody().get(0).getDescricao());
    assertEquals(2999.99, response.getBody().get(0).getValor());
  }

  @Test
  void adicionar() {
    when(produtoService.adicionar(any())).thenReturn(produto);

    ResponseEntity<Produto> response = produtoController.adicionar(produto);

    assertNotNull(response);
    assertEquals(ResponseEntity.class, response.getClass());
    assertEquals(Produto.class, response.getBody().getClass());
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
  }

  private void startProduto() {
    produto = new Produto(1L, "Computador", "Computador portátil", 2999.99,
            "27/07/2023 12:49:45", "27/07/2023 12:49:45", Status.DISPONIVEL);
  }
}