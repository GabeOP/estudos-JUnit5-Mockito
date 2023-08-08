package com.gabriel.estudosolojunit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabriel.estudosolojunit.model.dto.CategoriaDTO;
import com.gabriel.estudosolojunit.model.entities.Categoria;
import com.gabriel.estudosolojunit.model.exceptions.JaCadastradoException;
import com.gabriel.estudosolojunit.model.exceptions.NaoEncontradoException;
import com.gabriel.estudosolojunit.service.CategoriaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class CategoriaControllerTest {

  public static final long ID = 1L;
  public static final String NOME = "Eletrônico";
  public static final long ID_INEXISTENTE = 2L;
  @InjectMocks
  CategoriaController controller;

  @MockBean
  CategoriaService service;

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  Categoria categoria;
  CategoriaDTO categoriaDTO;
  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    iniciaCategoria();
  }

  @Test
  void whenListarTodosThenReturn200Status() throws Exception{
    when(service.listarTodos()).thenReturn(List.of(categoriaDTO));
    mockMvc.perform(MockMvcRequestBuilders.get("/categoria"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print());
  }

  @Test
  void whenListarPorIdThenReturn200Status() throws Exception {
    when(service.listarPorId(ID)).thenReturn(categoriaDTO);

    mockMvc.perform(MockMvcRequestBuilders.get("/categoria/{ID}", ID))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(NOME))
            .andDo(MockMvcResultHandlers.print());
  }

  @Test
  void whenListarPorIdReturn404Status() throws Exception {
    when(service.listarPorId(ID_INEXISTENTE)).thenThrow(new NaoEncontradoException("Categoria não encontrada"));

    mockMvc.perform(MockMvcRequestBuilders.get("/categoria/{ID_INEXISTENTE}", ID_INEXISTENTE))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andDo(MockMvcResultHandlers.print());
  }

  @Test
  void whenAdicionarThenReturn204Status() throws Exception {
    when(service.adicionar(any())).thenReturn(categoriaDTO);

    mockMvc.perform(MockMvcRequestBuilders.post("/categoria")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(categoriaDTO))
    )
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(NOME))
            .andDo(MockMvcResultHandlers.print());
  }

  @Test
  void whenAdicionarThenReturn422Status() throws Exception{
    when(service.adicionar(any())).thenThrow(new JaCadastradoException("Categoria já cadastrada"));

    mockMvc.perform(MockMvcRequestBuilders.post("/categoria")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(categoriaDTO))
    )
            .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
            .andDo(MockMvcResultHandlers.print());
  }
  private void iniciaCategoria() {
    categoria = new Categoria(ID, NOME);
    categoriaDTO = new CategoriaDTO(ID, NOME);
  }
}