package com.gabriel.estudosolojunit.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gabriel.estudosolojunit.model.entities.Produto;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaDTO {

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private Long id;

  @NotBlank(message = "Deve ser preenchido")
  private String nome;
  private Set<Produto> produtos = new HashSet<>();

  public CategoriaDTO(Long id, String nome) {
    this.id = id;
    this.nome = nome;
  }
}
