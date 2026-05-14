package com.ac2.petcare.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AnimalRequest(
    @NotBlank(message = "Nome é obrigatório") String nome,
    @NotBlank(message = "Espécie é obrigatória") String especie,
    String raca,
    @NotNull @Positive(message = "Idade deve ser positiva") Integer idade,
    @NotNull(message = "ID do tutor é obrigatório") Long tutorId
) {}
