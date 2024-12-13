package com.mysql.controller;

import com.mysql.mapper.ProfissionalMapper;
import com.mysql.model.atendimentos.Atendimentos;
import com.mysql.model.atendimentos.AtendimentosDTO;
import com.mysql.model.profissional.Profissional;
import com.mysql.model.profissional.ProfissionalDTO;
import com.mysql.service.ProfissionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profissional")
public class ProfissionalController {

    @Autowired
    private ProfissionalService profissionalService;

    @GetMapping
    public ResponseEntity<List<Profissional>> listarTodos() {
        return ResponseEntity.ok(profissionalService.mostrarTodos());
    }

    @PostMapping
    public ResponseEntity<ProfissionalDTO> cadastrar(@RequestBody ProfissionalDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(profissionalService.cadastrar(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        if (profissionalService.deletarProfissional(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Excluido com Sucesso!");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ja foi excluido ou não existe");
    }
}
