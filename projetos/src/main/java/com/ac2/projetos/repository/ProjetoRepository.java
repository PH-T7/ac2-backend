package com.ac2.projetos.repository;

import com.ac2.projetos.entity.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository do Projeto
 * Contém todas as queries JPQL obrigatórias da AC2
 */
@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Integer> {

    /**
     * Busca projeto pelo ID já carregando os funcionários (JOIN FETCH evita N+1 queries)
     */
    @Query("SELECT p FROM Projeto p LEFT JOIN FETCH p.funcionarios WHERE p.id = :id")
    Optional<Projeto> findByIdWithFuncionarios(@Param("id") Integer id);

    /**
     * Busca projetos em um determinado período (dataInicio >= inicio E dataFim <= fim)
     */
    @Query("SELECT p FROM Projeto p WHERE p.dataInicio >= :inicio AND p.dataFim <= :fim")
    List<Projeto> findByPeriodo(@Param("inicio") LocalDate inicio, @Param("fim") LocalDate fim);

    /**
     * Busca todos os projetos que um funcionário específico participa
     */
    @Query("SELECT p FROM Projeto p JOIN p.funcionarios f WHERE f.id = :funcionarioId")
    List<Projeto> findByFuncionarioId(@Param("funcionarioId") Integer funcionarioId);
}
