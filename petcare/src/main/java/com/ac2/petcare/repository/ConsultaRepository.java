package com.ac2.petcare.repository;

import com.ac2.petcare.entity.Consulta;
import com.ac2.petcare.entity.StatusConsulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    /**
     * REGRA CRÍTICA: verifica conflito de agenda do veterinário.
     * Considera que cada consulta dura 30 minutos.
     * Não pode agendar se já existe consulta no mesmo horário (± 30 min) para o mesmo vet.
     */
    @Query("""
        SELECT c FROM Consulta c
        WHERE c.veterinario.id = :vetId
        AND c.status != 'CANCELADA'
        AND c.dataHora BETWEEN :inicio AND :fim
        """)
    List<Consulta> findConflitosDeAgenda(
        @Param("vetId") Long vetId,
        @Param("inicio") LocalDateTime inicio,
        @Param("fim") LocalDateTime fim
    );

    /**
     * Busca histórico completo de consultas de um animal
     */
    @Query("SELECT c FROM Consulta c LEFT JOIN FETCH c.vacinas WHERE c.animal.id = :animalId ORDER BY c.dataHora DESC")
    List<Consulta> findHistoricoByAnimalId(@Param("animalId") Long animalId);

    /**
     * Busca consultas de um veterinário
     */
    List<Consulta> findByVeterinarioIdOrderByDataHoraDesc(Long veterinarioId);

    /**
     * Busca consulta por ID com todos os relacionamentos carregados
     */
    @Query("SELECT c FROM Consulta c LEFT JOIN FETCH c.vacinas LEFT JOIN FETCH c.animal LEFT JOIN FETCH c.veterinario WHERE c.id = :id")
    Optional<Consulta> findByIdWithDetails(@Param("id") Long id);

    /**
     * Lista consultas agendadas (futuras) de um veterinário
     */
    @Query("SELECT c FROM Consulta c WHERE c.veterinario.id = :vetId AND c.status = 'AGENDADA' AND c.dataHora >= :agora ORDER BY c.dataHora")
    List<Consulta> findAgendaFutura(@Param("vetId") Long vetId, @Param("agora") LocalDateTime agora);
}
