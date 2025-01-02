package com.mysql.controller;

import com.mysql.model.usuario.*;
import com.mysql.security.TokenService;
import com.mysql.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/logar")
    @Operation(summary = "Usuario Loga no Site")

    public ResponseEntity doLogin(@RequestBody @Valid DataAutentication data) {
        System.out.println(data.password());
        var token = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var authentication = authenticationManager.authenticate(token);

        var tokenJWT = tokenService.buildToken((UsuarioEntity) authentication.getPrincipal());
        return ResponseEntity.ok(tokenJWT);
    }

    @PostMapping("/cadastrar")
    @Operation(summary = "Cadastrar um usuario que loga no site")
    public ResponseEntity<String> createUser(@Valid @RequestBody UsuarioDTO dto) {
        return ResponseEntity.ok(usuarioService.createUser(dto));
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Deletar o Usuario que logam no site pelo ID")
    private ResponseEntity<String> deletarUsuario(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.deleteUser(id));
    }

    @SecurityRequirement(name = "bearer-key")
    @GetMapping
    @Operation(summary = "Listar todos os Usuarios que logam no site")
    private ResponseEntity<List<UserListDTO>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @PutMapping("/atualizar")
    @Operation(summary = "Atualize um Usuario que Autentica Relogue apos o Update")
    public ResponseEntity<LoginUpdateDTO> AtualizarLogin(@RequestBody @Valid DataAutentication data) {
        return ResponseEntity.ok(usuarioService.atualizarLogin(data));
    }


}

