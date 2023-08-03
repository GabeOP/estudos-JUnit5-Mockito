package com.gabriel.estudosolojunit.repository;

import com.gabriel.estudosolojunit.model.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
