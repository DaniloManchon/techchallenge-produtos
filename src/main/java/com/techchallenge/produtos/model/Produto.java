package com.techchallenge.produtos.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Produto {

    @JsonIgnore
    @Id
    private String id;
    private String nome;

    //nome do produto em snake case para facilitar buscas no banco de dados
    @JsonProperty(value = "nome_banco", access = JsonProperty.Access.READ_ONLY)
    private String nomeBanco;

    private String descricao;
    private float preco;

    public Produto(String nome, String nomeBanco, String descricao, float preco) {
        this.nome = nome;
        this.nomeBanco = nomeBanco;
        this.descricao = descricao;
        this.preco = preco;
    }
}
