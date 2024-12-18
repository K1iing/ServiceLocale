package com.mysql.model.atendimentos;

import com.mysql.model.profissional.Especialidade;

public record ProfissionalListagemDTO(Long id, String nome, Especialidade especialidade) {
}
