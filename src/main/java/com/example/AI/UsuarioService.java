package com.example.AI;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

@Service
public class UsuarioService {

    private final HashMap<String, Usuario> usuarios = new HashMap<>();

    public Usuario cadastrarUsuario(Usuario usuario) {
        if (usuario.getNome() == null ||
                usuario.getEmail() == null ||
                usuario.getDataNascimento() == null ||
                usuario.getCpf() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados inválidos");
        }

        usuario.setId(UUID.randomUUID().toString());
        usuarios.put(usuario.getId(), usuario);

        return usuario;
    }

    public Collection<Usuario> listarUsuarios() {
        return usuarios.values();
    }

    public Usuario buscarUsuario(String id) {
        Usuario usuario = usuarios.get(id);
        if (usuario == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }
        return usuario;
    }

    public Usuario atualizarUsuario(String id, String nome, String email, Date dataNascimento, String cpf) {
        Usuario usuario = buscarUsuario(id);

        if (nome == null || email == null || dataNascimento == null || cpf == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados inválidos");
        }

        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setDataNascimento(dataNascimento);
        usuario.setCpf(cpf);

        return usuario;
    }

    public void deletarUsuario(String id) {
        buscarUsuario(id);
        usuarios.remove(id);
    }
}
