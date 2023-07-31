package com.gabriel.estudosolojunit.service;

import com.gabriel.estudosolojunit.model.dto.ProdutoDTO;
import com.gabriel.estudosolojunit.model.entities.Produto;
import com.gabriel.estudosolojunit.model.exceptions.ProdutoJaCadastradoException;
import com.gabriel.estudosolojunit.model.exceptions.ProdutoNaoEncontradoException;
import com.gabriel.estudosolojunit.repository.ProdutoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

  public ProdutoDTO adicionar(ProdutoDTO dto) {
    Optional<Produto> optional = repository.findByNome(dto.getNome());
    if(optional.isPresent()) {
      throw new ProdutoJaCadastradoException("Produto já cadastrado");
    }

    Produto produto = mapper.map(dto, Produto.class);
    repository.save(produto);
    return dto;
  }


  public ProdutoDTO editar(Long id, ProdutoDTO dto) {

    Produto produto = repository.findById(id).orElseThrow(() -> {
      throw new ProdutoNaoEncontradoException("Produto não encontrado");
    });

    produto.setNome(dto.getNome());
    produto.setDescricao(dto.getDescricao());
    produto.setValor(dto.getValor());
    produto.setStatus(dto.getStatus());
    produto.setData_modificacao(dto.getData_modificacao());

    repository.save(produto);
    return mapper.map(produto, ProdutoDTO.class);

  }

  public void excluir(Long id) {
    repository.findById(id).orElseThrow(() -> {
      throw new ProdutoNaoEncontradoException("Produto não encontrado");
    });

    repository.deleteById(id);
  }
}
