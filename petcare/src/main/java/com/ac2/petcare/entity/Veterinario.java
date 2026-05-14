package com.ac2.petcare.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Veterinário — regra: atende apenas sua especialidade.
 */
@Entity
@Table(name = "veterinario")
public class Veterinario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do veterinário é obrigatório")
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "CRMV é obrigatório")
    @Column(nullable = false, unique = true)
    private String crmv;

    @NotNull(message = "Especialidade é obrigatória")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Especialidade especialidade;

    @OneToMany(mappedBy = "veterinario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Consulta> consultas = new ArrayList<>();

    public Veterinario() {}

    public Veterinario(String nome, String crmv, Especialidade especialidade) {
        this.nome = nome;
        this.crmv = crmv;
        this.especialidade = especialidade;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCrmv() { return crmv; }
    public void setCrmv(String crmv) { this.crmv = crmv; }
    public Especialidade getEspecialidade() { return especialidade; }
    public void setEspecialidade(Especialidade especialidade) { this.especialidade = especialidade; }
    public List<Consulta> getConsultas() { return consultas; }
    public void setConsultas(List<Consulta> consultas) { this.consultas = consultas; }

    @Override
    public String toString() {
        return "Veterinario{id=" + id + ", nome='" + nome + "', especialidade=" + especialidade + "}";
    }
}
