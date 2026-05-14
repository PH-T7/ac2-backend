package com.ac2.projetos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO para cadastro de Funcionário.
 */
public record FuncionarioRequest(
    @NotBlank(message = "Nome do funcionário é obrigatório")
    String nome,

    @NotNull(message = "ID do setor é obrigatório")
    Integer setorId
) {}
