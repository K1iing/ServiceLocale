package com.mysql.controller;


import com.mysql.model.client.ClienteDTO;
import com.mysql.model.client.NomeDTO;
import com.mysql.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
@SecurityRequirement(name = "bearer-key")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/cadastrar")
    @Operation(summary = "Cadastra um Cliente no Sistema")
    public ResponseEntity<ClienteDTO> cadastrar(@RequestBody @Valid ClienteDTO dto) {
        ClienteDTO clienteDTO = clienteService.cadastrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteDTO);
    }

    @GetMapping()
    @Operation(summary = "Lista todos os Clientes do sistema")
    public ResponseEntity<List<ClienteDTO>> listarTodos() {
        return ResponseEntity.ok(clienteService.listarTodos());
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui Cliente passando o ID")
    public ResponseEntity<HttpStatus> excluirCliente(@PathVariable Long id) {
        if (clienteService.excluirUsuario(id)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/{email}")
    @Operation(summary = "Busca nome do usuario pelo email")
    public ResponseEntity<String> buscarPeloEmail(@PathVariable String email) {
        return ResponseEntity.ok(clienteService.receberNome(email));
    }
}
