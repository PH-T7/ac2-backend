package com.ac2.petcare.controller;

import com.ac2.petcare.dto.TutorRequest;
import com.ac2.petcare.entity.Tutor;
import com.ac2.petcare.service.TutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tutores")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TutorController {

    private final TutorService tutorService;

    @GetMapping
    public ResponseEntity<List<Tutor>> listar() {
        return ResponseEntity.ok(tutorService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tutor> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tutorService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Tutor> cadastrar(@Valid @RequestBody TutorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tutorService.cadastrar(request));
    }
}
