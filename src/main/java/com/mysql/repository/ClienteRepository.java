package com.mysql.repository;

import com.mysql.model.client.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    boolean existsByEmail(String email);

    Optional<Cliente>findByEmail(String username);

}
