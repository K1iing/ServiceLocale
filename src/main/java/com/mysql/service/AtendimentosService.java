package com.mysql.service;

import com.mysql.exception.ExceptionPersonalizada;
import com.mysql.mapper.AtendimentoMapper;
import com.mysql.model.atendimentos.Atendimentos;
import com.mysql.model.atendimentos.AtendimentosDTO;
import com.mysql.model.client.Cliente;
import com.mysql.model.profissional.Profissional;
import com.mysql.repository.AtendimentosRepository;
import com.mysql.repository.ClienteRepository;
import com.mysql.repository.ProfissionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AtendimentosService {

    @Autowired
    private AtendimentosRepository atendimentosRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Autowired
    private AtendimentoMapper atendimentoMapper;


    public AtendimentosDTO agendamento(AtendimentosDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.idCliente())
                .orElseThrow(() -> new ExceptionPersonalizada("Não foi encontrado o Cliente"));

        Profissional profissional = profissionalRepository.findById(dto.idProfissional())
                .orElseThrow(() -> new ExceptionPersonalizada("Profissional Não encontrado"));

        Atendimentos atendimentos = new Atendimentos();
        atendimentos.setCliente(cliente);
        atendimentos.setProfissional(profissional);
        atendimentos.setDataAtendimento(dto.dataAgendada());

        atendimentos = atendimentosRepository.save(atendimentos);

        return atendimentoMapper.toDTO(atendimentos);

    }

    public List<Atendimentos> listarTodosAtendimentos() {
        return atendimentosRepository.findAll();
    }

    public boolean deletarPeloId(Long id) {
        if (atendimentosRepository.existsById(id)) {

            atendimentosRepository.deleteById(id);

            return true;
        }
        return false;
    }
}
