package com.mysql.controller;

import com.mysql.mapper.ProfissionalMapper;
import com.mysql.model.atendimentos.Atendimentos;
import com.mysql.model.atendimentos.AtendimentosDTO;
import com.mysql.model.atendimentos.AtendimentosListagemDTO;
import com.mysql.model.profissional.Profissional;
import com.mysql.model.profissional.ProfissionalDTO;
import com.mysql.service.ProfissionalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profissional")
@SecurityRequirement(name = "bearer-key")
public class ProfissionalController {

    @Autowired
    private ProfissionalService profissionalService;

    @GetMapping
    @Operation(summary = "Lista todos os Profissionais")
    public ResponseEntity<List<Profissional>> listarTodos() {
        return ResponseEntity.ok(profissionalService.mostrarTodos());
    }

    @PostMapping
    @Operation(summary = "Cadastra um Profissional")
    public ResponseEntity<ProfissionalDTO> cadastrar(@RequestBody ProfissionalDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(profissionalService.cadastrar(dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um profissional pelo ID")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        if (profissionalService.deletarProfissional(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Excluido com Sucesso!");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ja foi excluido ou não existe");
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lista o Historico de Atendimentos daquele Profissional passando o ID")
    public ResponseEntity<List<AtendimentosListagemDTO>> listarAtendimentoPorProfissional(@PathVariable Long id) {
        return ResponseEntity.ok(profissionalService.buscarPorProfissional(id));
    }



}
