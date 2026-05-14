package com.ac2.projetos.controller;

import com.ac2.projetos.dto.FuncionarioRequest;
import com.ac2.projetos.dto.FuncionarioResponse;
import com.ac2.projetos.service.FuncionarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller de Funcionários
 * Base URL: /api/funcionarios
 */
@RestController
@RequestMapping("/api/funcionarios")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    /** GET /api/funcionarios */
    @GetMapping
    public ResponseEntity<List<FuncionarioResponse>> listar() {
        return ResponseEntity.ok(funcionarioService.listarTodos());
    }

    /** GET /api/funcionarios/{id} */
    @GetMapping("/{id}")
    public ResponseEntity<FuncionarioResponse> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(funcionarioService.buscarPorId(id));
    }

    /** POST /api/funcionarios — cadastra funcionário */
    @PostMapping
    public ResponseEntity<FuncionarioResponse> cadastrar(@Valid @RequestBody FuncionarioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(funcionarioService.cadastrar(request));
    }

    /** GET /api/funcionarios/setor/{setorId} — lista funcionários de um setor */
    @GetMapping("/setor/{setorId}")
    public ResponseEntity<List<FuncionarioResponse>> listarPorSetor(@PathVariable Integer setorId) {
        return ResponseEntity.ok(funcionarioService.listarPorSetor(setorId));
    }
}
