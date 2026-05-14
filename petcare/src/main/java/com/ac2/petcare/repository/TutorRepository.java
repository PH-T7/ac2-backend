package com.ac2.petcare.repository;

import com.ac2.petcare.entity.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, Long> {
    Optional<Tutor> findByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCase(String email);
}
