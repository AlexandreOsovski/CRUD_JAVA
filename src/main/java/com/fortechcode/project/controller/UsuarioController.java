package com.fortechcode.project.controller;

import com.fortechcode.project.exception.ResourceNotFoundException;
import com.fortechcode.project.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.fortechcode.project.model.UsuarioModel;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;


@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    private UsuarioModel usuarioModel;

    UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }



    private ModelAndView home(){
        ModelAndView modelAndView = new ModelAndView("Dashboard/dashboard");
        return modelAndView;
    }

    @RequestMapping(value = "/usuarioLogin/", method = RequestMethod.POST)
    public String loginUsuario(@Valid @ModelAttribute UsuarioModel usuarioModel, BindingResult result) {
        UsuarioModel verificaUsuario = usuarioRepository.findByEmail(usuarioModel.getEmail());
        RedirectView redirectView = new RedirectView();
        if (verificaUsuario == null || !verificaUsuario.getSenha().equals(usuarioModel.getSenha())) {
            result.rejectValue("email", "email or password incorrect");
//            ModelAndView modelAndView = new ModelAndView("Usuario/login");
//            return modelAndView;
        }

        return "redirect:/Dashboard/dashboard";
    }


    @RequestMapping(value = {"/getUsuarios/{id}"}, method = RequestMethod.GET)
    public ResponseEntity findById(@PathVariable long id) {
        return usuarioRepository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());

    }

    @RequestMapping(value = "/salvarUsuario/", method = RequestMethod.POST)
    public @ResponseBody ModelAndView salvaUsuarios(@ModelAttribute @Valid UsuarioModel usuarioModel, BindingResult result) {

        if (result.hasErrors()) {
            ModelAndView modelAndView =  new ModelAndView("Usuario/login");
            return modelAndView;
        }

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
            if(usuarioModel.getEmail() == null || usuarioModel.getEmail().isBlank()){
                throw new IllegalArgumentException("O campo email é obrigatório");
            }
            if (usuarioModel.getSenha() == null || usuarioModel.getSenha().isBlank()) {
                throw new IllegalArgumentException("O campo senha é obrigatório");
            }
        }
        usuarioRepository.save(usuarioModel);
        ModelAndView modelAndView = new ModelAndView("Usuario/login");
        return modelAndView;
    }


    @PutMapping("/alterarUsuario/{id}")
    public ResponseEntity<UsuarioModel> updateUsuario(@PathVariable long id, @RequestBody UsuarioModel usuarioAtualizado) {

        UsuarioModel usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id: " + id));

        if (usuarioAtualizado.getNome() != null) {
            usuarioExistente.setNome(usuarioAtualizado.getNome());
        }
        if (usuarioAtualizado.getSobrenome() != null) {
            usuarioExistente.setSobrenome(usuarioAtualizado.getSobrenome());
        }
        if (usuarioAtualizado.getEmail() != null) {
            UsuarioModel verificaEmail = usuarioRepository.findByEmail(usuarioAtualizado.getEmail());
            if (verificaEmail != null && verificaEmail.getId() != usuarioExistente.getId()) {
                throw new IllegalArgumentException("Já existe esse email cadastrado em nosso banco de dados: " + usuarioAtualizado.getEmail());
            }
            usuarioExistente.setEmail(usuarioAtualizado.getEmail());
        }
        if (usuarioAtualizado.getSenha() != null) {
            usuarioExistente.setSenha(usuarioAtualizado.getSenha());
        }

        UsuarioModel usuarioAtualizadoSalvo = usuarioRepository.save(usuarioExistente);
        return ResponseEntity.ok(usuarioAtualizadoSalvo);
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

