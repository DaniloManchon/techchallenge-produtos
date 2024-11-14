package com.techchallenge.produtos.repository;


import com.techchallenge.produtos.model.produtos.Bebida;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BebidaRepository extends MongoRepository<Bebida, String> {
    Optional<Bebida> findByNomeBanco(String nomeBanco);

}
