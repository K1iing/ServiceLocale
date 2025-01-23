package com.mysql.model.atendimentos;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ListagemMeusAtendimentos(
        Long id,
        ProfissionalListagemDTO profissional,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime dataAtendimento,
        String descricao,
        StatusEnum status_atendimentos
) {
}
