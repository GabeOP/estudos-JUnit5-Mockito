package com.gabriel.estudosolojunit.controller;

import com.gabriel.estudosolojunit.model.dto.ProdutoDTO;
import com.gabriel.estudosolojunit.service.ProdutoService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

  @Autowired
  ModelMapper mapper;

  @Autowired
  ProdutoService service;

  @GetMapping
  public ResponseEntity<List<ProdutoDTO>> listarTodos() {
    List<ProdutoDTO> lista = service.listarTodos();
    return ResponseEntity.status(HttpStatus.OK).body(lista);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProdutoDTO> listarPorId(@PathVariable Long id) {
    ProdutoDTO dto = service.listarPorId(id);
    return ResponseEntity.status(HttpStatus.OK).body(dto);
  }

  @PostMapping
  public ResponseEntity<ProdutoDTO> adicionar(@Valid @RequestBody ProdutoDTO produto) {
    ProdutoDTO dto = service.adicionar(produto);
    return ResponseEntity.status(HttpStatus.CREATED).body(dto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProdutoDTO> editar(@Valid @PathVariable Long id, @RequestBody ProdutoDTO produto) {
    ProdutoDTO dto = service.editar(id, produto);
    return ResponseEntity.status(HttpStatus.OK).body(dto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> excluir(@PathVariable Long id) {
    service.excluir(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
