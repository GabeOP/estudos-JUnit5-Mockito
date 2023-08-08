package com.gabriel.estudosolojunit.controller;

import com.gabriel.estudosolojunit.model.dto.CategoriaDTO;
import com.gabriel.estudosolojunit.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
