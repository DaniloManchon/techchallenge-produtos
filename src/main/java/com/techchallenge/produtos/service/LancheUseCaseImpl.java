package com.techchallenge.produtos.service;


import com.techchallenge.produtos.model.produtos.Lanche;
import com.techchallenge.produtos.repository.LancheRepository;
import com.techchallenge.produtos.usecase.LancheUseCase;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class LancheUseCaseImpl implements LancheUseCase {

    @Autowired
    LancheRepository lancheRepository;

    public ResponseEntity<String> criarLanche(Lanche lanche) {
        try {
            if (buscarLanche(gerarNomeBanco(lanche.getNome())).getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                lancheRepository.save(
                        new Lanche(
                                lanche.getNome(),
                                gerarNomeBanco(lanche.getNome()),
                                lanche.getDescricao(),
                                lanche.getPreco()
                        )
                );
                log.info("{} criado", lanche.getNome());
                return new ResponseEntity<>(lanche.getNome() + " salvo no banco de dados", HttpStatus.CREATED);
            } else {
                log.warn("{} já existe no banco de dados", lanche.getNome());
                return new ResponseEntity<>(null, HttpStatus.CONFLICT);
            }
        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public String gerarNomeBanco(String nome) {
        String nomeBanco = nome.replaceAll(" ", "_").toLowerCase();
        return nomeBanco;
    }

    public ResponseEntity<Lanche> buscarLanche(String nomeBanco) {
        Optional<Lanche> lancheData_ = lancheRepository.findByNomeBanco(nomeBanco);
        if (lancheData_.isPresent()) {
            return new ResponseEntity<>(lancheData_.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<Lanche>> listarLanches() {
        List<Lanche> lanches = lancheRepository.findAll();
        return new ResponseEntity<>(lanches, HttpStatus.OK);
    }

    public ResponseEntity<Lanche> atualizarLanche(String nomeBanco, Lanche lanche) {
        try {
            Lanche lancheData_ = buscarLanche(nomeBanco).getBody();
            lancheData_.setDescricao(lanche.getDescricao());
            lancheData_.setPreco(lanche.getPreco());
            lancheRepository.save(lancheData_);
            log.info("{} atualizado com sucesso", lancheData_.getNome());
            return new ResponseEntity<>(lancheData_, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> apagarLanche(String nomeBanco) {
        try {
            Lanche lancheData_ = buscarLanche(nomeBanco).getBody();
            lancheRepository.delete(lancheData_);
            log.info("{} excluido", lancheData_.getNome());
            return new ResponseEntity<>(lancheData_.getNome() + " apagado", HttpStatus.OK);
        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
