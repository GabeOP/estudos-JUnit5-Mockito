package com.gabriel.estudosolojunit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabriel.estudosolojunit.model.dto.ProdutoDTO;
import com.gabriel.estudosolojunit.model.entities.Produto;
import com.gabriel.estudosolojunit.model.enums.Status;
import com.gabriel.estudosolojunit.model.exceptions.JaCadastradoException;
import com.gabriel.estudosolojunit.model.exceptions.NaoEncontradoException;
import com.gabriel.estudosolojunit.service.ProdutoService;
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
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;


@SpringBootTest
@AutoConfigureMockMvc
class ProdutoControllerTest {

  public static final String NOME = "Computador";
  public static final String DESCRICAO = "PC Gamer Mancer, Ryzen 7 5700G, 16GB DDR4, SSD 240GB, HD 1TB, Fonte 500W 80 Plus";
  public static final double VALOR = 3142.90;
  public static final Status STATUS = Status.DISPONIVEL;
  public static final long ID = 1L;
  public static final Long ID_INEXISTENTE = 2L;

  Produto produto;
  ProdutoDTO produtoDTO;

  @InjectMocks
  ProdutoController controller;

  @MockBean
  ProdutoService service;

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    startProduto();
  }

  @Test
  void whenListarTodosThenReturn200Status() throws Exception{
    when(service.listarTodos()).thenReturn(List.of(produtoDTO));

    mockMvc.perform(MockMvcRequestBuilders.get("/produto"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].nome").value(NOME))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].descricao").value(DESCRICAO))
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].valor").value(VALOR))
            .andDo(MockMvcResultHandlers.print());
  }

  @Test
  void whenListarPorIdThenReturn404Status() throws Exception {
    when(service.listarPorId(ID_INEXISTENTE)).thenThrow(new NaoEncontradoException("Produto não encontrado"));

    mockMvc.perform(MockMvcRequestBuilders.get("/produto/{idInexistente}", ID_INEXISTENTE)
            .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andDo(MockMvcResultHandlers.print());
  }

  @Test
  void whenAdicionarThenReturn201Status() throws Exception {
    when(service.adicionar(any())).thenReturn(produtoDTO);

    mockMvc.perform(MockMvcRequestBuilders.post("/produto")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(produtoDTO))
            )
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(NOME))
            .andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value(DESCRICAO))
            .andExpect(MockMvcResultMatchers.jsonPath("$.valor").value(VALOR))
            .andDo(MockMvcResultHandlers.print());
  }

  @Test
  void whenAdicionarThenReturn422Status() throws Exception {

    when(service.adicionar(any())).thenThrow(new JaCadastradoException("Produto já cadastrado"));

    mockMvc.perform(MockMvcRequestBuilders.post("/produto")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(produtoDTO))
            )
            .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
            .andDo(MockMvcResultHandlers.print());
  }

  @Test
  void whenEditarThenReturn200Status() throws Exception {
    when(service.editar(any())).thenReturn(produto);

    mockMvc.perform(MockMvcRequestBuilders.put("/produto/{ID}", ID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(produtoDTO))
    )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print());

  }

  @Test
  void whenEditarThenReturn404Status() throws Exception {
    when(service.editar(any())).thenThrow(new NaoEncontradoException("Produto não encontrado"));

    mockMvc.perform(MockMvcRequestBuilders.put("/produto/{ID}", ID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(produtoDTO))
    )
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.jsonPath("$.error").value("Produto não encontrado"))
            .andDo(MockMvcResultHandlers.print());

  }

  @Test
  void whenExcluirThenReturn204Status() throws Exception{

    mockMvc.perform(MockMvcRequestBuilders.delete("/produto/{ID}", ID))
            .andExpect(MockMvcResultMatchers.status().isNoContent())
            .andDo(MockMvcResultHandlers.print());
  }

  @Test
  void whenExcluirThenReturn404Status() throws Exception {
    doThrow(NaoEncontradoException.class).when(service).excluir(ID_INEXISTENTE);

    mockMvc.perform(MockMvcRequestBuilders.delete("/produto/{IdInexistente}", ID_INEXISTENTE))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andDo(MockMvcResultHandlers.print());
  }

  private void startProduto() {
    produto = new Produto(ID, NOME, DESCRICAO, VALOR, STATUS);
    produtoDTO = new ProdutoDTO(ID, NOME, DESCRICAO, VALOR, STATUS);
  }
}