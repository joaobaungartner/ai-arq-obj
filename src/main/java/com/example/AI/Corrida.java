package com.example.AI;

import java.util.Date;

public class Corrida {

    private String id;
    private Date data;
    private Usuario usuario;
    private Motorista motorista;

    public Corrida() {
    }

    public Corrida(String id, Date data, Usuario usuario, Motorista motorista) {
        this.id = id;
        this.data = data;
        this.usuario = usuario;
        this.motorista = motorista;
    }

    public String getId() {return id;}
    public void setId(String id) {this.id = id;}

    public Date getData() {return data;}
    public void setData(Date data) {this.data = data;}

    public Usuario getUsuario() {return usuario;}
    public void setUsuario(Usuario usuario) {this.usuario = usuario;}

    public Motorista getMotorista() {return motorista;}
    public void setMotorista(Motorista motorista) {this.motorista = motorista;}
}