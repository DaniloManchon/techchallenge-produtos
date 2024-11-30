package com.techchallenge.produtos.model.produtos;

import com.techchallenge.produtos.model.Produto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@Document
public class Bebida extends Produto {

    private String tamanho;

    public Bebida(String nome, String descricao, float preco, boolean disponivel, String tamanho) {
        super(nome, descricao, preco, disponivel);
        this.tamanho = tamanho;
        this.setNomeBanco(gerarNomeBanco(nome));
    }

    private String gerarNomeBanco(String nome) {
        String nomeBanco = nome.replaceAll(" ", "_").toLowerCase();
        nomeBanco = nomeBanco + "_" + tamanho;
        return nomeBanco;
    }

}
