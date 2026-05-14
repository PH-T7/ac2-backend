package com.ac2.projetos.dto;

import com.ac2.projetos.entity.Setor;
import java.util.List;

/**
 * DTO de resposta do Setor com lista de funcionários.
 */
public record SetorResponse(
    Integer id,
    String nome,
    List<FuncionarioResumo> funcionarios
) {
    public record FuncionarioResumo(Integer id, String nome) {}

    public static SetorResponse from(Setor s) {
        List<FuncionarioResumo> funcs = s.getFuncionarios().stream()
            .map(f -> new FuncionarioResumo(f.getId(), f.getNome()))
            .toList();
        return new SetorResponse(s.getId(), s.getNome(), funcs);
    }
}
