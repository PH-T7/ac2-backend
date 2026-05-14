package com.ac2.projetos.repository;

import com.ac2.projetos.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository do Funcionario
 */
@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {

    /**
     * Busca funcionários pelo ID do setor
     */
    List<Funcionario> findBySetorId(Integer setorId);

    /**
     * Verifica se já existe um funcionário com esse nome no mesmo setor
     */
    boolean existsByNomeAndSetorId(String nome, Integer setorId);

    /**
     * Busca funcionários pelo nome (ignorando case)
     */
    @Query("SELECT f FROM Funcionario f WHERE LOWER(f.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Funcionario> findByNomeContaining(@Param("nome") String nome);
}
