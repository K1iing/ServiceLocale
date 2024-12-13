package com.mysql.service;

import com.mysql.exception.ExceptionPersonalizada;
import com.mysql.mapper.ClienteMapper;
import com.mysql.model.client.Cliente;
import com.mysql.model.client.ClienteDTO;
import com.mysql.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteMapper clienteMapper;

    //Cadastra o Usuario e verifica se ele já existe pelo Email
    public ClienteDTO cadastrar(ClienteDTO dto) {
        Cliente cliente = clienteMapper.toEntity(dto);

        if(clienteRepository.existsByEmail(dto.email())) {
            throw new ExceptionPersonalizada("Cliente ja existente");
        }

        clienteRepository.save(cliente);

        return clienteMapper.toDTO(cliente);
    }

    //Lista todos os Clientes
    public List<Cliente> listarTodos() {

        List<Cliente> clientes = clienteRepository.findAll();
        return clientes;
    }

    public boolean excluirUsuario(Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return true;
        }
        return false;


    }
}
