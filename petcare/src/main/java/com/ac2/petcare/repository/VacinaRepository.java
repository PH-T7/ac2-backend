package com.ac2.petcare.repository;

import com.ac2.petcare.entity.Vacina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacinaRepository extends JpaRepository<Vacina, Long> {

    /**
     * Busca todas as vacinas de um animal (através das consultas)
     */
    @Query("SELECT v FROM Vacina v WHERE v.consulta.animal.id = :animalId ORDER BY v.dataAplicacao DESC")
    List<Vacina> findByAnimalId(@Param("animalId") Long animalId);

    List<Vacina> findByConsultaId(Long consultaId);
}
