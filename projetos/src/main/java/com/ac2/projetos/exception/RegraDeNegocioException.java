package com.ac2.projetos.exception;

/**
 * Lançada quando uma regra de negócio é violada.
 * Resulta em HTTP 400.
 */
public class RegraDeNegocioException extends RuntimeException {
    public RegraDeNegocioException(String mensagem) {
        super(mensagem);
    }
}
