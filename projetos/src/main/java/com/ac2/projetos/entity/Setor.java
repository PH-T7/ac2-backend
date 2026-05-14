package com.ac2.projetos.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Entidade Setor
 * Relacionamento: Setor (1) ---> (0..*) Funcionario
 */
@Entity
@Table(name = "setor")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "funcionarios")
public class Setor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Nome do setor é obrigatório")
    @Column(nullable = false, unique = true)
    private String nome;

    // Um setor possui vários funcionários
    @OneToMany(mappedBy = "setor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Funcionario> funcionarios = new ArrayList<>();

    public Setor(String nome) {
        this.nome = nome;
    }
}
