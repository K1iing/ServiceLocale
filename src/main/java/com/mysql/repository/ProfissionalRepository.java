package com.mysql.repository;

import com.mysql.model.atendimentos.Atendimentos;
import com.mysql.model.atendimentos.AtendimentosListagemDTO;
import com.mysql.model.profissional.Especialidade;
import com.mysql.model.profissional.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {
    boolean existsByNome(String nome);
    boolean existsByEspecialidade(Especialidade especialidade);


}
