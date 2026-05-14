package com.ac2.petcare.controller;

import com.ac2.petcare.dto.AnimalRequest;
import com.ac2.petcare.dto.AnimalResponse;
import com.ac2.petcare.service.AnimalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/animais")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AnimalController {

    private final AnimalService animalService;

    @GetMapping
    public ResponseEntity<List<AnimalResponse>> listar() {
        return ResponseEntity.ok(animalService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimalResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(animalService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<AnimalResponse> cadastrar(@Valid @RequestBody AnimalRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(animalService.cadastrar(request));
    }

    @GetMapping("/tutor/{tutorId}")
    public ResponseEntity<List<AnimalResponse>> listarPorTutor(@PathVariable Long tutorId) {
        return ResponseEntity.ok(animalService.listarPorTutor(tutorId));
    }
}
