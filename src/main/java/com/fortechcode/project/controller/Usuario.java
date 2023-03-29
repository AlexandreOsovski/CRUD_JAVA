package com.fortechcode.project.controller;

import com.fortechcode.project.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RestController
@RequestMapping({"/Usuarios/"})
public class Usuario {

    private UsuarioRepository usuarioRepository;

    Usuario(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public List findAll(){ //faz um select * from, que nesse caso é em "Usuarios"
        return usuarioRepository.findAll();
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity findById(@PathVariable long id){
        return usuarioRepository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

//    @PostMapping
//    public Usuario create(@RequestBody Usuario usuario){
//        return usuarioRepository.save(usuario);
//    }


//    @GetMapping("login")
//    public String login(){
//        return "login";
//    }
//
//    @RequestMapping("/cadastro")
//    public String cadastro(){
//        return "cadastro";
//    }
//
//    @RequestMapping("/esqueciSenha")
//    public String esqueciSenha(){
//        return "Usuario/esqueciSenha";
//    }







}
