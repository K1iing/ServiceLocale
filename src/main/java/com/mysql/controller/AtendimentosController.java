package com.mysql.controller;

import com.mysql.model.atendimentos.AtendimentosDTO;
import com.mysql.model.atendimentos.AtendimentosListagemDTO;
import com.mysql.service.AtendimentosService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/atendimentos")
@SecurityRequirement(name = "bearer-key")
public class AtendimentosController {

    @Autowired
    private AtendimentosService atendimentosService;

    @PostMapping
    public ResponseEntity<AtendimentosDTO> cadastrarAtendimento(@RequestBody @Valid AtendimentosDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(atendimentosService.agendamento(dto));
    }

    @GetMapping
    public ResponseEntity<List<AtendimentosListagemDTO>> listarTodos() {
        return ResponseEntity.ok(atendimentosService.listarTodosAtendimentos());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        if (atendimentosService.deletarPeloId(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Excluido com sucesso!");
        }
        return ResponseEntity.badRequest().build();
    }

}
