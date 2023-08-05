package com.gabriel.estudosolojunit.service;

import com.gabriel.estudosolojunit.model.dto.CategoriaDTO;
import com.gabriel.estudosolojunit.model.entities.Categoria;
import com.gabriel.estudosolojunit.model.exceptions.JaCadastradoException;
import com.gabriel.estudosolojunit.model.exceptions.NaoEncontradoException;
import com.gabriel.estudosolojunit.repository.CategoriaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CategoriaServiceTest {

  public static final long ID = 1L;
  public static final String NOME = "Eletrônico";
  Categoria categoria;
  CategoriaDTO categoriaDTO;

  @InjectMocks
  CategoriaService service;

  @Mock
  CategoriaRepository repository;

  @Mock
  ModelMapper mapper;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    iniciaCategoria();
  }
  @Test
  void whenListarTodosThenReturnListofCategoriaDTO() {
    when(repository.findAll()).thenReturn(List.of(categoria));
    when(mapper.map(any(), any())).thenReturn(categoriaDTO);

    List<CategoriaDTO> response = service.listarTodos();

    assertEquals(response.get(0).getClass(), CategoriaDTO.class);
    assertEquals(response.size(), 1);
    assertEquals(response.get(0).getId(), ID);
    assertEquals(response.get(0).getNome(), NOME);
  }

  @Test
  void whenListarPorIdThenReturnCategoriaDTO() {
    when(repository.findById(any())).thenReturn(Optional.of(categoria));
    when(mapper.map(any(),any())).thenReturn(categoriaDTO);

    CategoriaDTO response = service.listarPorId(ID);

    assertNotNull(response);
  }

  @Test
  void whenListarPorIdThenThrowNaoEncontradoException() {
    when(repository.findById(any())).thenReturn(Optional.empty());

    assertThrows(NaoEncontradoException.class, () -> {
      service.listarPorId(ID);
    });
  }

  @Test
  void whenAdicionarThenCriaUmaNovaCategoria() {
    when(repository.findById(any())).thenReturn(Optional.empty());
    when(mapper.map(categoriaDTO, Categoria.class)).thenReturn(categoria);

    CategoriaDTO response = service.adicionar(categoriaDTO);

    assertNotNull(response);
    assertEquals(response.getClass(), CategoriaDTO.class);
  }

  @Test
  void whenAdicionarThenThrowJaCadastradoException() {
    when(repository.findById(any())).thenReturn(Optional.of(categoria));

    assertThrows(JaCadastradoException.class, () -> {
      service.adicionar(categoriaDTO);
    });
  }

  private void iniciaCategoria() {
    categoria = new Categoria(ID, NOME);
    categoriaDTO = new CategoriaDTO(ID, NOME);
  }
}