package com.mysql.repository;

import com.mysql.model.atendimentos.Atendimentos;
import com.mysql.model.client.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AtendimentosRepository extends JpaRepository<Atendimentos, Long> {

    List<Atendimentos> findByClienteId(Long id);
    List<Atendimentos> findByProfissionalId(Long id);
    List<Atendimentos> findByClienteEmail(String email);
}
