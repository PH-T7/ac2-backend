package com.ac2.petcare.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

/**
 * Tutor: dono do animal.
 */
@Entity
@Table(name = "tutor")
public class Tutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do tutor é obrigatório")
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "Telefone é obrigatório")
    @Column(nullable = false)
    private String telefone;

    @Email(message = "Email inválido")
    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "tutor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Animal> animais = new ArrayList<>();

    public Tutor() {}

    public Tutor(String nome, String telefone, String email) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public List<Animal> getAnimais() { return animais; }
    public void setAnimais(List<Animal> animais) { this.animais = animais; }

    @Override
    public String toString() {
        return "Tutor{id=" + id + ", nome='" + nome + "'}";
    }
}
