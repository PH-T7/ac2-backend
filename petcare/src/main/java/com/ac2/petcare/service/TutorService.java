package com.ac2.petcare.service;

import com.ac2.petcare.dto.TutorRequest;
import com.ac2.petcare.entity.Tutor;
import com.ac2.petcare.exception.RecursoNaoEncontradoException;
import com.ac2.petcare.exception.RegraDeNegocioException;
import com.ac2.petcare.repository.TutorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TutorService {

    private final TutorRepository tutorRepository;

    public TutorService(TutorRepository tutorRepository) {
        this.tutorRepository = tutorRepository;
    }

    @Transactional
    public Tutor cadastrar(TutorRequest request) {
        if (request.email() != null && tutorRepository.existsByEmailIgnoreCase(request.email())) {
            throw new RegraDeNegocioException("Já existe um tutor com o email: " + request.email());
        }
        return tutorRepository.save(new Tutor(request.nome(), request.telefone(), request.email()));
    }

    @Transactional(readOnly = true)
    public List<Tutor> listarTodos() {
        return tutorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Tutor buscarPorId(Long id) {
        return tutorRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Tutor não encontrado com id: " + id));
    }
}
