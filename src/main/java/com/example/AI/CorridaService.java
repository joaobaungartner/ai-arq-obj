package com.example.AI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

@Service
public class CorridaService {

    private final HashMap<String, Corrida> corridas = new HashMap<>();

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MotoristaService motoristaService;

    private boolean textoInvalido(String texto) {
        return texto == null || texto.trim().isEmpty();
    }

    private Corrida buscarCorridaInclusiveDeleted(String id) {
        Corrida corrida = corridas.get(id);
        if (corrida == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Corrida não encontrada");
        }
        return corrida;
    }

    public Corrida cadastrarCorrida(Corrida corrida, String usuarioId) {
        if (corrida == null ||
                corrida.getData() == null ||
                corrida.getMotorista() == null ||
                textoInvalido(usuarioId) ||
                textoInvalido(corrida.getMotorista().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados inválidos");
        }

        Usuario usuarioExistente = usuarioService.buscarUsuario(usuarioId);
        Motorista motoristaExistente = motoristaService.buscarMotorista(corrida.getMotorista().getId());

        corrida.setId(UUID.randomUUID().toString());
        corrida.setUsuario(usuarioExistente);
        corrida.setMotorista(motoristaExistente);
        corrida.setDeleted(false);

        corridas.put(corrida.getId(), corrida);
        return corrida;
    }

    public Collection<Corrida> listarCorridas() {
        Collection<Corrida> corridasAtivas = new ArrayList<>();

        for (Corrida corrida : corridas.values()) {
            if (!corrida.isDeleted()) {
                corridasAtivas.add(corrida);
            }
        }

        return corridasAtivas;
    }

    public Corrida buscarCorrida(String id) {
        Corrida corrida = corridas.get(id);

        if (corrida == null || corrida.isDeleted()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Corrida não encontrada");
        }

        return corrida;
    }

    public Corrida atualizarCorrida(String id, Date data, String usuarioId, String motoristaId) {
        Corrida corrida = buscarCorrida(id);

        if (data == null ||
                textoInvalido(usuarioId) ||
                textoInvalido(motoristaId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados inválidos");
        }

        Usuario usuarioExistente = usuarioService.buscarUsuario(usuarioId);
        Motorista motoristaExistente = motoristaService.buscarMotorista(motoristaId);

        corrida.setData(data);
        corrida.setUsuario(usuarioExistente);
        corrida.setMotorista(motoristaExistente);

        return corrida;
    }

    public void deletarCorrida(String id) {
        Corrida corrida = buscarCorridaInclusiveDeleted(id);

        if (corrida.isDeleted()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Corrida já foi excluída");
        }

        corrida.setDeleted(true);
    }
}