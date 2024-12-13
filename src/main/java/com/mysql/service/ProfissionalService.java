package com.mysql.service;

import com.mysql.exception.ExceptionPersonalizada;
import com.mysql.mapper.ProfissionalMapper;
import com.mysql.model.atendimentos.Atendimentos;
import com.mysql.model.atendimentos.AtendimentosDTO;
import com.mysql.model.profissional.Profissional;
import com.mysql.model.profissional.ProfissionalDTO;
import com.mysql.repository.ProfissionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfissionalService {

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Autowired
    private ProfissionalMapper profissionalMapper;

    public List<Profissional> mostrarTodos() {

        List<Profissional> atendimentos = profissionalRepository.findAll();

        return atendimentos;
    }

    public ProfissionalDTO cadastrar(ProfissionalDTO dto) {
        Profissional profissional = profissionalMapper.toEntity(dto);

        if (profissionalRepository.existsByNome(dto.nome()) && profissionalRepository.existsByEspecialidade(dto.especialidade())) {
            throw new ExceptionPersonalizada("Usuario ja existe");
        }

        profissionalRepository.save(profissional);

        return profissionalMapper.toDTO(profissional);
    }

    public boolean deletarProfissional(Long id) {
        if (!profissionalRepository.existsById(id)) {
            return false;
        }
        profissionalRepository.deleteById(id);
        return true;
    }
}
