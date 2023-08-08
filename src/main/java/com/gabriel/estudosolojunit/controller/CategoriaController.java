package com.gabriel.estudosolojunit.controller;

import com.gabriel.estudosolojunit.model.dto.CategoriaDTO;
import com.gabriel.estudosolojunit.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

  @Autowired
  CategoriaService service;

  @GetMapping
  public ResponseEntity<List<CategoriaDTO>> listarTodos() {
    List<CategoriaDTO> lista = service.listarTodos();
    return ResponseEntity.status(HttpStatus.OK).body(lista);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CategoriaDTO> listarPorId(@PathVariable Long id) {
    CategoriaDTO dto = service.listarPorId(id);
    return ResponseEntity.status(HttpStatus.OK).body(dto);
  }

  @PostMapping
  public ResponseEntity<CategoriaDTO> adicionar(@Valid @RequestBody CategoriaDTO obj) {
    CategoriaDTO dto = service.adicionar(obj);
    return ResponseEntity.status(HttpStatus.CREATED).body(dto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<CategoriaDTO> editar(@Valid @PathVariable Long id, @RequestBody CategoriaDTO obj) {
    obj.setId(id);
    CategoriaDTO dto = service.editar(obj);
    return ResponseEntity.status(HttpStatus.OK).body(dto);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> excluir(@PathVariable Long id) {
    service.excluir(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
