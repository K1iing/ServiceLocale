package com.mysql.model.atendimentos;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record AtendimentosListagemDTO(
        Long id,
        ClienteListagemDTO cliente,
        ProfissionalListagemDTO profissional,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime dataAtendimento,
        String descricao,
        StatusEnum status_atendimentos

) {
}
