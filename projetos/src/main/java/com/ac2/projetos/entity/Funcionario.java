package com.ac2.projetos.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Entidade Funcionario
 * Relacionamento: Funcionario (0..*) ---> (1) Setor
 * Relacionamento: Funcionario (*) <---> (*) Projeto (many-to-many)
 */
@Entity
@Table(name = "funcionario")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"setor", "projetos"})
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Nome do funcionário é obrigatório")
    @Column(nullable = false)
    private String nome;

    // Muitos funcionários pertencem a um setor
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "setor_id", nullable = false)
    private Setor setor;

    // Funcionário pode participar de múltiplos projetos
    @ManyToMany(mappedBy = "funcionarios", fetch = FetchType.LAZY)
    private List<Projeto> projetos = new ArrayList<>();

    public Funcionario(String nome) {
        this.nome = nome;
    }
}
