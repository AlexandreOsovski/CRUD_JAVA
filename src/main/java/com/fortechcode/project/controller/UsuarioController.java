package com.fortechcode.project.controller;

import com.fortechcode.project.exception.UsuarioException;
import com.fortechcode.project.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public String login1() {
        return "Site/site";
    }

    @GetMapping("/getUsuarios")
    public List findAll() { //faz um select * from, que nesse caso é em "Usuarios"
        if (usuarioRepository.findAll() == null) {
            System.out.println("Sem usuarios");
        }
        return usuarioRepository.findAll();
    }

    @GetMapping(path = {"/getUsuarios/{id}"})
    public ResponseEntity findById(@PathVariable long id) {
        return usuarioRepository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/salvarUsuario/")
    public @ResponseBody String salvaUsuarios(@RequestBody UsuarioModel usuarioModel) {

        UsuarioModel verificaEmail = usuarioRepository.findByEmail(usuarioModel.getEmail());

        if (verificaEmail != null && verificaEmail.getId() != usuarioModel.getId()) {
            throw new IllegalArgumentException("Já existe esse email cadastrado em nosso banco de dados: " + usuarioModel.getEmail());
        }else{
            if (usuarioModel.getNome() == null || usuarioModel.getNome().isBlank()) {
                throw new IllegalArgumentException("O campo nome é obrigatório");
            }
            if (usuarioModel.getSobrenome() == null || usuarioModel.getSobrenome().isBlank()) {
                throw new IllegalArgumentException("O campo sobrenome é obrigatório");
            }
            if(usuarioModel.getSobrenome() == null || usuarioModel.getSobrenome().isBlank()){
                throw new IllegalArgumentException("O campo email é obrigatório");
            }
            if (usuarioModel.getSenha() == null || usuarioModel.getSenha().isBlank()) {
                throw new IllegalArgumentException("O campo senha é obrigatório");
            }
        }
        usuarioRepository.save(usuarioModel);
        return login1();
    }

    @PutMapping("/alterarUsuario/{id}")
    public ResponseEntity<UsuarioModel> updateUsuario(@PathVariable long id, @Valid @RequestBody UsuarioModel usuarioModel) {


        UsuarioModel verificaEmail = usuarioRepository.findByEmail(usuarioModel.getEmail());

        if (verificaEmail != null && verificaEmail.getId() != usuarioModel.getId()) {
            throw new IllegalArgumentException("Já existe esse email cadastrado em nosso banco de dados: " + usuarioModel.getEmail());
        }else {
            if (!usuarioModel.getNome().equals(usuarioModel.getNome())) {
                usuarioModel.setNome(usuarioModel.getNome());
            }
            if (!usuarioModel.getSobrenome().equals(usuarioModel.getSobrenome())) {
                usuarioModel.setSobrenome(usuarioModel.getSobrenome());
            }
            if (!usuarioModel.getEmail().equals(usuarioModel.getEmail())) {
                usuarioModel.setEmail(usuarioModel.getEmail());
            }
            if (!usuarioModel.getSenha().equals(usuarioModel.getSenha())) {
                usuarioModel.setSenha(usuarioModel.getSenha());
            }
        }
        usuarioRepository.save(usuarioModel);
        return ResponseEntity.ok(usuarioModel);
    }


    @DeleteMapping(path ={"/deleteUsuario/{id}"})
    public ResponseEntity <?> deleteUsuario(@PathVariable long id) {

        return usuarioRepository.findById(id)
                .map(record -> {
                    usuarioRepository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

}

