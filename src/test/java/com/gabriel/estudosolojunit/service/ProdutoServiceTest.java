package com.gabriel.estudosolojunit.service;

import com.gabriel.estudosolojunit.model.dto.ProdutoDTO;
import com.gabriel.estudosolojunit.model.entities.Produto;
import com.gabriel.estudosolojunit.model.enums.Status;
import com.gabriel.estudosolojunit.model.exceptions.ProdutoJaCadastradoException;
import com.gabriel.estudosolojunit.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProdutoServiceTest {

  public static final String NOME = "Computador";
  public static final String DESCRICAO = "Lorem Ipsum";
  public static final double VALOR = 1999.90;
  public static final Status STATUS = Status.DISPONIVEL;
  @InjectMocks
  ProdutoService service;

  @Mock
  ProdutoRepository repository;

  @Mock
  ModelMapper mapper;

  private Produto produto;
  private ProdutoDTO produtoDTO;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    startProduto();
  }

  @Test
  void listarTodos() {
  }

  @Test
  void listarPorId() {
  }

  @Test
  void whenAdicionarThenAddUser() {
    when(repository.save(any())).thenReturn(produto);

    ProdutoDTO response = service.adicionar(produtoDTO);

    assertNotNull(response);
    assertEquals(response.getClass(), ProdutoDTO.class);

    assertEquals(response.getNome(), NOME);
    assertEquals(response.getDescricao(), DESCRICAO);
    assertEquals(response.getValor(), VALOR);
    assertEquals(response.getStatus(), STATUS);
  }

  @Test
  void whenAdicionarThenThrowProdutoJaCadastradoException() {
    when(repository.save(any())).thenThrow(new ProdutoJaCadastradoException("Produto já cadastrado"));

    try{
      service.adicionar(produtoDTO);
    }catch(Exception ex) {
      assertEquals(ProdutoJaCadastradoException.class, ex.getClass());
      assertEquals("Produto já cadastrado", ex.getMessage());
    }

  }
  @Test
  void editar() {
  }

  @Test
  void excluir() {
  }

  private void startProduto() {
    produto = new Produto(NOME, DESCRICAO, VALOR, STATUS);
    produtoDTO = new ProdutoDTO(NOME, DESCRICAO, VALOR, STATUS);
  }
}