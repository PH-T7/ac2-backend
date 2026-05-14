package com.ac2.petcare.service;

import com.ac2.petcare.dto.AnimalRequest;
import com.ac2.petcare.dto.AnimalResponse;
import com.ac2.petcare.entity.Animal;
import com.ac2.petcare.entity.Tutor;
import com.ac2.petcare.exception.RecursoNaoEncontradoException;
import com.ac2.petcare.repository.AnimalRepository;
import com.ac2.petcare.repository.TutorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final TutorRepository tutorRepository;

    public AnimalService(AnimalRepository animalRepository, TutorRepository tutorRepository) {
        this.animalRepository = animalRepository;
        this.tutorRepository = tutorRepository;
    }

    @Transactional
    public AnimalResponse cadastrar(AnimalRequest request) {
        Tutor tutor = tutorRepository.findById(request.tutorId())
            .orElseThrow(() -> new RecursoNaoEncontradoException("Tutor não encontrado com id: " + request.tutorId()));
        Animal animal = new Animal(request.nome(), request.especie(), request.raca(), request.idade(), tutor);
        return AnimalResponse.from(animalRepository.save(animal));
    }

    @Transactional(readOnly = true)
    public AnimalResponse buscarPorId(Long id) {
        return animalRepository.findById(id)
            .map(AnimalResponse::from)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Animal não encontrado com id: " + id));
    }

    @Transactional(readOnly = true)
    public List<AnimalResponse> listarTodos() {
        return animalRepository.findAll().stream().map(AnimalResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public List<AnimalResponse> listarPorTutor(Long tutorId) {
        if (!tutorRepository.existsById(tutorId)) {
            throw new RecursoNaoEncontradoException("Tutor não encontrado com id: " + tutorId);
        }
        return animalRepository.findByTutorId(tutorId).stream().map(AnimalResponse::from).toList();
    }
}
