package com.gabriel.estudosolojunit.controller;

import com.gabriel.estudosolojunit.model.dto.ProdutoDTO;
import com.gabriel.estudosolojunit.model.entities.Produto;
import com.gabriel.estudosolojunit.model.enums.Status;
import com.gabriel.estudosolojunit.service.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ProdutoControllerTest {

  public static final String NOME = "Computador";
  public static final String DESCRICAO = "PC Gamer Mancer, Ryzen 7 5700G, 16GB DDR4, SSD 240GB, HD 1TB, Fonte 500W 80 Plus";
  public static final double VALOR = 3142.90;
  public static final Status STATUS = Status.DISPONIVEL;
  public static final long ID = 1L;

  Produto produto;
  ProdutoDTO produtoDTO;

  @InjectMocks
  ProdutoController controller;

  @Mock
  ProdutoService service;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    startProduto();
  }

  @Test
  void listarTodos() {
    when(service.listarTodos()).thenReturn(List.of(produtoDTO));

    ResponseEntity<List<ProdutoDTO>> response = controller.listarTodos();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(ResponseEntity.class, response.getClass());

    assertEquals(ID, response.getBody().get(0).getId());
    assertEquals(NOME, response.getBody().get(0).getNome());
    assertEquals(DESCRICAO, response.getBody().get(0).getDescricao());
    assertEquals(VALOR, response.getBody().get(0).getValor());
  }

  @Test
  void listarPorId() {
  }

  @Test
  void adicionar() {
  }

  @Test
  void editar() {
  }

  @Test
  void excluir() {
  }

  private void startProduto() {
    produto = new Produto(ID, NOME, DESCRICAO, VALOR, STATUS);
    produtoDTO = new ProdutoDTO(ID, NOME, DESCRICAO, VALOR, STATUS);
  }
}