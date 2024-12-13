package com.mysql.repository;

import com.mysql.model.atendimentos.Atendimentos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtendimentosRepository extends JpaRepository<Atendimentos, Long> {
}
