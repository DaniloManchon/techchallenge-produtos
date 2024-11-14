package com.techchallenge.produtos.controller;


import com.techchallenge.produtos.model.produtos.Lanche;
import com.techchallenge.produtos.usecase.LancheUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/lanche")
public class LancheController {

    @Autowired
    LancheUseCase lancheUseCase;

    @PostMapping
    public ResponseEntity<String> criarLanche(@RequestBody Lanche lanche) {
        try {
            return lancheUseCase.criarLanche(lanche);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Lanche>> listarLanches() {
        try {
            return lancheUseCase.listarLanches();
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<Lanche> buscarLanche(@RequestParam("nomeBanco") String nomeBanco) {
        try {
            return lancheUseCase.buscarLanche(nomeBanco);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity atualizarLanche(@RequestParam("nomeBanco") String nomeBanco, @RequestBody Lanche lanche) {
        try {
            return lancheUseCase.atualizarLanche(nomeBanco, lanche);
        } catch (Exception e) {
            return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping
    public ResponseEntity apagarLanche(@RequestParam("nomeBanco") String nomeBanco) {
        try {
            return lancheUseCase.apagarLanche(nomeBanco);
        } catch (Exception e) {
            return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
