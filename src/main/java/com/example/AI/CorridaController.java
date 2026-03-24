package com.example.AI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@RestController
public class CorridaController {

    @Autowired
    private CorridaService corridaService;

    @GetMapping("/corrida")
    public Collection<Corrida> getCorridas() {
        return corridaService.listarCorridas();
    }

    @GetMapping("/corrida/{id}")
    public Corrida getCorrida(@PathVariable String id) {
        return corridaService.buscarCorrida(id);
    }

    @PostMapping("/corrida")
    @ResponseStatus(HttpStatus.CREATED)
    public Corrida postCorrida(@RequestBody Corrida corrida,
                               @RequestHeader("X-USER-ID") String usuarioId) {
        return corridaService.cadastrarCorrida(corrida, usuarioId);
    }

    @PutMapping("/corrida/{id}")
    public Corrida putCorrida(@PathVariable String id, @RequestBody Corrida corrida) {
        if (corrida.getUsuario() == null || corrida.getMotorista() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário e motorista são obrigatórios");
        }

        return corridaService.atualizarCorrida(
                id,
                corrida.getData(),
                corrida.getUsuario().getId(),
                corrida.getMotorista().getId()
        );
    }

    @DeleteMapping("/corrida/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCorrida(@PathVariable String id) {
        corridaService.deletarCorrida(id);
    }
}