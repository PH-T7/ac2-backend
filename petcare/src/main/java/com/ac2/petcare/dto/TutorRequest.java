package com.ac2.petcare.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record TutorRequest(
    @NotBlank(message = "Nome é obrigatório") String nome,
    @NotBlank(message = "Telefone é obrigatório") String telefone,
    @Email(message = "Email inválido") String email
) {}
