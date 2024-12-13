package com.mysql.model.atendimentos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mysql.model.client.Cliente;
import com.mysql.model.profissional.Profissional;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "atendimentos")
public class Atendimentos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    @ManyToOne
    @JoinColumn(name = "profissional_id")
    private Profissional profissional;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataAtendimento;

    public Atendimentos(Long id, Cliente cliente, Profissional profissional, LocalDateTime dataAtendimento) {
        Id = id;
        this.cliente = cliente;
        this.profissional = profissional;
        this.dataAtendimento = dataAtendimento;
    }

    public Profissional getProfissional() {
        return profissional;
    }

    public Atendimentos() {
    }

    public void setProfissional(Profissional profissional) {
        this.profissional = profissional;
    }

    public LocalDateTime getDataAtendimento() {
        return dataAtendimento;
    }

    public void setDataAtendimento(LocalDateTime dataAtendimento) {
        this.dataAtendimento = dataAtendimento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }


}
