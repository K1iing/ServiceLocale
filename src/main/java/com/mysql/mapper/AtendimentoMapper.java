package com.mysql.mapper;

import com.mysql.model.atendimentos.Atendimentos;
import com.mysql.model.atendimentos.AtendimentosDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AtendimentoMapper {

   Atendimentos toEntity(AtendimentosDTO dto);

    @Mapping(target = "idCliente", source = "cliente.id")
    @Mapping(target = "idProfissional", source = "profissional.id")
    @Mapping(target = "dataAgendada", source = "dataAtendimento")
   AtendimentosDTO toDTO(Atendimentos atendimentos);

    List<AtendimentosDTO> toDTOList(List<Atendimentos> atendimentos);
}

