package com.mysql.service;

import com.mysql.exception.ExceptionPersonalizada;
import com.mysql.mapper.ClienteMapper;
import com.mysql.mapper.GeneroMapper;
import com.mysql.model.client.Cliente;
import com.mysql.model.client.ClienteDTO;
import com.mysql.model.client.NomeDTO;
import com.mysql.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteMapper clienteMapper;

    @Autowired
    private GeneroMapper generoMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    //Cadastra o Usuario e verifica se ele já existe pelo Email
    public ClienteDTO cadastrar(ClienteDTO dto) {
        Cliente cliente = clienteMapper.toEntity(dto);

        if(clienteRepository.existsByEmail(dto.email())) {
            throw new ExceptionPersonalizada("Cliente ja existente");
        }
        cliente.setSenha(passwordEncoder.encode(dto.senha()));
        clienteRepository.save(cliente);

        return clienteMapper.toDTO(cliente);
    }

    //Lista todos os Clientes
    public List<ClienteDTO> listarTodos() {

        List<Cliente> clientes = clienteRepository.findAll();

        return generoMapper.toDTOList(clientes);

    }

    public boolean excluirUsuario(Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return true;
        }
        return false;

    }

    public String receberNome(String email) {

        Optional<Cliente> clienteOptional = clienteRepository.findByEmail(email);

        Cliente cliente = clienteOptional.get();

        return cliente.getNome();
    }

}
