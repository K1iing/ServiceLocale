package com.mysql.controller;

import com.mysql.model.usuario.*;
import com.mysql.security.TokenService;
import com.mysql.service.UsuarioService;
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
    public ResponseEntity doLogin(@RequestBody @Valid DataAutentication data) {
        System.out.println(data.password());
        var token = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var authentication = authenticationManager.authenticate(token);

        var tokenJWT = tokenService.buildToken((UsuarioEntity) authentication.getPrincipal());
        return ResponseEntity.ok(new ApiResponse(true, tokenJWT));
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<String> createUser(@Valid @RequestBody UsuarioDTO dto) {
        return ResponseEntity.ok(usuarioService.createUser(dto));
    }

    @DeleteMapping("{id}")
    private ResponseEntity<String> deletarUsuario(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.deleteUser(id));
    }

    @GetMapping
    private ResponseEntity<List<UserListDTO>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }
}

