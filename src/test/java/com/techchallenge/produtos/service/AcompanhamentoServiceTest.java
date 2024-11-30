package com.techchallenge.produtos.service;

import com.techchallenge.produtos.model.produtos.Acompanhamento;
import com.techchallenge.produtos.repository.AcompanhamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AcompanhamentoServiceTest {

    @Mock
    AcompanhamentoRepository acompanhamentoRepository;

    @InjectMocks
    AcompanhamentoService acompanhamentoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    Acompanhamento acompanhamento = new Acompanhamento("Acompanhamento", "um acompanhamento", 9.90f, true);

    @Test
    void criarAcompanhamento() {
        String nomeBanco = "acompanhamento";
        Acompanhamento acompanhamentoNovo = this.acompanhamento;

        when(acompanhamentoRepository.findByNomeBanco(nomeBanco)).thenReturn(Optional.empty());
        when(acompanhamentoRepository.save(acompanhamentoNovo)).thenReturn(acompanhamentoNovo);

        ResponseEntity<String> response = acompanhamentoService.criarAcompanhamento(acompanhamentoNovo);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(acompanhamentoRepository).save(acompanhamentoNovo);
    }

    @Test
    void buscarAcompanhamento() {
        String nomeBanco = "acompanhamento";
        Acompanhamento acompanhamentoExistente = this.acompanhamento;

        when(acompanhamentoRepository.findByNomeBanco(nomeBanco)).thenReturn(Optional.of(acompanhamentoExistente));

        ResponseEntity<Acompanhamento> response = acompanhamentoService.buscarAcompanhamento(nomeBanco);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(acompanhamento, response.getBody());
    }

    @Test
    void listarAcompanhamentos() {
        List<Acompanhamento> list = new ArrayList<>();
        list.add(acompanhamento);
        when(acompanhamentoRepository.findAll()).thenReturn(list);

        ResponseEntity<List<Acompanhamento>> response = acompanhamentoService.listarAcompanhamentos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void atualizarAcompanhamento() {
        boolean disponibilidadeNovo = false;
        Acompanhamento acompanhamentoModificado = this.acompanhamento;
        acompanhamentoModificado.setDisponivel(disponibilidadeNovo);

        when(acompanhamentoRepository.findByNomeBanco(acompanhamento.getNomeBanco())).thenReturn(Optional.of(acompanhamento));

        ResponseEntity<Acompanhamento> response = acompanhamentoService.atualizarAcompanhamento(acompanhamento.getNomeBanco(), acompanhamentoModificado);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(disponibilidadeNovo, response.getBody().isDisponivel());
    }

    @Test
    void apagarAcompanhamento() {
        Acompanhamento acompanhamentoExistente = this.acompanhamento;

        when(acompanhamentoRepository.save(acompanhamentoExistente)).thenReturn(acompanhamentoExistente);
        when(acompanhamentoRepository.findByNomeBanco(acompanhamento.getNomeBanco())).thenReturn(Optional.of(acompanhamento));

        ResponseEntity<String> response = acompanhamentoService.apagarAcompanhamento(acompanhamento.getNomeBanco());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(acompanhamentoRepository).delete(acompanhamentoExistente);

    }
}