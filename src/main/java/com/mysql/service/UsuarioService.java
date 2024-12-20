package com.mysql.service;

import com.mysql.model.usuario.UsuarioDTO;
import com.mysql.model.usuario.UsuarioEntity;
import com.mysql.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UserRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public String createUser(UsuarioDTO dto) {
        if (usuarioRepository.existsByUsername(dto.username())) {
            throw new RuntimeException("Usuario ja existe");
        }
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setUsername(dto.username());
        usuario.setPassword(passwordEncoder.encode(dto.password()));

        usuarioRepository.save(usuario);
        return "Usuario criado com sucesso";
    }
}
