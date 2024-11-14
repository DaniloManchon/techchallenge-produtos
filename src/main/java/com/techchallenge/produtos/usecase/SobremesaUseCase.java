package com.techchallenge.produtos.usecase;


import com.techchallenge.produtos.model.produtos.Sobremesa;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SobremesaUseCase {
    ResponseEntity<String> criarSobremesa(Sobremesa sobremesa);

    ResponseEntity<Sobremesa> buscarSobremesa(String nomeBanco);

    ResponseEntity<List<Sobremesa>> listarSobremesas();

    ResponseEntity<String> apagarSobremesa(String nomeBanco);

    ResponseEntity<Sobremesa> atualizarSobremesa(String nomeBanco, Sobremesa sobremesa);
}
