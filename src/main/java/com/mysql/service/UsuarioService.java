package com.mysql.service;

import com.mysql.mapper.UsuarioMapper;
import com.mysql.model.client.Cliente;
import com.mysql.model.usuario.UserListDTO;
import com.mysql.model.usuario.UsuarioDTO;
import com.mysql.model.usuario.UsuarioEntity;
import com.mysql.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private UserRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public String createUser(UsuarioDTO dto) {
        if (usuarioRepository.existsByUsername(dto.email())) {
            throw new RuntimeException("Usuario ja existe");
        }
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setUsername(dto.email());
        usuario.setPassword(passwordEncoder.encode(dto.password()));

        usuarioRepository.save(usuario);
        return "Usuario criado com sucesso";
    }

    public String deleteUser(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return "Usuario deletado com sucesso";
        }
        else return "Usuario não encontrado";
    }

    public List<UserListDTO> listarTodos() {
        List<UsuarioEntity> lista = usuarioRepository.findAll();

        return usuarioMapper.toListDTO(lista);
    }
}
