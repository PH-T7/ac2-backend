package com.ac2.petcare.dto;

import com.ac2.petcare.entity.Consulta;
import com.ac2.petcare.entity.StatusConsulta;

import java.time.LocalDateTime;
import java.util.List;

public record ConsultaResponse(
    Long id,
    LocalDateTime dataHora,
    String motivo,
    String diagnostico,
    String prescricao,
    StatusConsulta status,
    Long animalId,
    String animalNome,
    Long veterinarioId,
    String veterinarioNome,
    String especialidade,
    List<VacinaResumo> vacinas
) {
    public record VacinaResumo(Long id, String nome, String dataAplicacao) {}

    public static ConsultaResponse from(Consulta c) {
        List<VacinaResumo> vacinaList = c.getVacinas().stream()
            .map(v -> new VacinaResumo(v.getId(), v.getNome(), v.getDataAplicacao().toString()))
            .toList();
        return new ConsultaResponse(
            c.getId(), c.getDataHora(), c.getMotivo(), c.getDiagnostico(), c.getPrescricao(), c.getStatus(),
            c.getAnimal() != null ? c.getAnimal().getId() : null,
            c.getAnimal() != null ? c.getAnimal().getNome() : null,
            c.getVeterinario() != null ? c.getVeterinario().getId() : null,
            c.getVeterinario() != null ? c.getVeterinario().getNome() : null,
            c.getVeterinario() != null ? c.getVeterinario().getEspecialidade().name() : null,
            vacinaList
        );
    }
}
