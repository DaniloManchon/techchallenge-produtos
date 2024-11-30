package com.techchallenge.produtos.service;


import com.techchallenge.produtos.model.produtos.Acompanhamento;
import com.techchallenge.produtos.repository.AcompanhamentoRepository;
import com.techchallenge.produtos.usecase.AcompanhamentoUseCase;
import jakarta.annotation.Nullable;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class AcompanhamentoService implements AcompanhamentoUseCase {

    @Autowired
    AcompanhamentoRepository acompanhamentoRepository;

    public ResponseEntity<String> criarAcompanhamento(Acompanhamento acompanhamento) {
        try {
            if (!buscarAcompanhamento(acompanhamento.getNomeBanco()).hasBody()) {
                acompanhamentoRepository.save(acompanhamento);
                log.info("{} criado", acompanhamento.getNome());
                return new ResponseEntity<>(acompanhamento.getNome() + " salvo no banco de dados", HttpStatus.CREATED);
            } else {
                log.warn("{} já existe no banco de dados", acompanhamento.getNome());
                return new ResponseEntity<>(null, HttpStatus.CONFLICT);
            }
        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Acompanhamento> buscarAcompanhamento(@Nullable String nomeBanco) {
        Optional<Acompanhamento> acompanhamentoData_ = acompanhamentoRepository.findByNomeBanco(nomeBanco);

        if (acompanhamentoData_.isPresent()) {
            return new ResponseEntity<>(acompanhamentoData_.get(), HttpStatus.OK);
        } else {
            log.warn("{} não encontrado no banco de dados", nomeBanco);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<Acompanhamento>> listarAcompanhamentos() {
        List<Acompanhamento> acompanhamentos = acompanhamentoRepository.findAll();
        return new ResponseEntity<>(acompanhamentos, HttpStatus.OK);
    }

    public ResponseEntity<Acompanhamento> atualizarAcompanhamento(String nomeBanco, Acompanhamento acompanhamento) {
        try {
            Acompanhamento acompanhamentoData_ = buscarAcompanhamento(nomeBanco).getBody();
            acompanhamentoData_.setDescricao(acompanhamento.getDescricao());
            acompanhamentoData_.setPreco(acompanhamento.getPreco());
            acompanhamentoRepository.save(acompanhamentoData_);
            log.info("{} atualizado com sucesso", acompanhamentoData_.getNome());
            return new ResponseEntity<>(acompanhamentoData_, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> apagarAcompanhamento(String nomeBanco) {
        try {
            Acompanhamento acompanhamentoData_ = buscarAcompanhamento(nomeBanco).getBody();
            acompanhamentoRepository.delete(acompanhamentoData_);
            log.info("{} excluido", acompanhamentoData_.getNome());
            return new ResponseEntity<>(acompanhamentoData_.getNome() + " apagado", HttpStatus.OK);
        } catch (Exception e) {
            log.error(e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
