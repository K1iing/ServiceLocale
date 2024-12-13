package com.mysql.repository;

import com.mysql.model.profissional.Especialidade;
import com.mysql.model.profissional.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {
    boolean existsByNome(String nome);
    boolean existsByEspecialidade(Especialidade especialidade);
}
