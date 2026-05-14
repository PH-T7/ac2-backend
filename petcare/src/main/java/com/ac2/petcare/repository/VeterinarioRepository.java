package com.ac2.petcare.repository;

import com.ac2.petcare.entity.Especialidade;
import com.ac2.petcare.entity.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VeterinarioRepository extends JpaRepository<Veterinario, Long> {
    Optional<Veterinario> findByCrmv(String crmv);
    boolean existsByCrmv(String crmv);
    List<Veterinario> findByEspecialidade(Especialidade especialidade);
}
