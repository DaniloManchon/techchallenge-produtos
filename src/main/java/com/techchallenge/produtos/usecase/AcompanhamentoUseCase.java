package com.techchallenge.produtos.usecase;


import com.techchallenge.produtos.model.produtos.Acompanhamento;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AcompanhamentoUseCase {
    ResponseEntity<String> criarAcompanhamento(Acompanhamento acompanhamento);

    ResponseEntity<Acompanhamento> buscarAcompanhamento(String nomeBanco);

    ResponseEntity<List<Acompanhamento>> listarAcompanhamentos();

    ResponseEntity<String> apagarAcompanhamento(String nomeBanco);

    ResponseEntity<Acompanhamento> atualizarAcompanhamento(String nomeBanco, Acompanhamento acompanhamento);
}
