package com.gabriel.estudosolojunit.service;

import com.gabriel.estudosolojunit.model.entities.Produto;
import com.gabriel.estudosolojunit.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

  @Autowired
  ProdutoRepository repository;

  public List<Produto> listarTodos() {
    return repository.findAll();
  }
  public Produto adicionar(Produto produto) {
    repository.save(produto);
    return produto;
  }
}
