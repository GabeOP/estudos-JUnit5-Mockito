package com.gabriel.estudosolojunit.service;

import com.gabriel.estudosolojunit.model.entities.Produto;
import com.gabriel.estudosolojunit.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

  @Autowired
  ProdutoRepository repository;

  public Produto criar(Produto produto) {
    repository.save(produto);
    return produto;
  }
}
