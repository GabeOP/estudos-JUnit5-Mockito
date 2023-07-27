package com.gabriel.estudosolojunit.controller;

import com.gabriel.estudosolojunit.model.entities.Produto;
import com.gabriel.estudosolojunit.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

  @Autowired
  ProdutoService service;

  @PostMapping
  public Produto adicionar(@Valid @RequestBody Produto produto) {
    service.criar(produto);
    return produto;
  }
}
