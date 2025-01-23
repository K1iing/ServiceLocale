package com.mysql.service;

import com.mysql.exception.ExceptionPersonalizada;
import com.mysql.mapper.AtendimentoMapper;
import com.mysql.model.atendimentos.*;
import com.mysql.model.client.Cliente;
import com.mysql.model.profissional.Profissional;
import com.mysql.repository.AtendimentosRepository;
import com.mysql.repository.ClienteRepository;
import com.mysql.repository.ProfissionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<ListagemMeusAtendimentos> listarTodosPeloEmail(String email) {
        return atendimentoMapper.toListAtendimentosDTO(atendimentosRepository.findByClienteEmail(email));

    }


    public boolean deletarPeloId(Long id) {
        if (atendimentosRepository.existsById(id)) {

            atendimentosRepository.deleteById(id);

            return true;
        }
        return false;

    }

    public List<AtendimentosListagemDTO> listarHistorico(Long id) {

        List<Atendimentos> atendimentos = atendimentosRepository.findByClienteId(id);

        if (atendimentos.isEmpty()) {
            throw new ExceptionPersonalizada("atendimento esta vazio ou não existe");
        }

        return atendimentoMapper.toListDTO(atendimentos);
    }

    public AtendimentosListagemDTO listarPeloId(Long id) {

        Atendimentos atendimentos = atendimentosRepository.findById(id).orElseThrow(() -> new ExceptionPersonalizada("Atendimento não Encontrado"));

        return atendimentoMapper.toListagemDTO(atendimentos);
    }

    public AtendimentosListagemDTO mudarStatus(Long id, StatusDTO status) {
        Atendimentos atendimento = atendimentosRepository.findById(id).orElseThrow(() -> new ExceptionPersonalizada("Atendimento não encontrado"));
        atendimento.setStatus_atendimentos(status.status());

        atendimento.setStatus_atendimentos(status.status());

        atendimento = atendimentosRepository.save(atendimento);

        System.out.println(atendimento);

        return atendimentoMapper.toListagemDTO(atendimento);
    }

    public AtendimentosDTO atualizarAtendimentos(Long id, AtendimentosDTO dto) {

        Optional<Atendimentos> optionalAtendimentos = atendimentosRepository.findById(id);

        if (optionalAtendimentos.isEmpty()) {
            throw new ExceptionPersonalizada("Atendimentos nao encontrado");
        }

        Optional<Profissional> profissionalOptional = profissionalRepository.findById(dto.idProfissional());

        if (profissionalOptional.isEmpty()) {
            throw new ExceptionPersonalizada("Profissional Não Encontrado");
        }

        Profissional profissional = profissionalOptional.get();

        Atendimentos atendimentos = optionalAtendimentos.get();


        atendimentos.setDataAtendimento(dto.dataAgendada());
        atendimentos.setProfissional(profissional);
        atendimentos.setDescricao(dto.descricao());

        atendimentos = atendimentosRepository.save(atendimentos);

        return atendimentoMapper.toDTO(atendimentos);


    }
}
