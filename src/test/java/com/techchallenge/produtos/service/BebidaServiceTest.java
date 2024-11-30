package com.techchallenge.produtos.service;

import com.techchallenge.produtos.model.produtos.Bebida;
import com.techchallenge.produtos.repository.BebidaRepository;
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

class BebidaServiceTest {

    @Mock
    BebidaRepository bebidaRepository;

    @InjectMocks
    BebidaService bebidaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    Bebida bebida = new Bebida("bebida", "uma bebida", 10.20f, true, "350ml");

    @Test
    void criarBebida() {
        String nomeBanco = "bebida";
        Bebida bebidaNovo = this.bebida;

        when(bebidaRepository.findByNomeBanco(nomeBanco)).thenReturn(Optional.empty());
        when(bebidaRepository.save(bebidaNovo)).thenReturn(bebidaNovo);

        ResponseEntity<String> response = bebidaService.criarBebida(bebidaNovo);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(bebidaRepository).save(bebidaNovo);
    }

    @Test
    void buscarBebida() {
        String nomeBanco = "bebida";
        Bebida bebidaExistente = this.bebida;

        when(bebidaRepository.findByNomeBanco(nomeBanco)).thenReturn(Optional.of(bebidaExistente));

        ResponseEntity<Bebida> response = bebidaService.buscarBebida(nomeBanco);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(bebida, response.getBody());
    }

    @Test
    void listarBebidas() {
        List<Bebida> list = new ArrayList<>();
        list.add(bebida);

        when(bebidaRepository.findAll()).thenReturn(list);

        ResponseEntity<List<Bebida>> response = bebidaService.listarBebidas();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void atualizarBebida() {
        boolean disponibilidadeNovo = false;
        Bebida bebidaModificado = this.bebida;
        bebidaModificado.setDisponivel(disponibilidadeNovo);

        when(bebidaRepository.findByNomeBanco(bebida.getNomeBanco())).thenReturn(Optional.of(bebida));

        ResponseEntity<Bebida> response = bebidaService.atualizarBebida(bebida.getNomeBanco(), bebidaModificado);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(disponibilidadeNovo, response.getBody().isDisponivel());
    }

    @Test
    void apagarBebida() {
        Bebida bebida = this.bebida;

        when(bebidaRepository.save(bebida)).thenReturn(bebida);
        when(bebidaRepository.findByNomeBanco(bebida.getNomeBanco())).thenReturn(Optional.of(bebida));

        ResponseEntity<String> response = bebidaService.apagarBebida(bebida.getNomeBanco());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(bebidaRepository).delete(bebida);
    }
}