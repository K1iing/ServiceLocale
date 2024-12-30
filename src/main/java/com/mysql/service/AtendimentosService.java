package com.mysql.service;

import com.mysql.exception.ExceptionPersonalizada;
import com.mysql.mapper.AtendimentoMapper;
import com.mysql.model.atendimentos.Atendimentos;
import com.mysql.model.atendimentos.AtendimentosDTO;
import com.mysql.model.atendimentos.AtendimentosListagemDTO;
import com.mysql.model.client.Cliente;
import com.mysql.model.profissional.Profissional;
import com.mysql.repository.AtendimentosRepository;
import com.mysql.repository.ClienteRepository;
import com.mysql.repository.ProfissionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Cliente cliente = clienteRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Profissional profissional = profissionalRepository.findById(dto.idProfissional())
                .orElseThrow(() -> new ExceptionPersonalizada("Profissional Não encontrado"));
        
        Atendimentos atendimentos = new Atendimentos(cliente, profissional, dto.dataAgendada(), dto.descricao());

        atendimentos = atendimentosRepository.save(atendimentos);

        return atendimentoMapper.toDTO(atendimentos);

    }

    public List<AtendimentosListagemDTO> listarTodosAtendimentos() {
        return atendimentoMapper.toListDTO(atendimentosRepository.findAll());
    }


    public boolean deletarPeloId(Long id) {
        if (atendimentosRepository.existsById(id)) {

            atendimentosRepository.deleteById(id);

            return true;
        }
        return false;

    }
    public List<AtendimentosListagemDTO> listarHistorico(Long id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        LocalDateTime horasagora = LocalDate.now().atStartOfDay();

        return atendimentoMapper.toListDTO(atendimentosRepository.findByClienteAndDataAtendimentoBefore(cliente, horasagora));
    }

    public AtendimentosListagemDTO listarPeloId(Long id) {

        Atendimentos atendimentos = atendimentosRepository.findById(id).orElseThrow(() -> new ExceptionPersonalizada("Atendimento não Encontrado"));

        return atendimentoMapper.toListagemDTO(atendimentos);
    }
}
