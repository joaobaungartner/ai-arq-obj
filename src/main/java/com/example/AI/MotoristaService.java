package com.example.AI;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

@Service
public class MotoristaService {

    private final HashMap<String, Motorista> motoristas = new HashMap<>();

    private boolean textoInvalido(String texto) {
        return texto == null || texto.trim().isEmpty();
    }

    private Motorista buscarMotoristaInclusiveDeleted(String id) {
        Motorista motorista = motoristas.get(id);
        if (motorista == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Motorista não encontrado");
        }
        return motorista;
    }

    public Motorista cadastrarMotorista(Motorista motorista) {
        if (motorista == null ||
                textoInvalido(motorista.getNome()) ||
                textoInvalido(motorista.getEmail()) ||
                textoInvalido(motorista.getPlacaCarro()) ||
                textoInvalido(motorista.getCpf())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados inválidos");
        }

        motorista.setId(UUID.randomUUID().toString());
        motorista.setDeleted(false);
        motoristas.put(motorista.getId(), motorista);

        return motorista;
    }

    public Collection<Motorista> listarMotoristas() {
        Collection<Motorista> motoristasAtivos = new ArrayList<>();

        for (Motorista motorista : motoristas.values()) {
            if (!motorista.isDeleted()) {
                motoristasAtivos.add(motorista);
            }
        }

        return motoristasAtivos;
    }

    public Motorista buscarMotorista(String id) {
        Motorista motorista = motoristas.get(id);

        if (motorista == null || motorista.isDeleted()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Motorista não encontrado");
        }

        return motorista;
    }

    public Motorista atualizarMotorista(String id, String nome, String email, String placaCarro, String cpf) {
        Motorista motorista = buscarMotorista(id);

        if (textoInvalido(nome) ||
                textoInvalido(email) ||
                textoInvalido(placaCarro) ||
                textoInvalido(cpf)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados inválidos");
        }

        motorista.setNome(nome);
        motorista.setEmail(email);
        motorista.setPlacaCarro(placaCarro);
        motorista.setCpf(cpf);

        return motorista;
    }

    public void deletarMotorista(String id) {
        Motorista motorista = buscarMotoristaInclusiveDeleted(id);

        if (motorista.isDeleted()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Motorista já foi excluído");
        }

        motorista.setDeleted(true);
    }
}