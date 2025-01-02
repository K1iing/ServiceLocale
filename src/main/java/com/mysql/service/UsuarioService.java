package com.mysql.service;

import com.mysql.exception.ExceptionPersonalizada;
import com.mysql.mapper.UsuarioMapper;
import com.mysql.model.client.Cliente;
import com.mysql.model.usuario.*;
import com.mysql.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

        public LoginUpdateDTO atualizarLogin(DataAutentication data) {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();

            Optional<UsuarioEntity> usuario = usuarioRepository.searchByUsername(email);

            if (usuario.isEmpty()) {
                throw new ExceptionPersonalizada("Usuario não encontrado não pode ser alterado");
            }

            UsuarioEntity usuario1 = usuario.get();

            if (!usuario1.getUsername().equals(data.email()) && usuarioRepository.existsByUsername(data.email())) {
                throw new ExceptionPersonalizada("O email ja foi fornecido e esta em uso por outro usuario");
            }
            usuario1.setUsername(data.email());
            usuario1.setPassword(passwordEncoder.encode(data.password()));

            UsuarioEntity dataUsuario = usuarioRepository.save(usuario1);

            return usuarioMapper.toDtoUpdate(dataUsuario);

        }
}
