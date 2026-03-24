package com.example.AI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class MotoristaController {

    @Autowired
    private MotoristaService motoristaService;

    @GetMapping("/motorista")
    public Collection<Motorista> getMotoristas() {
        return motoristaService.listarMotoristas();
    }

    @GetMapping("/motorista/{id}")
    public Motorista getMotorista(@PathVariable String id) {
        return motoristaService.buscarMotorista(id);
    }

    @PostMapping("/motorista")
    @ResponseStatus(HttpStatus.CREATED)
    public Motorista postMotoristas(@RequestBody Motorista motorista) {
        return motoristaService.cadastrarMotorista(motorista);
    }

    @PutMapping("/motorista/{id}")
    public Motorista putMotorista(@PathVariable String id, @RequestBody Motorista motorista) {
        return motoristaService.atualizarMotorista(
                id,
                motorista.getNome(),
                motorista.getEmail(),
                motorista.getPlacaCarro(),
                motorista.getCpf()
        );
    }

    @DeleteMapping("/motorista/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMotorista(@PathVariable String id) {
        motoristaService.deletarMotorista(id);
    }
}