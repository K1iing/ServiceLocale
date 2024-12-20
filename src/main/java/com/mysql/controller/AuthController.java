package com.mysql.controller;

import com.mysql.model.usuario.ApiResponse;
import com.mysql.model.usuario.DataAutentication;
import com.mysql.model.usuario.UsuarioDTO;
import com.mysql.model.usuario.UsuarioEntity;
import com.mysql.security.TokenService;
import com.mysql.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        var token = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var authentication = authenticationManager.authenticate(token);

        var tokenJWT = tokenService.buildToken((UsuarioEntity) authentication.getPrincipal());
        return ResponseEntity.ok(new ApiResponse(true, tokenJWT));
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<String> createUser(@Valid @RequestBody UsuarioDTO dto) {
        return ResponseEntity.ok(usuarioService.createUser(dto));
    }

}
