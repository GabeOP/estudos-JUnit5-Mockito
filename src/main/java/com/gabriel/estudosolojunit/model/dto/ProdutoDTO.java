package com.gabriel.estudosolojunit.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gabriel.estudosolojunit.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDTO {
  private String nome;
  private String descricao;
  private Double valor;
  private Status status;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String data_modificacao = formatarData(LocalDateTime.now());

  private static String formatarData(LocalDateTime data) {
    // Define o padrão de formatação desejado
    String padrao = "dd/MM/yyyy HH:mm:ss";

    // Cria um DateTimeFormatter com o padrão desejado
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(padrao);

    // Formata a data e hora usando o formatter
    return data.format(formatter);
  }
}
