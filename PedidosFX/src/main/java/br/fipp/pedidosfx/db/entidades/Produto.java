package br.fipp.pedidosfx.db.entidades;

public class Produto {
    private int id;
    private String nome;
    private Categoria categoria;
    private double preco;
    private double estoque;

    public Produto(int id, String nome, Categoria categoria, double preco, double estoque) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
        this.estoque = estoque;
    }

    public Produto() {
        this(0,"",new Categoria(),0,0);
    }

    public Produto(String nome, Categoria categoria, double preco, double estoque) {
        this(0,nome,categoria,preco,estoque);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Categoria getCategoria() {
        return categoria;
    }
    public int getcat_id(){
        return categoria.getId();

    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public double getEstoque() {
        return estoque;
    }

    public void setEstoque(double estoque) {
        this.estoque = estoque;
    }

    @Override
    public String toString() {
        return nome;
    }
}
