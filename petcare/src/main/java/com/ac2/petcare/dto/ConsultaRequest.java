package com.ac2.petcare.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record ConsultaRequest(
    @NotNull(message = "Data/hora é obrigatória") LocalDateTime dataHora,
    @NotBlank(message = "Motivo é obrigatório") String motivo,
    @NotNull(message = "ID do animal é obrigatório") Long animalId,
    @NotNull(message = "ID do veterinário é obrigatório") Long veterinarioId
) {}
