package com.techchallenge.produtos.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoTest {

    @Test
    public void testProdutoConstructor() {
        String nome = "Whooper";
        String nomeBanco = "whooper";
        String descricao = "um lanche";
        float preco = 9.90f;
        boolean disponivel = true;

        Produto produto = new Produto(nome, descricao, preco, disponivel);

        assertEquals(nome, produto.getNome());
        assertEquals(nomeBanco, produto.getNomeBanco());
        assertEquals(preco, produto.getPreco());
        assertTrue(produto.isDisponivel());
    }

    @Test
    public void testSettersAndGetters() {
        Produto produto = new Produto("Whooper", "um lanche", 9.90f, true);

        produto.setDisponivel(false);
        assertFalse(produto.isDisponivel());

        produto.setPreco(8.50f);
        assertEquals(8.50f, produto.getPreco());
    }
}