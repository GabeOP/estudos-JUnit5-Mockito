package com.gabriel.estudosolojunit.model.exceptions;

public class ProdutoNaoEncontradoException extends RuntimeException{

  public ProdutoNaoEncontradoException(String msg) {
    super(msg);
  }
}
