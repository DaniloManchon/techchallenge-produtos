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
    private boolean disponivel;

    public Produto(String nome, String descricao, float preco, boolean disponivel) {
        this.nome = nome;
        this.nomeBanco = gerarNomeBanco(nome);
        this.descricao = descricao;
        this.preco = preco;
        this.disponivel = disponivel;
    }

    private String gerarNomeBanco(String nome) {
        String nomeBanco = nome.replaceAll(" ", "_").toLowerCase();
        return nomeBanco;
    }
}
