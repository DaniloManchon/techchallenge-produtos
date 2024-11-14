package com.techchallenge.produtos.model.produtos;

import com.techchallenge.produtos.model.Produto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document
public class Sobremesa extends Produto {
    public Sobremesa(String nome, String nomeBanco, String descricao, float preco) {
        super(nome, nomeBanco, descricao, preco);
    }
}
