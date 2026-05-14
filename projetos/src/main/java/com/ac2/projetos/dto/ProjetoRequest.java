package com.ac2.projetos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * DTO para criação e atualização de Projeto.
 * Recebe apenas os dados necessários, sem expor a entidade JPA.
 */
public record ProjetoRequest(
    @NotBlank(message = "Descrição é obrigatória")
    String descricao,

    @NotNull(message = "Data de início é obrigatória")
    LocalDate dataInicio,

    @NotNull(message = "Data fim é obrigatória")
    LocalDate dataFim
) {}
