package com.techchallenge.produtos.service;

import com.techchallenge.produtos.model.produtos.Sobremesa;
import com.techchallenge.produtos.repository.SobremesaRepository;
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

class SobremesaServiceTest {

    @Mock
    SobremesaRepository sobremesaRepository;

    @InjectMocks
    SobremesaService sobremesaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    Sobremesa sobremesa = new Sobremesa("sobremesa","uma sobremesa", 5.0f, true);

    @Test
    void criarSobremesa() {
        String nomeBanco = "sobremesa";
        Sobremesa sobremesaNovo = this.sobremesa;

        when(sobremesaRepository.findByNomeBanco(nomeBanco)).thenReturn(Optional.empty());
        when(sobremesaRepository.save(sobremesaNovo)).thenReturn(sobremesaNovo);

        ResponseEntity<String> response = sobremesaService.criarSobremesa(sobremesaNovo);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertNotEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(sobremesaRepository).save(sobremesaNovo);
    }

    @Test
    void buscarSobremesa() {
        String nomeBanco = "sobremesa";
        Sobremesa sobremesaExistente = this.sobremesa;

        when(sobremesaRepository.findByNomeBanco(nomeBanco)).thenReturn(Optional.of(sobremesaExistente));

        ResponseEntity<Sobremesa> response = sobremesaService.buscarSobremesa(nomeBanco);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(sobremesa, response.getBody());
    }

    @Test
    void listarSobremesas() {
        List<Sobremesa> list = new ArrayList<>();
        list.add(sobremesa);

        when(sobremesaRepository.findAll()).thenReturn(list);

        ResponseEntity<List<Sobremesa>> response = sobremesaService.listarSobremesas();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }

    @Test
    void atualizarSobremesa() {
        boolean disponibilidadeNovo = false;
        Sobremesa sobremesaModificado = this.sobremesa;
        sobremesaModificado.setDisponivel(disponibilidadeNovo);

        when(sobremesaRepository.findByNomeBanco(sobremesa.getNomeBanco())).thenReturn(Optional.of(sobremesa));

        ResponseEntity<Sobremesa> response = sobremesaService.atualizarSobremesa(sobremesa.getNomeBanco(), sobremesaModificado);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(disponibilidadeNovo, response.getBody().isDisponivel());
    }

    @Test
    void apagarSobremesa() {
        Sobremesa sobremesa = this.sobremesa;

        when(sobremesaRepository.save(sobremesa)).thenReturn(sobremesa);
        when(sobremesaRepository.findByNomeBanco(sobremesa.getNomeBanco())).thenReturn(Optional.of(sobremesa));

        ResponseEntity<String> response = sobremesaService.apagarSobremesa(sobremesa.getNomeBanco());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(sobremesaRepository).delete(sobremesa);
    }

}