package com.mysql.controller;

import com.mysql.model.atendimentos.AtendimentosDTO;
import com.mysql.model.atendimentos.AtendimentosListagemDTO;
import com.mysql.service.AtendimentosService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Usuario cadastra um Atendimento")
    public ResponseEntity<AtendimentosDTO> cadastrarAtendimento(@RequestBody @Valid AtendimentosDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(atendimentosService.agendamento(dto));
    }

    @GetMapping
    @Operation(summary = "Lista todos os Atendimentos")
    public ResponseEntity<List<AtendimentosListagemDTO>> listarTodos() {
        return ResponseEntity.ok(atendimentosService.listarTodosAtendimentos());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um atendimento pelo ID")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        if (atendimentosService.deletarPeloId(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Excluido com sucesso!");
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lista o historico de um atendimento pelo ID")
    public ResponseEntity<List<AtendimentosListagemDTO>> listarHistorico(@PathVariable Long id) {
        return ResponseEntity.ok(atendimentosService.listarHistorico(id));
    }

    @GetMapping("/listar/{id}")
    @Operation(summary = "Listar o atendimento pelo ID")
    public ResponseEntity<AtendimentosListagemDTO> listarPeloId(@PathVariable Long id) {
        return ResponseEntity.ok(atendimentosService.listarPeloId(id));
    }




}
