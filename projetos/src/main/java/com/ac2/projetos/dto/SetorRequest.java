package com.ac2.projetos.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO para cadastro de Setor.
 */
public record SetorRequest(
    @NotBlank(message = "Nome do setor é obrigatório")
    String nome
) {}
