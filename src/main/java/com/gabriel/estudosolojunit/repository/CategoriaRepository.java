package com.gabriel.estudosolojunit.repository;

import com.gabriel.estudosolojunit.model.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
  Optional<Categoria> findByNome(String nome);
}
