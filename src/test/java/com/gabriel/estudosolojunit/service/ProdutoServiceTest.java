package com.gabriel.estudosolojunit.service;

import com.gabriel.estudosolojunit.model.dto.ProdutoDTO;
import com.gabriel.estudosolojunit.model.entities.Produto;
import com.gabriel.estudosolojunit.model.enums.Status;
import com.gabriel.estudosolojunit.model.exceptions.JaCadastradoException;
import com.gabriel.estudosolojunit.model.exceptions.NaoEncontradoException;
import com.gabriel.estudosolojunit.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class ProdutoServiceTest {

  public static final String NOME = "Computador";
  public static final String DESCRICAO = "PC Gamer Mancer, Ryzen 7 5700G, 16GB DDR4, SSD 240GB, HD 1TB, Fonte 500W 80 Plus";
  public static final double VALOR = 3142.90;
  public static final Status STATUS = Status.DISPONIVEL;
  public static final long ID = 1L;
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
    when(repository.findAll()).thenReturn(List.of(produto));
    when(mapper.map(any(), any())).thenReturn(produtoDTO);

    List<ProdutoDTO> response = service.listarTodos();

    assertEquals(response.size(), 1);

    assertEquals(response.get(0).getClass(), ProdutoDTO.class);
    assertEquals(response.get(0).getId(), ID);
    assertEquals(response.get(0).getNome(), NOME);
    assertEquals(response.get(0).getDescricao(), DESCRICAO);
    assertEquals(response.get(0).getValor(), VALOR);
  }

  @Test
  void whenListarPorIdThenReturnProdutoDTO() {
    when(repository.findById(any())).thenReturn(Optional.of(produto));
    when(mapper.map(any(), any())).thenReturn(produtoDTO);

    ProdutoDTO response = service.listarPorId(anyLong());

    assertNotNull(response);
    assertEquals(response.getClass(), ProdutoDTO.class);
    assertEquals(response.getId(), ID);
    assertEquals(response.getNome(), NOME);
    assertEquals(response.getValor(), VALOR);
    assertEquals(response.getDescricao(), DESCRICAO);
    assertEquals(response.getStatus(), STATUS);
  }

  @Test
  void whenListarPorIdThenThrowProdutoNaoEncontradoException() {
    when(repository.findById(any())).thenReturn(Optional.empty());

    assertThrows(NaoEncontradoException.class, () -> {
      service.listarPorId(ID);
    });

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
    when(repository.findByNome(any())).thenReturn(Optional.of(produto));

    assertThrows(JaCadastradoException.class, () -> {
      service.adicionar(produtoDTO);
    });

  }
  @Test
  void whenEditarThenModificaProduto() {
    when(repository.findById(any())).thenReturn(Optional.of(produto));
    when(repository.save(any())).thenReturn(produto);

    Produto response = service.editar(produtoDTO);

    assertNotNull(response);
    assertEquals(Produto.class, response.getClass());

    assertEquals(produtoDTO.getId(), response.getId());
    assertEquals(produtoDTO.getNome(), response.getNome());
    assertEquals(produtoDTO.getDescricao(), response.getDescricao());
    assertEquals(produtoDTO.getStatus(), response.getStatus());
  }

  @Test
  void whenEditarThenThrowProdutoNaoEncontradoException() {
    when(repository.findById(any())).thenReturn(Optional.empty());

    assertThrows(NaoEncontradoException.class, () -> {
      service.editar(produtoDTO);
    });

  }
  @Test
  void whenExcluirThenDeleteSuccessfuly() {
    when(repository.findById(any())).thenReturn(Optional.of(produto));
    doNothing().when(repository).deleteById(anyLong());

    service.excluir(produto.getId());

    verify(repository, times(1)).deleteById(anyLong());
  }

  @Test
  void whenExcluirThenThrowProdutoNaoEncontradoException() {
    when(repository.findById(anyLong())).thenReturn(Optional.empty());

    assertThrows(NaoEncontradoException.class, () -> {
      service.excluir(ID);
    });

  }

  private void startProduto() {
    produto = new Produto(ID, NOME, DESCRICAO, VALOR, STATUS);
    produtoDTO = new ProdutoDTO(ID, NOME, DESCRICAO, VALOR, STATUS);

    repository.save(produto);
  }
}