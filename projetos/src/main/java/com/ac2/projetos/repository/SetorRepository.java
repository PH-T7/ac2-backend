package com.ac2.projetos.repository;

import com.ac2.projetos.entity.Setor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository do Setor
 */
@Repository
public interface SetorRepository extends JpaRepository<Setor, Integer> {

    /**
     * Lista todos os setores junto com seus funcionários (JOIN FETCH)
     * Evita o problema N+1 queries
     */
    @Query("SELECT DISTINCT s FROM Setor s LEFT JOIN FETCH s.funcionarios")
    List<Setor> findAllWithFuncionarios();

    /**
     * Busca setor pelo nome (ignorando case)
     */
    Optional<Setor> findByNomeIgnoreCase(String nome);

    /**
     * Verifica se já existe um setor com esse nome
     */
    boolean existsByNomeIgnoreCase(String nome);
}
