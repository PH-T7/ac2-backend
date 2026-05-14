package com.ac2.petcare.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Consulta — regras: sem conflito de agenda, veterinário atende sua especialidade.
 */
@Entity
@Table(name = "consulta")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Data/hora é obrigatória")
    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    @Column(length = 1000)
    private String motivo;

    @Column(name = "diagnostico", length = 2000)
    private String diagnostico;

    @Column(name = "prescricao", length = 2000)
    private String prescricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusConsulta status = StatusConsulta.AGENDADA;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal_id", nullable = false)
    private Animal animal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veterinario_id", nullable = false)
    private Veterinario veterinario;

    @OneToMany(mappedBy = "consulta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Vacina> vacinas = new ArrayList<>();

    public Consulta() {}

    public Consulta(LocalDateTime dataHora, String motivo, Animal animal, Veterinario veterinario) {
        this.dataHora = dataHora;
        this.motivo = motivo;
        this.animal = animal;
        this.veterinario = veterinario;
    }

    public Long getId() { return id; }
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }
    public String getPrescricao() { return prescricao; }
    public void setPrescricao(String prescricao) { this.prescricao = prescricao; }
    public StatusConsulta getStatus() { return status; }
    public void setStatus(StatusConsulta status) { this.status = status; }
    public Animal getAnimal() { return animal; }
    public void setAnimal(Animal animal) { this.animal = animal; }
    public Veterinario getVeterinario() { return veterinario; }
    public void setVeterinario(Veterinario veterinario) { this.veterinario = veterinario; }
    public List<Vacina> getVacinas() { return vacinas; }
    public void setVacinas(List<Vacina> vacinas) { this.vacinas = vacinas; }

    @Override
    public String toString() {
        return "Consulta{id=" + id + ", dataHora=" + dataHora + ", status=" + status + "}";
    }
}
