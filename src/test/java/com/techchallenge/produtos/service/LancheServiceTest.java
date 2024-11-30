package com.techchallenge.produtos.service;

import com.techchallenge.produtos.model.produtos.Lanche;
import com.techchallenge.produtos.repository.LancheRepository;
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

class LancheServiceTest {

    @Mock
    LancheRepository lancheRepository;

    @InjectMocks
    LancheService lancheService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    Lanche lanche = new Lanche("lanche","um lanche", 30.0f, true);

    @Test
    void criarLanche() {
        String nomeBanco = "lanche";
        Lanche lancheNovo = this.lanche;

        when(lancheRepository.findByNomeBanco(nomeBanco)).thenReturn(Optional.empty());
        when(lancheRepository.save(lancheNovo)).thenReturn(lancheNovo);

        ResponseEntity<String> response = lancheService.criarLanche(lancheNovo);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(lancheRepository).save(lancheNovo);
    }

    @Test
    void buscarLanche() {
        String nomeBanco = "lanche";
        Lanche lancheExistente = this.lanche;

        when(lancheRepository.findByNomeBanco(nomeBanco)).thenReturn(Optional.of(lancheExistente));

        ResponseEntity<Lanche> response = lancheService.buscarLanche(nomeBanco);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(lanche, response.getBody());
    }

    @Test
    void listarLanches() {
        List<Lanche> list = new ArrayList<>();
        list.add(lanche);

        when(lancheRepository.findAll()).thenReturn(list);

        ResponseEntity<List<Lanche>> response = lancheService.listarLanches();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void atualizarLanche() {
        boolean disponibilidadeNovo = false;
        Lanche lancheModificado = this.lanche;
        lancheModificado.setDisponivel(disponibilidadeNovo);

        when(lancheRepository.findByNomeBanco(lanche.getNomeBanco())).thenReturn(Optional.of(lanche));

        ResponseEntity<Lanche> response = lancheService.atualizarLanche(lanche.getNomeBanco(), lancheModificado);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(disponibilidadeNovo, response.getBody().isDisponivel());
    }

    @Test
    void apagarLanche() {
        Lanche lanche = this.lanche;

        when(lancheRepository.save(lanche)).thenReturn(lanche);
        when(lancheRepository.findByNomeBanco(lanche.getNomeBanco())).thenReturn(Optional.of(lanche));

        ResponseEntity<String> response = lancheService.apagarLanche(lanche.getNomeBanco());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(lancheRepository).delete(lanche);
    }
}