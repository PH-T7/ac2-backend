package com.ac2.projetos.exception;

/**
 * Lançada quando um recurso não é encontrado no banco de dados.
 * Resulta em HTTP 404.
 */
public class RecursoNaoEncontradoException extends RuntimeException {
    public RecursoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
