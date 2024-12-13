package com.mysql.controller;


import com.mysql.model.client.Cliente;
import com.mysql.model.client.ClienteDTO;
import com.mysql.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping()
    public ResponseEntity<ClienteDTO> cadastrar(@RequestBody ClienteDTO dto) {
        ClienteDTO clienteDTO = clienteService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteDTO);
    }

    @GetMapping()
    public ResponseEntity<List<Cliente>> listarTodos() {
        return ResponseEntity.ok(clienteService.listarTodos());
    }

    @GetMapping("hello")
    public String olaMundo() {
        return "Ola, Mundo";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> excluirCliente(@PathVariable Long id) {
        if (clienteService.excluirUsuario(id)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
