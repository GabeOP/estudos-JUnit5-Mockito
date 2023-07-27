package com.gabriel.estudosolojunit.controller;

import com.gabriel.estudosolojunit.model.entities.Produto;
import com.gabriel.estudosolojunit.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

  @Autowired
  ProdutoService service;

  @GetMapping
  public ResponseEntity<List<Produto>> listarTodos() {
    List<Produto> lista = service.listarTodos();
    return ResponseEntity.status(HttpStatus.OK).body(lista);
  }

  @PostMapping
  public ResponseEntity<Produto> adicionar(@Valid @RequestBody Produto produto) {
    service.adicionar(produto);
    return ResponseEntity.status(HttpStatus.CREATED).body(produto);
  }
}
