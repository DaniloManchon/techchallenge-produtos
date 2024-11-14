package com.techchallenge.produtos.controller;


import com.techchallenge.produtos.model.produtos.Sobremesa;
import com.techchallenge.produtos.usecase.SobremesaUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/sobremesa")
public class SobremesaController {

    @Autowired
    SobremesaUseCase sobremesaUseCase;

    @PostMapping
    public ResponseEntity<String> criarSobremesa(@RequestBody Sobremesa sobremesa) {
        try {
            return sobremesaUseCase.criarSobremesa(sobremesa);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Sobremesa>> listarSobremesas() {
        try {
            return sobremesaUseCase.listarSobremesas();
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<Sobremesa> buscarSobremesa(@RequestParam("nomeBanco") String nomeBanco) {
        try {
            return sobremesaUseCase.buscarSobremesa(nomeBanco);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity atualizarSobremesa(@RequestParam("nomeBanco") String nomeBanco, @RequestBody Sobremesa sobremesa) {
        try {
            return sobremesaUseCase.atualizarSobremesa(nomeBanco, sobremesa);
        } catch (Exception e) {
            return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity apagarSobremesa(@RequestParam("nomeBanco") String nomeBanco) {
        try {
            return sobremesaUseCase.apagarSobremesa(nomeBanco);
        } catch (Exception e) {
            return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
