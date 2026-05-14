package com.ac2.petcare.controller;

import com.ac2.petcare.dto.ConsultaRequest;
import com.ac2.petcare.dto.ConsultaResponse;
import com.ac2.petcare.service.ConsultaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller de Consultas
 * Base URL: /api/consultas
 */
@RestController
@RequestMapping("/api/consultas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ConsultaController {

    private final ConsultaService consultaService;

    /** GET /api/consultas */
    @GetMapping
    public ResponseEntity<List<ConsultaResponse>> listar() {
        return ResponseEntity.ok(consultaService.listarTodas());
    }

    /** POST /api/consultas — agenda nova consulta (com validações de regra de negócio) */
    @PostMapping
    public ResponseEntity<ConsultaResponse> agendar(@Valid @RequestBody ConsultaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(consultaService.agendar(request));
    }

    /** GET /api/consultas/{id} */
    @GetMapping("/{id}")
    public ResponseEntity<ConsultaResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(consultaService.buscarPorId(id));
    }

    /** GET /api/consultas/animal/{animalId} — histórico completo do animal */
    @GetMapping("/animal/{animalId}")
    public ResponseEntity<List<ConsultaResponse>> historicoAnimal(@PathVariable Long animalId) {
        return ResponseEntity.ok(consultaService.historicoAnimal(animalId));
    }

    /** GET /api/consultas/veterinario/{vetId}/agenda — agenda futura do vet */
    @GetMapping("/veterinario/{vetId}/agenda")
    public ResponseEntity<List<ConsultaResponse>> agendaFutura(@PathVariable Long vetId) {
        return ResponseEntity.ok(consultaService.agendaFuturaVeterinario(vetId));
    }

    /**
     * PATCH /api/consultas/{id}/finalizar
     * Body: { "diagnostico": "...", "prescricao": "..." }
     */
    @PatchMapping("/{id}/finalizar")
    public ResponseEntity<ConsultaResponse> finalizar(
        @PathVariable Long id,
        @RequestBody Map<String, String> body
    ) {
        return ResponseEntity.ok(
            consultaService.finalizarConsulta(id, body.get("diagnostico"), body.get("prescricao"))
        );
    }

    /** PATCH /api/consultas/{id}/cancelar */
    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<ConsultaResponse> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(consultaService.cancelarConsulta(id));
    }
}
