package com.ac2.petcare.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/**
 * Vacina — parte do prontuário do animal.
 */
@Entity
@Table(name = "vacina")
public class Vacina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome da vacina é obrigatório")
    @Column(nullable = false)
    private String nome;

    @NotNull(message = "Data de aplicação é obrigatória")
    @Column(name = "data_aplicacao", nullable = false)
    private LocalDate dataAplicacao;

    @Column(name = "data_reforco")
    private LocalDate dataReforco;

    @Column(name = "lote")
    private String lote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consulta_id", nullable = false)
    private Consulta consulta;

    public Vacina() {}

    public Vacina(String nome, LocalDate dataAplicacao, LocalDate dataReforco, String lote, Consulta consulta) {
        this.nome = nome;
        this.dataAplicacao = dataAplicacao;
        this.dataReforco = dataReforco;
        this.lote = lote;
        this.consulta = consulta;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public LocalDate getDataAplicacao() { return dataAplicacao; }
    public void setDataAplicacao(LocalDate dataAplicacao) { this.dataAplicacao = dataAplicacao; }
    public LocalDate getDataReforco() { return dataReforco; }
    public void setDataReforco(LocalDate dataReforco) { this.dataReforco = dataReforco; }
    public String getLote() { return lote; }
    public void setLote(String lote) { this.lote = lote; }
    public Consulta getConsulta() { return consulta; }
    public void setConsulta(Consulta consulta) { this.consulta = consulta; }

    @Override
    public String toString() {
        return "Vacina{id=" + id + ", nome='" + nome + "', dataAplicacao=" + dataAplicacao + "}";
    }
}
