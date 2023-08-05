package com.gabriel.estudosolojunit.service;

import com.gabriel.estudosolojunit.model.dto.ProdutoDTO;
import com.gabriel.estudosolojunit.model.entities.Produto;
import com.gabriel.estudosolojunit.model.exceptions.JaCadastradoException;
import com.gabriel.estudosolojunit.model.exceptions.NaoEncontradoException;
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
    verificaSeProdutoExiste(id);
    return mapper.map(repository.findById(id), ProdutoDTO.class);
  }

  public ProdutoDTO adicionar(ProdutoDTO dto) {
    Optional<Produto> optional = repository.findByNome(dto.getNome());
    if(optional.isPresent()) {
      throw new JaCadastradoException("Produto já cadastrado");
    }

    Produto produto = mapper.map(dto, Produto.class);
    repository.save(produto);
    return dto;
  }

  public Produto editar(ProdutoDTO obj) {
    verificaSeProdutoExiste(obj.getId());

    return repository.save(mapper.map(obj, Produto.class));
  }

  public void excluir(Long id) {
    verificaSeProdutoExiste(id);

    repository.deleteById(id);
  }

  private void verificaSeProdutoExiste(Long id) {
    repository.findById(id).orElseThrow(() -> new NaoEncontradoException("Produto não encontrado"));
  }
}
