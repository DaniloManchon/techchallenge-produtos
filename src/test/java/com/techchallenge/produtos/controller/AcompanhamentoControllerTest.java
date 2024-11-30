package com.techchallenge.produtos.controller;

import com.techchallenge.produtos.model.produtos.Acompanhamento;
import com.techchallenge.produtos.repository.AcompanhamentoRepository;
import com.techchallenge.produtos.service.AcompanhamentoService;
import com.techchallenge.produtos.usecase.AcompanhamentoUseCase;
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
import static org.mockito.Mockito.when;

class AcompanhamentoControllerTest {

    @Mock
    AcompanhamentoRepository acompanhamentoRepository;

    @Mock
    AcompanhamentoUseCase acompanhamentoUseCase;

    @InjectMocks
    AcompanhamentoController acompanhamentoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    Acompanhamento acompanhamento = new Acompanhamento("Acompanhamento", "um acompanhamento", 9.90f, true);

    @Test
    void criarAcompanhamento() {
        Acompanhamento acompanhamentoNovo = this.acompanhamento;

        when(acompanhamentoUseCase.criarAcompanhamento(acompanhamentoNovo)).thenReturn(new ResponseEntity<String>(acompanhamento.getNome() + " salvo no banco de dados", HttpStatus.CREATED));

        ResponseEntity<String> response = acompanhamentoController.criarAcompanhamento(acompanhamentoNovo);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    }

    @Test
    void listarAcompanhamentos() {
        List<Acompanhamento> list = new ArrayList<>();
        list.add(acompanhamento);

        when(acompanhamentoUseCase.listarAcompanhamentos()).thenReturn(new ResponseEntity<>(list, HttpStatus.OK));

        ResponseEntity<List<Acompanhamento>> response = acompanhamentoController.listarAcompanhamentos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(list, response.getBody());

    }

    @Test
    void buscarAcompanhamento() {
        String nomeBanco = "acompanhamento";
        Acompanhamento acompanhamentoExistente = this.acompanhamento;

        when(acompanhamentoUseCase.buscarAcompanhamento(nomeBanco)).thenReturn(new ResponseEntity<>(acompanhamentoExistente, HttpStatus.OK));

        ResponseEntity<Acompanhamento> response = acompanhamentoController.buscarAcompanhamento(nomeBanco);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(acompanhamento, response.getBody());
    }

    @Test
    void atualizarAcompanhamento() {
        boolean disponibilidadeNovo = false;
        Acompanhamento acompanhamentoModificado = this.acompanhamento;
        acompanhamentoModificado.setDisponivel(disponibilidadeNovo);

        when(acompanhamentoUseCase.buscarAcompanhamento(acompanhamentoModificado.getNomeBanco())).thenReturn(new ResponseEntity<>(acompanhamento, HttpStatus.OK));
        when(acompanhamentoUseCase.atualizarAcompanhamento(acompanhamento.getNomeBanco(), acompanhamentoModificado)).thenReturn(new ResponseEntity<>(acompanhamentoModificado, HttpStatus.OK));

        ResponseEntity response = acompanhamentoController.atualizarAcompanhamento(acompanhamento.getNomeBanco(), acompanhamentoModificado);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());

    }

    @Test
    void apagarAcompanhamento() {
        Acompanhamento acompanhamentoExistente = this.acompanhamento;

        when(acompanhamentoUseCase.buscarAcompanhamento(acompanhamentoExistente.getNomeBanco())).thenReturn(new ResponseEntity<>(acompanhamentoExistente, HttpStatus.OK));
        when(acompanhamentoUseCase.apagarAcompanhamento(acompanhamentoExistente.getNomeBanco())).thenReturn(new ResponseEntity<>(null, HttpStatus.OK));

        ResponseEntity<String> response = acompanhamentoController.apagarAcompanhamento(acompanhamentoExistente.getNomeBanco());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());

    }
}