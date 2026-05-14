package com.ac2.petcare.dto;

import com.ac2.petcare.entity.Especialidade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VeterinarioRequest(
    @NotBlank(message = "Nome é obrigatório") String nome,
    @NotBlank(message = "CRMV é obrigatório") String crmv,
    @NotNull(message = "Especialidade é obrigatória") Especialidade especialidade
) {}
