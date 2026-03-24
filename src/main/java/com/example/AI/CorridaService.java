package com.example.AI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public Corrida cadastrarCorrida(Corrida corrida) {
        if (corrida.getData() == null ||
                corrida.getUsuario() == null ||
                corrida.getMotorista() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados inválidos");
        }

        Usuario usuarioExistente = usuarioService.buscarUsuario(corrida.getUsuario().getId());
        Motorista motoristaExistente = motoristaService.buscarMotorista(corrida.getMotorista().getId());

        corrida.setId(UUID.randomUUID().toString());
        corrida.setUsuario(usuarioExistente);
        corrida.setMotorista(motoristaExistente);

        corridas.put(corrida.getId(), corrida);
        return corrida;
    }

    public Collection<Corrida> listarCorridas(){return corridas.values();}

    public Corrida buscarCorrida(String id) {
        Corrida corrida = corridas.get(id);
        if (corrida == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Música não encontrada");
        }
        return corrida;
    }

    public Corrida atualizarCorrida(String id, Date data, String usuarioId, String motoristaId) {
        Corrida corrida = buscarCorrida(id);
        if (data == null ||
                usuarioId == null ||
                motoristaId == null) {
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
        buscarCorrida(id);
        corridas.remove(id);
    }
}
