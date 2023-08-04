package com.gabriel.estudosolojunit.service;

import com.gabriel.estudosolojunit.model.dto.CategoriaDTO;
import com.gabriel.estudosolojunit.model.entities.Categoria;
import com.gabriel.estudosolojunit.model.exceptions.NaoEncontradoException;
import com.gabriel.estudosolojunit.repository.CategoriaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

  @Autowired
  CategoriaRepository repository;

  @Autowired
  ModelMapper mapper;

  public List<CategoriaDTO> listarTodos() {
    return repository.findAll()
            .stream().map(x -> mapper.map(x, CategoriaDTO.class)).collect(Collectors.toList());
  }

  public CategoriaDTO listarPorId(Long id) {
    Categoria categoria = repository.findById(id).orElseThrow(() -> new NaoEncontradoException("m"));
    return mapper.map(categoria, CategoriaDTO.class);
  }
}
