package com.example.AI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/usuario")
    public Collection<Usuario> getUsuarios() {
        return usuarioService.listarUsuarios();
    }

    @GetMapping("/usuario/{id}")
    public Usuario getUsuario(@PathVariable String id) {
        return usuarioService.buscarUsuario(id);
    }

    @PostMapping("/usuario")
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario postUsuarios(@RequestBody Usuario usuario) {
        return usuarioService.cadastrarUsuario(usuario);
    }

    @PutMapping("/usuario/{id}")
    public Usuario putUsuario(@PathVariable String id, @RequestBody Usuario usuario) {
        return usuarioService.atualizarUsuario(
                id,
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getDataNascimento(),
                usuario.getCpf()
        );
    }

    @DeleteMapping("/usuario/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUsuario(@PathVariable String id) {
        usuarioService.deletarUsuario(id);
    }
}