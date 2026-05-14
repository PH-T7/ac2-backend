package com.ac2.petcare.service;

import com.ac2.petcare.dto.VeterinarioRequest;
import com.ac2.petcare.entity.Veterinario;
import com.ac2.petcare.exception.RecursoNaoEncontradoException;
import com.ac2.petcare.exception.RegraDeNegocioException;
import com.ac2.petcare.repository.VeterinarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VeterinarioService {

    private final VeterinarioRepository veterinarioRepository;

    public VeterinarioService(VeterinarioRepository veterinarioRepository) {
        this.veterinarioRepository = veterinarioRepository;
    }

    @Transactional
    public Veterinario cadastrar(VeterinarioRequest request) {
        if (veterinarioRepository.existsByCrmv(request.crmv())) {
            throw new RegraDeNegocioException("Já existe um veterinário com o CRMV: " + request.crmv());
        }
        return veterinarioRepository.save(new Veterinario(request.nome(), request.crmv(), request.especialidade()));
    }

    @Transactional(readOnly = true)
    public List<Veterinario> listarTodos() {
        return veterinarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Veterinario buscarPorId(Long id) {
        return veterinarioRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Veterinário não encontrado com id: " + id));
    }
}
