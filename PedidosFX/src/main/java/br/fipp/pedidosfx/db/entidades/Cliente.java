package br.fipp.pedidosfx.db.entidades;

public class Cliente {
    private int id;
    private long documento;
    private String nome, endereco, bairro, cidade, cep, uf, email;

    public Cliente() {
        this(0,0,"","","","","","","");
    }

    public Cliente(int id, long documento, String nome, String endereco, String bairro, String cidade, String cep, String uf, String email) {
        this.id = id;
        this.documento = documento;
        this.nome = nome;
        this.endereco = endereco;
        this.bairro = bairro;
        this.cidade = cidade;
        this.cep = cep;
        this.uf = uf;
        this.email = email;
    }

    public Cliente(long documento, String nome, String endereco, String bairro, String cidade, String cep, String uf, String email) {
        this(0,documento, nome, endereco, bairro, cidade, cep, uf, email);
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDocumento() {
        return documento;
    }

    public void setDocumento(long documento) {
        this.documento = documento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return nome;
    }
}
