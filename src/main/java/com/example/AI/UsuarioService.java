package com.example.AI;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

@Service
public class UsuarioService {

    private final HashMap<String, Usuario> usuarios = new HashMap<>();

    private boolean textoInvalido(String texto) {
        return texto == null || texto.trim().isEmpty();
    }

    private Usuario buscarUsuarioInclusiveDeleted(String id) {
        Usuario usuario = usuarios.get(id);
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }
        return usuario;
    }

    public Usuario cadastrarUsuario(Usuario usuario) {
        if (usuario == null ||
                textoInvalido(usuario.getNome()) ||
                textoInvalido(usuario.getEmail()) ||
                usuario.getDataNascimento() == null ||
                textoInvalido(usuario.getCpf())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados inválidos");
        }

        usuario.setId(UUID.randomUUID().toString());
        usuario.setDeleted(false);
        usuarios.put(usuario.getId(), usuario);

        return usuario;
    }

    public Collection<Usuario> listarUsuarios() {
        Collection<Usuario> usuariosAtivos = new ArrayList<>();

        for (Usuario usuario : usuarios.values()) {
            if (!usuario.isDeleted()) {
                usuariosAtivos.add(usuario);
            }
        }

        return usuariosAtivos;
    }

    public Usuario buscarUsuario(String id) {
        Usuario usuario = usuarios.get(id);

        if (usuario == null || usuario.isDeleted()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }

        return usuario;
    }

    public Usuario atualizarUsuario(String id, String nome, String email, Date dataNascimento, String cpf) {
        Usuario usuario = buscarUsuario(id);

        if (textoInvalido(nome) ||
                textoInvalido(email) ||
                dataNascimento == null ||
                textoInvalido(cpf)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados inválidos");
        }

        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setDataNascimento(dataNascimento);
        usuario.setCpf(cpf);

        return usuario;
    }

    public void deletarUsuario(String id) {
        Usuario usuario = buscarUsuarioInclusiveDeleted(id);

        if (usuario.isDeleted()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já foi excluído");
        }

        usuario.setDeleted(true);
    }
}