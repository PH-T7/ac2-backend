package com.ac2.projetos.dto;

import com.ac2.projetos.entity.Projeto;

import java.time.LocalDate;
import java.util.List;

/**
 * DTO de resposta do Projeto — inclui lista simplificada de funcionários.
 */
public record ProjetoResponse(
    Integer id,
    String descricao,
    LocalDate dataInicio,
    LocalDate dataFim,
    List<FuncionarioResumo> funcionarios
) {
    public record FuncionarioResumo(Integer id, String nome, String setor) {}

    /** Factory method: converte Projeto (entidade) -> ProjetoResponse (DTO) */
    public static ProjetoResponse from(Projeto p) {
        List<FuncionarioResumo> funcs = p.getFuncionarios().stream()
            .map(f -> new FuncionarioResumo(
                f.getId(),
                f.getNome(),
                f.getSetor() != null ? f.getSetor().getNome() : null
            ))
            .toList();
        return new ProjetoResponse(p.getId(), p.getDescricao(), p.getDataInicio(), p.getDataFim(), funcs);
    }
}
