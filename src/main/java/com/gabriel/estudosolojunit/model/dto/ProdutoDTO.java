package com.gabriel.estudosolojunit.model.dto;

import com.gabriel.estudosolojunit.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDTO {
  private String nome;
  private String descricao;
  private Double valor;
  private Status status;
}
