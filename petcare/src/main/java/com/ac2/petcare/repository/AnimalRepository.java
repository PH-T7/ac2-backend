package com.ac2.petcare.repository;

import com.ac2.petcare.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

    List<Animal> findByTutorId(Long tutorId);

    @Query("SELECT a FROM Animal a WHERE LOWER(a.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Animal> findByNomeContaining(@Param("nome") String nome);

    List<Animal> findByEspecieIgnoreCase(String especie);
}
