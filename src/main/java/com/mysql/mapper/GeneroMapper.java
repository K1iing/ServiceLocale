package com.mysql.mapper;

import com.mysql.model.client.Cliente;
import com.mysql.model.client.ClienteDTO;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring", collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface GeneroMapper extends BaseMapper<Cliente, ClienteDTO> {

    GeneroMapper INSTANCE = Mappers.getMapper(GeneroMapper.class);

}