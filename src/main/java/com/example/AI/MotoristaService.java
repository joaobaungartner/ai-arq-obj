package com.example.AI;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

@Service
public class MotoristaService {

    private final HashMap<String, Motorista> motoristas = new HashMap<>();

    public Motorista cadastrarMotorista(Motorista motorista) {
        if (motorista.getNome() == null ||
                motorista.getEmail() == null ||
                motorista.getPlacaCarro() == null ||
                motorista.getCpf() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados inválidos");
        }

        motorista.setId(UUID.randomUUID().toString());
        motoristas.put(motorista.getId(), motorista);

        return motorista;
    }

    public Collection<Motorista> listarMotoristas() {
        return motoristas.values();
    }

    public Motorista buscarMotorista(String id) {
        Motorista motorista = motoristas.get(id);
        if (motorista == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Motorista não encontrado");
        }
        return motorista;
    }

    public Motorista atualizarMotorista(String id, String nome, String email, String placaCarro, String cpf) {
        Motorista motorista = buscarMotorista(id);

        if (nome == null || email == null || placaCarro == null || cpf == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados inválidos");
        }

        motorista.setNome(nome);
        motorista.setEmail(email);
        motorista.setPlacaCarro(placaCarro);
        motorista.setCpf(cpf);

        return motorista;
    }

    public void deletarMotorista(String id) {
        buscarMotorista(id);
        motoristas.remove(id);
    }
}
