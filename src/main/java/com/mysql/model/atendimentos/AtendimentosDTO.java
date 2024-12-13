package com.mysql.model.atendimentos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AtendimentosDTO(
        @NotNull(message = "Não pode ser nulo o IdCliente")
        Long idCliente,
        @NotNull(message = "Não pode ser nulo o IdProfissional")
        Long idProfissional,

        @NotNull
        @Future(message = "A data deve estar no futuro")
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime dataAgendada) {
}
