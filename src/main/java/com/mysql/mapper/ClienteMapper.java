package com.mysql.mapper;

import com.mysql.model.client.Cliente;
import com.mysql.model.client.ClienteDTO;
import com.mysql.model.usuario.LoginUpdateDTO;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ClienteMapper {


    Cliente toEntity(ClienteDTO dto);
    ClienteDTO toDTO(Cliente cliente);


}
