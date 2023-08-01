package com.gabriel.estudosolojunit.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gabriel.estudosolojunit.model.entities.Categoria;
import com.gabriel.estudosolojunit.model.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDTO {
  @NotBlank(message = "Deve ser preenchido")
  private String nome;

  @NotBlank(message = "Deve ser preenchido")
  private String descricao;

  @NotNull(message = "Deve ser preenchido")
  @PositiveOrZero
  private Double valor;

  @NotNull(message = "Deve ser preenchido")
  private Status status;
  private Set<Categoria> categorias;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String data_modificacao = formatarData(LocalDateTime.now());

  public ProdutoDTO(String nome, String descricao, Double valor, Status status) {
    this.nome = nome;
    this.descricao = descricao;
    this.valor = valor;
    this.status = status;
  }

  private static String formatarData(LocalDateTime data) {
    // Define o padrão de formatação desejado
    String padrao = "dd/MM/yyyy HH:mm:ss";

    // Cria um DateTimeFormatter com o padrão desejado
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(padrao);

    // Formata a data e hora usando o formatter
    return data.format(formatter);
  }
}
