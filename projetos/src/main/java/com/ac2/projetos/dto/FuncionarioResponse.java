package com.ac2.projetos.dto;

import com.ac2.projetos.entity.Funcionario;

/**
 * DTO de resposta do Funcionário.
 */
public record FuncionarioResponse(
    Integer id,
    String nome,
    Integer setorId,
    String setorNome
) {
    public static FuncionarioResponse from(Funcionario f) {
        return new FuncionarioResponse(
            f.getId(),
            f.getNome(),
            f.getSetor() != null ? f.getSetor().getId() : null,
            f.getSetor() != null ? f.getSetor().getNome() : null
        );
    }
}
