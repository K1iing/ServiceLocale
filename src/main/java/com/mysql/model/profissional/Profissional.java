package com.mysql.model.profissional;

import jakarta.persistence.*;

@Entity
@Table(name = "profissionais")
public class Profissional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    public Profissional() {
    }

    public Profissional(String nome, Long id, Especialidade especialidade) {
        this.nome = nome;
        Id = id;
        this.especialidade = especialidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(Especialidade especialidade) {
        this.especialidade = especialidade;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    @Column(nullable = false, unique = true)
    private String nome;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Especialidade especialidade;
}
