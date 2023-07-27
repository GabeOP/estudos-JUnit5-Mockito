package com.gabriel.estudosolojunit.service;

import com.gabriel.estudosolojunit.model.dto.ProdutoDTO;
import com.gabriel.estudosolojunit.model.entities.Produto;
import com.gabriel.estudosolojunit.model.exceptions.ProdutoNaoEncontradoException;
import com.gabriel.estudosolojunit.repository.ProdutoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService {


  @Autowired
  ProdutoRepository repository;

  @Autowired
  ModelMapper mapper;

  public List<ProdutoDTO> listarTodos() {
    List<ProdutoDTO> dto = repository.findAll()
            .stream().map(x -> mapper.map(x, ProdutoDTO.class)).collect(Collectors.toList());
    return dto;
  }

  public ProdutoDTO listarPorId(Long id) {
    Produto entity = repository.findById(id).orElseThrow(() -> {
      throw new ProdutoNaoEncontradoException("Produto não encontrado");
    });
    return mapper.map(entity, ProdutoDTO.class);
  }

  public Produto adicionar(Produto produto) {
    repository.save(produto);
    return produto;
  }
}
