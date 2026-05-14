package com.ac2.projetos.service;

import com.ac2.projetos.dto.SetorRequest;
import com.ac2.projetos.dto.SetorResponse;
import com.ac2.projetos.entity.Setor;
import com.ac2.projetos.exception.RecursoNaoEncontradoException;
import com.ac2.projetos.exception.RegraDeNegocioException;
import com.ac2.projetos.repository.SetorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SetorService {

    private final SetorRepository setorRepository;

    @Transactional(readOnly = true)
    public List<SetorResponse> listarTodosComFuncionarios() {
        return setorRepository.findAllWithFuncionarios().stream()
            .map(SetorResponse::from)
            .toList();
    }

    @Transactional(readOnly = true)
    public SetorResponse buscarPorId(Integer id) {
        Setor setor = setorRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Setor não encontrado com id: " + id));
        return SetorResponse.from(setor);
    }

    /**
     * Cadastra um setor.
     * REGRA: nome do setor deve ser único.
     */
    @Transactional
    public SetorResponse cadastrar(SetorRequest request) {
        if (setorRepository.existsByNomeIgnoreCase(request.nome())) {
            throw new RegraDeNegocioException("Já existe um setor com o nome '" + request.nome() + "'");
        }
        Setor setor = new Setor(request.nome());
        Setor salvo = setorRepository.save(setor);
        log.info("Setor cadastrado: {}", salvo.getNome());
        return SetorResponse.from(salvo);
    }
}
