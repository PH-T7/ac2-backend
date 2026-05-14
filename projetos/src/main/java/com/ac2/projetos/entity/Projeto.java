package com.ac2.projetos.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade Projeto
 * Relacionamento: Projeto (*) <---> (*) Funcionario (many-to-many via tabela projeto_funcionario)
 */
@Entity
@Table(name = "projeto")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "funcionarios")
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Descrição do projeto é obrigatória")
    @Column(nullable = false)
    private String descricao;

    @NotNull(message = "Data de início é obrigatória")
    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @NotNull(message = "Data fim é obrigatória")
    @Column(name = "data_fim", nullable = false)
    private LocalDate dataFim;

    // Projeto tem múltiplos funcionários (tabela de junção criada automaticamente)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "projeto_funcionario",
        joinColumns = @JoinColumn(name = "projeto_id"),
        inverseJoinColumns = @JoinColumn(name = "funcionario_id")
    )
    private List<Funcionario> funcionarios = new ArrayList<>();

    public Projeto(String descricao, LocalDate dataInicio, LocalDate dataFim) {
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }
}
