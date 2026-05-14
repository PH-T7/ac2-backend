package com.ac2.projetos.controller;

import com.ac2.projetos.dto.SetorRequest;
import com.ac2.projetos.dto.SetorResponse;
import com.ac2.projetos.service.SetorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller de Setores
 * Base URL: /api/setores
 */
@RestController
@RequestMapping("/api/setores")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SetorController {

    private final SetorService setorService;

    /** GET /api/setores — lista todos os setores com funcionários */
    @GetMapping
    public ResponseEntity<List<SetorResponse>> listar() {
        return ResponseEntity.ok(setorService.listarTodosComFuncionarios());
    }

    /** GET /api/setores/{id} — busca setor por ID */
    @GetMapping("/{id}")
    public ResponseEntity<SetorResponse> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(setorService.buscarPorId(id));
    }

    /** POST /api/setores — cadastra novo setor */
    @PostMapping
    public ResponseEntity<SetorResponse> cadastrar(@Valid @RequestBody SetorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(setorService.cadastrar(request));
    }
}
