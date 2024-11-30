package com.techchallenge.produtos.controller;

import com.techchallenge.produtos.model.produtos.Acompanhamento;
import com.techchallenge.produtos.usecase.AcompanhamentoUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/acompanhamento")
public class AcompanhamentoController {

    @Autowired
    AcompanhamentoUseCase acompanhamentoUseCase;

    @PostMapping
    public ResponseEntity<String> criarAcompanhamento(@RequestBody Acompanhamento acompanhamento) {
        try {
            return acompanhamentoUseCase.criarAcompanhamento(acompanhamento);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Acompanhamento>> listarAcompanhamentos() {
        try {
            return acompanhamentoUseCase.listarAcompanhamentos();
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<Acompanhamento> buscarAcompanhamento(@RequestParam("nomeBanco") String nomeBanco) {
        try {
            return acompanhamentoUseCase.buscarAcompanhamento(nomeBanco);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity atualizarAcompanhamento(@RequestParam("nomeBanco") String nomeBanco, @RequestBody Acompanhamento acompanhamento) {
        try {
            return acompanhamentoUseCase.atualizarAcompanhamento(nomeBanco, acompanhamento);
        } catch (Exception e) {
            return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity apagarAcompanhamento(@RequestParam("nomeBanco") String nomeBanco) {
        try {
            return acompanhamentoUseCase.apagarAcompanhamento(nomeBanco);
        } catch (Exception e) {
            return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
