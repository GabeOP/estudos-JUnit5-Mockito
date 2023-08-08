package com.gabriel.estudosolojunit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabriel.estudosolojunit.model.dto.CategoriaDTO;
import com.gabriel.estudosolojunit.model.entities.Categoria;
import com.gabriel.estudosolojunit.service.CategoriaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class CategoriaControllerTest {

  public static final long ID = 1L;
  public static final String NOME = "Eletrônico";
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

  private void iniciaCategoria() {
    categoria = new Categoria(ID, NOME);
    categoriaDTO = new CategoriaDTO(ID, NOME);
  }
}