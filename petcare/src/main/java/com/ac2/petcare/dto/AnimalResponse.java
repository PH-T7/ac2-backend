package com.ac2.petcare.dto;

import com.ac2.petcare.entity.Animal;

public record AnimalResponse(
    Long id, String nome, String especie, String raca, Integer idade,
    Long tutorId, String tutorNome
) {
    public static AnimalResponse from(Animal a) {
        return new AnimalResponse(
            a.getId(), a.getNome(), a.getEspecie(), a.getRaca(), a.getIdade(),
            a.getTutor() != null ? a.getTutor().getId() : null,
            a.getTutor() != null ? a.getTutor().getNome() : null
        );
    }
}
