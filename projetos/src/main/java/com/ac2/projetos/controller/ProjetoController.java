package com.ac2.projetos.controller;

import com.ac2.projetos.dto.ProjetoRequest;
import com.ac2.projetos.dto.ProjetoResponse;
import com.ac2.projetos.service.ProjetoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller de Projetos — expõe os endpoints REST.
 *
 * Base URL: /api/projetos
 */
@RestController
@RequestMapping("/api/projetos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Permite acesso do frontend local
public class ProjetoController {

    private final ProjetoService projetoService;

    /** GET /api/projetos — lista todos os projetos */
    @GetMapping
    public ResponseEntity<List<ProjetoResponse>> listar() {
        return ResponseEntity.ok(projetoService.listarTodos());
    }

    /** POST /api/projetos — cadastra um novo projeto */
    @PostMapping
    public ResponseEntity<ProjetoResponse> criar(@Valid @RequestBody ProjetoRequest request) {
        ProjetoResponse response = projetoService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /** GET /api/projetos/{id} — busca projeto pelo ID com funcionários */
    @GetMapping("/{id}")
    public ResponseEntity<ProjetoResponse> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(projetoService.buscarPorIdComFuncionarios(id));
    }

    /**
     * GET /api/projetos/periodo?inicio=2024-01-01&fim=2024-12-31
     * Busca projetos dentro de um período
     */
    @GetMapping("/periodo")
    public ResponseEntity<List<ProjetoResponse>> buscarPorPeriodo(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim
    ) {
        return ResponseEntity.ok(projetoService.buscarPorPeriodo(inicio, fim));
    }

    /**
     * GET /api/projetos/funcionario/{funcionarioId}
     * Lista todos os projetos de um funcionário
     */
    @GetMapping("/funcionario/{funcionarioId}")
    public ResponseEntity<List<ProjetoResponse>> buscarPorFuncionario(@PathVariable Integer funcionarioId) {
        return ResponseEntity.ok(projetoService.buscarPorFuncionario(funcionarioId));
    }

    /**
     * POST /api/projetos/{projetoId}/funcionarios/{funcionarioId}
     * Vincula um funcionário ao projeto
     */
    @PostMapping("/{projetoId}/funcionarios/{funcionarioId}")
    public ResponseEntity<ProjetoResponse> vincularFuncionario(
        @PathVariable Integer projetoId,
        @PathVariable Integer funcionarioId
    ) {
        return ResponseEntity.ok(projetoService.vincularFuncionario(projetoId, funcionarioId));
    }

    /**
     * DELETE /api/projetos/{projetoId}/funcionarios/{funcionarioId}
     * Remove vínculo entre funcionário e projeto
     */
    @DeleteMapping("/{projetoId}/funcionarios/{funcionarioId}")
    public ResponseEntity<ProjetoResponse> desvincularFuncionario(
        @PathVariable Integer projetoId,
        @PathVariable Integer funcionarioId
    ) {
        return ResponseEntity.ok(projetoService.desvincularFuncionario(projetoId, funcionarioId));
    }
}
