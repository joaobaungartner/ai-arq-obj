package com.example.AI;

public class Motorista {

    private String id;
    private String nome;
    private String email;
    private String placaCarro;
    private String cpf;
    private boolean deleted;

    public Motorista() {
    }

    public Motorista(String id, String nome, String email, String placaCarro, String cpf) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.placaCarro = placaCarro;
        this.cpf = cpf;
        this.deleted = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPlacaCarro() {
        return placaCarro;
    }

    public void setPlacaCarro(String placaCarro) {
        this.placaCarro = placaCarro;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}