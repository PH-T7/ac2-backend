package com.ac2.petcare.controller;

import com.ac2.petcare.dto.VacinaRequest;
import com.ac2.petcare.entity.Vacina;
import com.ac2.petcare.service.VacinaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vacinas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class VacinaController {

    private final VacinaService vacinaService;

    /** POST /api/vacinas — registra vacina em uma consulta */
    @PostMapping
    public ResponseEntity<Vacina> registrar(@Valid @RequestBody VacinaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vacinaService.registrar(request));
    }

    /** GET /api/vacinas/animal/{animalId} — histórico de vacinação do animal */
    @GetMapping("/animal/{animalId}")
    public ResponseEntity<List<Vacina>> porAnimal(@PathVariable Long animalId) {
        return ResponseEntity.ok(vacinaService.listarPorAnimal(animalId));
    }

    /** GET /api/vacinas/consulta/{consultaId} */
    @GetMapping("/consulta/{consultaId}")
    public ResponseEntity<List<Vacina>> porConsulta(@PathVariable Long consultaId) {
        return ResponseEntity.ok(vacinaService.listarPorConsulta(consultaId));
    }
}
