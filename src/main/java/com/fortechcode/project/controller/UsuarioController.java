package com.fortechcode.project.controller;

import com.fortechcode.project.exception.UsuarioException;
import com.fortechcode.project.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.fortechcode.project.model.UsuarioModel;

import java.util.List;

@Controller
@RestController
@RequestMapping({"/Usuarios/"})
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    private UsuarioModel usuarioModel;

    private String resultado;
    private String nome;
    private String senha;
    private String email;
    private String sobrenome;


    UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/login")
    public String login1() {
        return "Site/site";
    }

    @PostMapping("/salvarUsuario/")
    public @ResponseBody String salvaUsuarios(@RequestBody UsuarioModel usuarioModel) {
            UsuarioModel salvarUsuario = new UsuarioModel();

            salvarUsuario.setNome(usuarioModel.getNome());
            salvarUsuario.setSenha(usuarioModel.getSenha());
            salvarUsuario.setSobreNome(usuarioModel.getSobreNome());

            usuarioRepository.save(usuarioModel);

            return login1();
    }

    @GetMapping("/Usuarios/getUsuarios")
    public List findAll() { //faz um select * from, que nesse caso é em "Usuarios"
        if (usuarioRepository.findAll() == null) {
            System.out.println("Sem usuarios");
        }
        return usuarioRepository.findAll();
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity findById(@PathVariable long id) {
        return usuarioRepository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/Usuario/alterarUsuario/{id}")
    public ResponseEntity<UsuarioModel> updateUsuario(@PathVariable long id,@RequestBody UsuarioModel usuarioModel) {
        UsuarioModel salvarUsuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioException("User not exist with id: " + id));

        salvarUsuario.setNome(usuarioModel.getNome());
        salvarUsuario.setSenha(usuarioModel.getSenha());
        salvarUsuario.setSobreNome(usuarioModel.getSobreNome());

        usuarioRepository.save(salvarUsuario);

        return ResponseEntity.ok(salvarUsuario);
    }
}

