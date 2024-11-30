package com.techchallenge.produtos.controller;

import com.techchallenge.produtos.model.produtos.Lanche;
import com.techchallenge.produtos.repository.LancheRepository;
import com.techchallenge.produtos.usecase.LancheUseCase;
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

class LancheControllerTest {

    @Mock
    LancheRepository lancheRepository;

    @Mock
    LancheUseCase lancheUseCase;

    @InjectMocks
    LancheController lancheController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    Lanche lanche = new Lanche("Lanche", "um lanche", 9.90f, true);

    @Test
    void criarLanche() {
        Lanche lancheNovo = this.lanche;

        when(lancheUseCase.criarLanche(lancheNovo)).thenReturn(new ResponseEntity<String>(lanche.getNome() + " salvo no banco de dados", HttpStatus.CREATED));

        ResponseEntity<String> response = lancheController.criarLanche(lancheNovo);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    }

    @Test
    void listarLanches() {
        List<Lanche> list = new ArrayList<>();
        list.add(lanche);

        when(lancheUseCase.listarLanches()).thenReturn(new ResponseEntity<>(list, HttpStatus.OK));

        ResponseEntity<List<Lanche>> response = lancheController.listarLanches();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(list, response.getBody());

    }

    @Test
    void buscarLanche() {
        String nomeBanco = "lanche";
        Lanche lancheExistente = this.lanche;

        when(lancheUseCase.buscarLanche(nomeBanco)).thenReturn(new ResponseEntity<>(lancheExistente, HttpStatus.OK));

        ResponseEntity<Lanche> response = lancheController.buscarLanche(nomeBanco);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(lanche, response.getBody());
    }

    @Test
    void atualizarLanche() {
        boolean disponibilidadeNovo = false;
        Lanche lancheModificado = this.lanche;
        lancheModificado.setDisponivel(disponibilidadeNovo);

        when(lancheUseCase.buscarLanche(lancheModificado.getNomeBanco())).thenReturn(new ResponseEntity<>(lanche, HttpStatus.OK));
        when(lancheUseCase.atualizarLanche(lanche.getNomeBanco(), lancheModificado)).thenReturn(new ResponseEntity<>(lancheModificado, HttpStatus.OK));

        ResponseEntity response = lancheController.atualizarLanche(lanche.getNomeBanco(), lancheModificado);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());

    }

    @Test
    void apagarLanche() {
        Lanche lancheExistente = this.lanche;

        when(lancheUseCase.buscarLanche(lancheExistente.getNomeBanco())).thenReturn(new ResponseEntity<>(lancheExistente, HttpStatus.OK));
        when(lancheUseCase.apagarLanche(lancheExistente.getNomeBanco())).thenReturn(new ResponseEntity<>(null, HttpStatus.OK));

        ResponseEntity<String> response = lancheController.apagarLanche(lancheExistente.getNomeBanco());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());

    }

}