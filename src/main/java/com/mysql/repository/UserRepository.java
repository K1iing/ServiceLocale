package com.mysql.repository;

import com.mysql.model.usuario.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<UsuarioEntity, Long> {

    UsuarioEntity findByUsername(String username);

    boolean existsByUsername(String username);
}
