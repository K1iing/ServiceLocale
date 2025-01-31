package com.mysql.mapper;

import com.mysql.model.profissional.Profissional;
import com.mysql.model.profissional.ProfissionalDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProfissionalMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", source = "email")
    Profissional toEntity(ProfissionalDTO dto);
    ProfissionalDTO toDTO (Profissional profissional);
}
