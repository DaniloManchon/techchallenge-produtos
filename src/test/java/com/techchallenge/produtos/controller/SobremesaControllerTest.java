package com.techchallenge.produtos.controller;

import com.techchallenge.produtos.model.produtos.Sobremesa;
import com.techchallenge.produtos.repository.SobremesaRepository;
import com.techchallenge.produtos.usecase.SobremesaUseCase;
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

class SobremesaControllerTest {

    @Mock
    SobremesaRepository sobremesaRepository;

    @Mock
    SobremesaUseCase sobremesaUseCase;

    @InjectMocks
    SobremesaController sobremesaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    Sobremesa sobremesa = new Sobremesa("Sobremesa", "uma sobremesa", 9.90f, true);

    @Test
    void criarSobremesa() {
        Sobremesa sobremesaNovo = this.sobremesa;

        when(sobremesaUseCase.criarSobremesa(sobremesaNovo)).thenReturn(new ResponseEntity<String>(sobremesa.getNome() + " salvo no banco de dados", HttpStatus.CREATED));

        ResponseEntity<String> response = sobremesaController.criarSobremesa(sobremesaNovo);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    }

    @Test
    void listarSobremesas() {
        List<Sobremesa> list = new ArrayList<>();
        list.add(sobremesa);

        when(sobremesaUseCase.listarSobremesas()).thenReturn(new ResponseEntity<>(list, HttpStatus.OK));

        ResponseEntity<List<Sobremesa>> response = sobremesaController.listarSobremesas();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(list, response.getBody());

    }

    @Test
    void buscarSobremesa() {
        String nomeBanco = "sobremesa";
        Sobremesa sobremesaExistente = this.sobremesa;

        when(sobremesaUseCase.buscarSobremesa(nomeBanco)).thenReturn(new ResponseEntity<>(sobremesaExistente, HttpStatus.OK));

        ResponseEntity<Sobremesa> response = sobremesaController.buscarSobremesa(nomeBanco);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(sobremesa, response.getBody());
    }

    @Test
    void atualizarSobremesa() {
        boolean disponibilidadeNovo = false;
        Sobremesa sobremesaModificado = this.sobremesa;
        sobremesaModificado.setDisponivel(disponibilidadeNovo);

        when(sobremesaUseCase.buscarSobremesa(sobremesaModificado.getNomeBanco())).thenReturn(new ResponseEntity<>(sobremesa, HttpStatus.OK));
        when(sobremesaUseCase.atualizarSobremesa(sobremesa.getNomeBanco(), sobremesaModificado)).thenReturn(new ResponseEntity<>(sobremesaModificado, HttpStatus.OK));

        ResponseEntity response = sobremesaController.atualizarSobremesa(sobremesa.getNomeBanco(), sobremesaModificado);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());

    }

    @Test
    void apagarSobremesa() {
        Sobremesa sobremesaExistente = this.sobremesa;

        when(sobremesaUseCase.buscarSobremesa(sobremesaExistente.getNomeBanco())).thenReturn(new ResponseEntity<>(sobremesaExistente, HttpStatus.OK));
        when(sobremesaUseCase.apagarSobremesa(sobremesaExistente.getNomeBanco())).thenReturn(new ResponseEntity<>(null, HttpStatus.OK));

        ResponseEntity<String> response = sobremesaController.apagarSobremesa(sobremesaExistente.getNomeBanco());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());

    }

}