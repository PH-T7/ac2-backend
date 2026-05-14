package com.ac2.petcare.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record VacinaRequest(
    @NotBlank String nome,
    @NotNull LocalDate dataAplicacao,
    LocalDate dataReforco,
    String lote,
    @NotNull Long consultaId
) {}
