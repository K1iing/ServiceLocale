package com.mysql.service;

import com.mysql.exception.ExceptionPersonalizada;
import com.mysql.mapper.AtendimentoMapper;
import com.mysql.model.atendimentos.Atendimentos;
import com.mysql.model.atendimentos.AtendimentosListagemDTO;
import com.mysql.model.atendimentos.ClienteListagemDTO;
import com.mysql.model.atendimentos.ProfissionalListagemDTO;
import com.mysql.model.client.Cliente;
import com.mysql.model.profissional.Especialidade;
import com.mysql.repository.AtendimentosRepository;
import com.mysql.repository.ClienteRepository;
import com.mysql.repository.ProfissionalRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AtendimentosServiceTest {

    @InjectMocks
    private AtendimentosService atendimentosService;

    @Mock
    private AtendimentosRepository atendimentosRepository;

    @Mock
    private AtendimentoMapper atendimentoMapper;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ProfissionalRepository profissionalRepository;

    @Test
    public void deveRetornarUmaListaDeAtendimentos() {
        Atendimentos atendimento1 = new Atendimentos();
        Atendimentos atendimento2 = new Atendimentos();
        List<Atendimentos> atendimentosMock = Arrays.asList(atendimento1, atendimento2);

        ClienteListagemDTO cliente = new ClienteListagemDTO(1L, "Cliente A", "thduasdhua@gmail.com", "1111111111");
        ProfissionalListagemDTO profissional = new ProfissionalListagemDTO(1L, "Profissional A", Especialidade.MEDICO);

        // Criando objetos DTO
        AtendimentosListagemDTO dto1 = new AtendimentosListagemDTO(
                1L,
                cliente,
                profissional,
                LocalDateTime.of(2024, 12, 27, 10, 0, 0),
                "Descrição Atendimento 1"
        );

        AtendimentosListagemDTO dto2 = new AtendimentosListagemDTO(
                2L,
                cliente,
                profissional,
                LocalDateTime.of(2024, 12, 27, 11, 0, 0),
                "Descrição Atendimento 2"
        );

        List<AtendimentosListagemDTO> dtoMock = Arrays.asList(dto1, dto2);

        // Configurar comportamento dos mocks
        when(atendimentosRepository.findAll()).thenReturn(atendimentosMock);
        when(atendimentoMapper.toListDTO(atendimentosMock)).thenReturn(dtoMock);

        // Chamar o método
        List<AtendimentosListagemDTO> resultado = atendimentosService.listarTodosAtendimentos();

        // Verificar interações e resultado
        verify(atendimentosRepository, times(1)).findAll();
        verify(atendimentoMapper, times(1)).toListDTO(atendimentosMock);

        assertEquals(2, resultado.size());
        assertEquals(dto1, resultado.get(0));
        assertEquals(dto2, resultado.get(1));
    }

    @Test
    public void TestarDeleteById() {

        Atendimentos atendimentos01 = new Atendimentos();
        atendimentos01.setId(1L);

        when(atendimentosRepository.existsById(1L)).thenReturn(true);

        boolean resultado = atendimentosService.deletarPeloId(1L);

        verify(atendimentosRepository).deleteById(1L);

        assertTrue(resultado);
    }

    @Test
    public void TestarBuscaPorHistorico() {
        // Configurando o cliente mockado
        Cliente clienteMock = new Cliente(1L, "Cliente Teste", "cliente@teste.com", "111111", "123123");

        // Configurando o DTO do cliente
        ClienteListagemDTO clienteListagemDTO = new ClienteListagemDTO(1L, "Cliente Teste", "cliente@teste.com", "111111");

        // Configurando o DTO do profissional
        ProfissionalListagemDTO profissionalListagemDTO = new ProfissionalListagemDTO(1L, "Profissional Teste", Especialidade.MEDICO);

        // Criando o atendimento mockado
        AtendimentosListagemDTO atendimentoDTO = new AtendimentosListagemDTO(
                1L,
                clienteListagemDTO,
                profissionalListagemDTO,
                LocalDateTime.of(2025, 12, 1, 1, 10, 10),
                "Consulta de rotina"
        );

        // Criando uma lista de atendimentos simulada
        Atendimentos atendimentoMock = new Atendimentos();
        List<Atendimentos> atendimentosMockList = Arrays.asList(atendimentoMock);

        // Configurando o comportamento dos mocks
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteMock));
        when(atendimentosRepository.findByClienteAndDataAtendimentoBefore(eq(clienteMock), any(LocalDateTime.class)))
                .thenReturn(atendimentosMockList);
        when(atendimentoMapper.toListDTO(atendimentosMockList)).thenReturn(Arrays.asList(atendimentoDTO));

        // Chamando o método a ser testado
        List<AtendimentosListagemDTO> historico = atendimentosService.listarHistorico(1L);

        // Verificando o resultado
        assertNotNull(historico);
        assertEquals(1, historico.size());
        assertEquals("Cliente Teste", historico.get(0).cliente().nome());
        assertEquals("Profissional Teste", historico.get(0).profissional().nome());
        assertEquals("Consulta de rotina", historico.get(0).descricao());
        assertEquals(LocalDateTime.of(2025, 12, 1, 1, 10, 10), historico.get(0).dataAtendimento());

        // Verificando se os métodos dos mocks foram chamados corretamente
        verify(clienteRepository, times(1)).findById(1L);
        verify(atendimentosRepository, times(1)).findByClienteAndDataAtendimentoBefore(eq(clienteMock), any(LocalDateTime.class));
        verify(atendimentoMapper, times(1)).toListDTO(atendimentosMockList);
    }

    @Test
    public void atendimentoUsuarioPeloId() {
        ClienteListagemDTO clienteMock = new ClienteListagemDTO(
                1L, "AA", "hduasuha@gmail.com", "11111111"
        );
        ProfissionalListagemDTO profissionalListagem = new ProfissionalListagemDTO(
                1L, "BB", Especialidade.NUTRICIONISTA
        );
        AtendimentosListagemDTO atendimentos = new AtendimentosListagemDTO(
                1L,
                clienteMock,
                profissionalListagem,
                LocalDateTime.of(2025, 12, 1, 1, 10, 10),
                "AAA"
        );

        Atendimentos atendimentosMock = new Atendimentos();
        atendimentosMock.setId(1L);

        when(atendimentosRepository.findById(1L)).thenReturn(Optional.of(atendimentosMock));
        when(atendimentoMapper.toListagemDTO(atendimentosMock)).thenReturn(atendimentos);

        AtendimentosListagemDTO resultado = atendimentosService.listarPeloId(1L);

        assertEquals("AA", resultado.cliente().nome());
        assertEquals("BB", resultado.profissional().nome());
        assertEquals("AAA", resultado.descricao());

        verify(atendimentosRepository, times(1)).findById(1L);
        verify(atendimentoMapper, times(1)).toListagemDTO(atendimentosMock);
    }

    @Test
    public void DeveLancarExcecaoQuandoNaoEncontrado() {
        Long id = 2L;

        when(atendimentosRepository.findById(id)).thenReturn(Optional.empty());

        ExceptionPersonalizada exception = assertThrows(ExceptionPersonalizada.class, () -> atendimentosService.listarPeloId(id));

        assertEquals("Atendimento não Encontrado", exception.getMessage());

        verify(atendimentosRepository, times(1)).findById(id);

        verify(atendimentoMapper, never()).toListagemDTO(any());
    }
}