package com.mysql.mapper;

import com.mysql.model.atendimentos.Atendimentos;
import com.mysql.model.atendimentos.AtendimentosDTO;
import com.mysql.model.atendimentos.AtendimentosListagemDTO;
import com.mysql.model.atendimentos.ListagemMeusAtendimentos;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AtendimentoMapper {

    Atendimentos toEntity(AtendimentosDTO dto);


    @Mapping(target = "idProfissional", source = "profissional.id")
    @Mapping(target = "dataAgendada", source = "dataAtendimento")

    AtendimentosDTO toDTO(Atendimentos atendimentos);

    @Mapping(target = "descricao", source = "descricao")
    @Mapping(target = "id", source = "Id")
    List<AtendimentosListagemDTO> toListDTO(List<Atendimentos> dtoLista);


    AtendimentosListagemDTO toListagemDTO(Atendimentos atendimentos);

    @Mapping(target = "dataAtendimento", source = "dataAtendimento")
    @Mapping(target = "id", source = "Id")
    List<ListagemMeusAtendimentos> toListAtendimentosDTO(List<Atendimentos> dtolista);
}

