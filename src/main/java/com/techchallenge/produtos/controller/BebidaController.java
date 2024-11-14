package com.techchallenge.produtos.controller;


import com.techchallenge.produtos.model.produtos.Bebida;
import com.techchallenge.produtos.usecase.BebidaUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/bebida")
public class BebidaController {

    @Autowired
    BebidaUseCase bebidaUseCase;

    @PostMapping
    public ResponseEntity<String> criarBebida(@RequestBody Bebida bebida) {
        try {
            return bebidaUseCase.criarBebida(bebida);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Bebida>> listarBebidas() {
        try {
            return bebidaUseCase.listarBebidas();
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<Bebida> buscarBebidaNome(@RequestParam("nomeBanco") String nomeBanco) {
        try {
            return bebidaUseCase.buscarBebida(nomeBanco);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping
    public ResponseEntity atualizarBebidas(@RequestParam("nomeBanco") String nomeBanco, @RequestBody Bebida bebida) {
        try {
            return bebidaUseCase.atualizarBebida(nomeBanco, bebida);
        } catch (Exception e) {
            return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity apagarBebida(@RequestParam("nomeBanco") String nomeBanco) {
        try {
            return bebidaUseCase.apagarBebida(nomeBanco);
        } catch (Exception e) {
            return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
