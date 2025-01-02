package com.mysql.mapper;

import com.mysql.model.usuario.LoginUpdateDTO;
import com.mysql.model.usuario.UserListDTO;
import com.mysql.model.usuario.UsuarioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(target = "id", expression = "java(entity.getId())")
    @Mapping(target = "email", source = "username")
    @Mapping(target = "password", source = "password")
    UserListDTO toDTO(UsuarioEntity entity);

    List<UserListDTO> toListDTO(List<UsuarioEntity> entities);

    @Mapping(target = "email", source = "username")
    @Mapping(target = "senha", source = "password")
    LoginUpdateDTO toDtoUpdate(UsuarioEntity user);
}
