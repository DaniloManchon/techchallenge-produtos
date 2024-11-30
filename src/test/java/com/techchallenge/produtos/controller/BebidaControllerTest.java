package com.techchallenge.produtos.controller;

import com.techchallenge.produtos.model.produtos.Bebida;
import com.techchallenge.produtos.repository.BebidaRepository;
import com.techchallenge.produtos.usecase.BebidaUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class BebidaControllerTest {

    @Mock
    BebidaRepository bebidaRepository;

    @Mock
    BebidaUseCase bebidaUseCase;

    @InjectMocks
    BebidaController bebidaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    Bebida bebida = new Bebida("Bebida", "um bebida", 9.90f, true, "2l");

    @Test
    void criarBebida() {
        Bebida bebidaNovo = this.bebida;

        when(bebidaUseCase.criarBebida(bebidaNovo)).thenReturn(new ResponseEntity<String>(bebida.getNome() + " salvo no banco de dados", HttpStatus.CREATED));

        ResponseEntity<String> response = bebidaController.criarBebida(bebidaNovo);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    }

    @Test
    void listarBebidas() {
        List<Bebida> list = new ArrayList<>();
        list.add(bebida);

        when(bebidaUseCase.listarBebidas()).thenReturn(new ResponseEntity<>(list, HttpStatus.OK));

        ResponseEntity<List<Bebida>> response = bebidaController.listarBebidas();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(list, response.getBody());

    }

    @Test
    void buscarBebida() {
        String nomeBanco = "bebida";
        Bebida bebidaExistente = this.bebida;

        when(bebidaUseCase.buscarBebida(nomeBanco)).thenReturn(new ResponseEntity<>(bebidaExistente, HttpStatus.OK));

        ResponseEntity<Bebida> response = bebidaController.buscarBebida(nomeBanco);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(bebida, response.getBody());
    }

    @Test
    void atualizarBebida() {
        boolean disponibilidadeNovo = false;
        Bebida bebidaModificado = this.bebida;
        bebidaModificado.setDisponivel(disponibilidadeNovo);

        when(bebidaUseCase.buscarBebida(bebidaModificado.getNomeBanco())).thenReturn(new ResponseEntity<>(bebida, HttpStatus.OK));
        when(bebidaUseCase.atualizarBebida(bebida.getNomeBanco(), bebidaModificado)).thenReturn(new ResponseEntity<>(bebidaModificado, HttpStatus.OK));

        ResponseEntity response = bebidaController.atualizarBebida(bebida.getNomeBanco(), bebidaModificado);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());

    }

    @Test
    void apagarBebida() {
        Bebida bebidaExistente = this.bebida;

        when(bebidaUseCase.buscarBebida(bebidaExistente.getNomeBanco())).thenReturn(new ResponseEntity<>(bebidaExistente, HttpStatus.OK));
        when(bebidaUseCase.apagarBebida(bebidaExistente.getNomeBanco())).thenReturn(new ResponseEntity<>(null, HttpStatus.OK));

        ResponseEntity<String> response = bebidaController.apagarBebida(bebidaExistente.getNomeBanco());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());

    }

}