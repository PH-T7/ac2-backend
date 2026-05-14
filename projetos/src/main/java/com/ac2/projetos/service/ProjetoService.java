package com.ac2.projetos.service;

import com.ac2.projetos.dto.ProjetoRequest;
import com.ac2.projetos.dto.ProjetoResponse;
import com.ac2.projetos.entity.Funcionario;
import com.ac2.projetos.entity.Projeto;
import com.ac2.projetos.exception.RecursoNaoEncontradoException;
import com.ac2.projetos.exception.RegraDeNegocioException;
import com.ac2.projetos.repository.FuncionarioRepository;
import com.ac2.projetos.repository.ProjetoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Service do Projeto — camada de regras de negócio.
 *
 * DIFERENÇA entre Repository e Service (para a prova):
 *  - Repository: só acessa o banco (CRUD + queries)
 *  - Service: contém as regras de negócio, chama o repository, lança exceções de negócio
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProjetoService {

    private final ProjetoRepository projetoRepository;
    private final FuncionarioRepository funcionarioRepository;

    /**
     * Lista todos os projetos (sem funcionários para não sobrecarregar)
     */
    @Transactional(readOnly = true)
    public List<ProjetoResponse> listarTodos() {
        return projetoRepository.findAll().stream()
            .map(ProjetoResponse::from)
            .toList();
    }

    /**
     * Cria um novo projeto com validações de negócio.
     * REGRA: data de início não pode ser depois da data fim.
     */
    @Transactional
    public ProjetoResponse criar(ProjetoRequest request) {
        // Regra de negócio: dataInicio deve ser anterior a dataFim
        if (!request.dataInicio().isBefore(request.dataFim())) {
            throw new RegraDeNegocioException(
                "Data de início (" + request.dataInicio() + ") deve ser anterior à data fim (" + request.dataFim() + ")"
            );
        }

        Projeto projeto = new Projeto(request.descricao(), request.dataInicio(), request.dataFim());
        Projeto salvo = projetoRepository.save(projeto);
        log.info("Projeto criado: id={}, descricao={}", salvo.getId(), salvo.getDescricao());
        return ProjetoResponse.from(salvo);
    }

    /**
     * Busca projeto por ID com seus funcionários (JOIN FETCH).
     */
    @Transactional(readOnly = true)
    public ProjetoResponse buscarPorIdComFuncionarios(Integer id) {
        Projeto projeto = projetoRepository.findByIdWithFuncionarios(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Projeto não encontrado com id: " + id));
        return ProjetoResponse.from(projeto);
    }

    /**
     * Busca projetos em um período.
     * REGRA: período deve ser válido (inicio antes de fim).
     */
    @Transactional(readOnly = true)
    public List<ProjetoResponse> buscarPorPeriodo(LocalDate inicio, LocalDate fim) {
        if (inicio.isAfter(fim)) {
            throw new RegraDeNegocioException("Data início não pode ser posterior à data fim no filtro");
        }
        return projetoRepository.findByPeriodo(inicio, fim).stream()
            .map(ProjetoResponse::from)
            .toList();
    }

    /**
     * Busca todos os projetos de um funcionário específico.
     */
    @Transactional(readOnly = true)
    public List<ProjetoResponse> buscarPorFuncionario(Integer funcionarioId) {
        // Valida se o funcionário existe
        if (!funcionarioRepository.existsById(funcionarioId)) {
            throw new RecursoNaoEncontradoException("Funcionário não encontrado com id: " + funcionarioId);
        }
        return projetoRepository.findByFuncionarioId(funcionarioId).stream()
            .map(ProjetoResponse::from)
            .toList();
    }

    /**
     * Vincula um funcionário a um projeto.
     * REGRA: não permitir vincular o mesmo funcionário duas vezes ao mesmo projeto.
     */
    @Transactional
    public ProjetoResponse vincularFuncionario(Integer projetoId, Integer funcionarioId) {
        Projeto projeto = projetoRepository.findByIdWithFuncionarios(projetoId)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Projeto não encontrado com id: " + projetoId));

        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Funcionário não encontrado com id: " + funcionarioId));

        // Regra: funcionário já está no projeto?
        boolean jaVinculado = projeto.getFuncionarios().stream()
            .anyMatch(f -> f.getId().equals(funcionarioId));
        if (jaVinculado) {
            throw new RegraDeNegocioException(
                "Funcionário '" + funcionario.getNome() + "' já está vinculado ao projeto '" + projeto.getDescricao() + "'"
            );
        }

        projeto.getFuncionarios().add(funcionario);
        Projeto atualizado = projetoRepository.save(projeto);
        log.info("Funcionário {} vinculado ao projeto {}", funcionario.getNome(), projeto.getDescricao());
        return ProjetoResponse.from(atualizado);
    }

    /**
     * Remove um funcionário de um projeto.
     */
    @Transactional
    public ProjetoResponse desvincularFuncionario(Integer projetoId, Integer funcionarioId) {
        Projeto projeto = projetoRepository.findByIdWithFuncionarios(projetoId)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Projeto não encontrado com id: " + projetoId));

        boolean removido = projeto.getFuncionarios().removeIf(f -> f.getId().equals(funcionarioId));
        if (!removido) {
            throw new RegraDeNegocioException("Funcionário id=" + funcionarioId + " não está vinculado a este projeto");
        }

        return ProjetoResponse.from(projetoRepository.save(projeto));
    }
}
