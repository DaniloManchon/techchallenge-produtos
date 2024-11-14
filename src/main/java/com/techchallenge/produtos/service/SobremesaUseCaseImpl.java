package com.techchallenge.produtos.service;


import com.techchallenge.produtos.model.produtos.Sobremesa;
import com.techchallenge.produtos.repository.SobremesaRepository;
import com.techchallenge.produtos.usecase.SobremesaUseCase;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class SobremesaUseCaseImpl implements SobremesaUseCase {

    @Autowired
    SobremesaRepository sobremesaRepository;

    public ResponseEntity<String> criarSobremesa(Sobremesa sobremesa) {
        try {
            if (buscarSobremesa(gerarNomeBanco(sobremesa.getNome())).getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                sobremesaRepository.save(
                        new Sobremesa(
                                sobremesa.getNome(),
                                gerarNomeBanco(sobremesa.getNome()),
                                sobremesa.getDescricao(),
                                sobremesa.getPreco()
                        )
                );
                log.info("{} criado", sobremesa.getNome());
                return new ResponseEntity<>(sobremesa.getNome() + " salvo no banco de dados", HttpStatus.CREATED);
            } else {
                log.warn("{} já existe no banco de dados", sobremesa.getNome());
                return new ResponseEntity<>(null, HttpStatus.CONFLICT);
            }
        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String gerarNomeBanco(String nome) {
        String nomeBanco = nome.replaceAll(" ", "_").toLowerCase();
        return nomeBanco;
    }

    public ResponseEntity<Sobremesa> buscarSobremesa(String nomeBanco) {
        Optional<Sobremesa> sobremesaData_ = sobremesaRepository.findByNomeBanco(nomeBanco);
        if (sobremesaData_.isPresent()) {
            return new ResponseEntity<>(sobremesaData_.get(), HttpStatus.OK);
        } else {
            log.warn("{} não encontrado no banco de dados", nomeBanco);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<Sobremesa>> listarSobremesas() {
        List<Sobremesa> sobremesas = sobremesaRepository.findAll();
        return new ResponseEntity<>(sobremesas, HttpStatus.OK);
    }

    public ResponseEntity<Sobremesa> atualizarSobremesa(String nomeBanco, Sobremesa sobremesa) {
        try {
            Sobremesa sobremesaData_ = buscarSobremesa(nomeBanco).getBody();
            sobremesaData_.setDescricao(sobremesa.getDescricao());
            sobremesaData_.setPreco(sobremesa.getPreco());
            sobremesaRepository.save(sobremesaData_);
            log.info("{} atualizado com sucesso", sobremesaData_.getNome());
            return new ResponseEntity<>(sobremesaData_, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> apagarSobremesa(String nomeBanco) {
        try {
            Sobremesa sobremesaData_ = buscarSobremesa(nomeBanco).getBody();
            sobremesaRepository.delete(sobremesaData_);
            log.info("{} excluido", sobremesaData_.getNome());
            return new ResponseEntity<>(sobremesaData_.getNome() + " apagado", HttpStatus.OK);
        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
