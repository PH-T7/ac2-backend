package com.ac2.projetos.service;

import com.ac2.projetos.dto.FuncionarioRequest;
import com.ac2.projetos.dto.FuncionarioResponse;
import com.ac2.projetos.entity.Funcionario;
import com.ac2.projetos.entity.Setor;
import com.ac2.projetos.exception.RecursoNaoEncontradoException;
import com.ac2.projetos.exception.RegraDeNegocioException;
import com.ac2.projetos.repository.FuncionarioRepository;
import com.ac2.projetos.repository.SetorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final SetorRepository setorRepository;

    @Transactional(readOnly = true)
    public List<FuncionarioResponse> listarTodos() {
        return funcionarioRepository.findAll().stream()
            .map(FuncionarioResponse::from)
            .toList();
    }

    @Transactional(readOnly = true)
    public FuncionarioResponse buscarPorId(Integer id) {
        return funcionarioRepository.findById(id)
            .map(FuncionarioResponse::from)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Funcionário não encontrado com id: " + id));
    }

    /**
     * Cadastra um funcionário.
     * REGRA: o setor deve existir antes de cadastrar o funcionário.
     */
    @Transactional
    public FuncionarioResponse cadastrar(FuncionarioRequest request) {
        Setor setor = setorRepository.findById(request.setorId())
            .orElseThrow(() -> new RecursoNaoEncontradoException("Setor não encontrado com id: " + request.setorId()));

        // Regra: sem duplicata de nome no mesmo setor
        if (funcionarioRepository.existsByNomeAndSetorId(request.nome(), request.setorId())) {
            throw new RegraDeNegocioException(
                "Já existe um funcionário com o nome '" + request.nome() + "' no setor '" + setor.getNome() + "'"
            );
        }

        Funcionario f = new Funcionario(request.nome());
        f.setSetor(setor);
        Funcionario salvo = funcionarioRepository.save(f);
        log.info("Funcionário cadastrado: {} no setor {}", salvo.getNome(), setor.getNome());
        return FuncionarioResponse.from(salvo);
    }

    @Transactional(readOnly = true)
    public List<FuncionarioResponse> listarPorSetor(Integer setorId) {
        if (!setorRepository.existsById(setorId)) {
            throw new RecursoNaoEncontradoException("Setor não encontrado com id: " + setorId);
        }
        return funcionarioRepository.findBySetorId(setorId).stream()
            .map(FuncionarioResponse::from)
            .toList();
    }
}
