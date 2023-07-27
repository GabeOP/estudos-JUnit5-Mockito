package com.gabriel.estudosolojunit.model.entities;

import com.gabriel.estudosolojunit.model.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "tb_produto")
public class Produto {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  @NotNull(message = "o campo 'nome' não pode ser vazio")
  private String nome;

  @Column
  @NotNull(message = "o campo 'descricao' não pode ser vazio")
  private String descricao;

  @Column
  @NotNull(message = "o campo 'valor' não pode ser vazio")
  @PositiveOrZero
  private Double valor;

  private String data_criacao = formatarData(LocalDateTime.now());
  private String data_modificacao = formatarData(LocalDateTime.now());

  @Enumerated(EnumType.STRING)
  @Column
  @NotNull(message = "o campo 'status' não pode ser vazio")
  private Status status;

  @ManyToMany
  @JoinTable(
          name="tb_produto_categoria",
          joinColumns = @JoinColumn(name = "produto_id"),
          inverseJoinColumns = @JoinColumn(name = "categoria_id")
  )
  private Set<Categoria> categorias = new HashSet<>();

  private static String formatarData(LocalDateTime data) {
    // Define o padrão de formatação desejado
    String padrao = "dd/MM/yyyy HH:mm:ss";

    // Cria um DateTimeFormatter com o padrão desejado
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(padrao);

    // Formata a data e hora usando o formatter
    return data.format(formatter);
  }
}
