package com.fortechcode.project.controller;

import com.fortechcode.project.exception.ResourceNotFoundException;
import com.fortechcode.project.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.fortechcode.project.model.UsuarioModel;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/areadocliente")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    private UsuarioModel usuarioModel;


    @GetMapping
    public String login(final Model model) {

        return "AreaDoCliente/login";
    }

    @GetMapping("/cadastro")
    public String cadastro(final Model model) {
        model.addAttribute("usuarioModel", new UsuarioModel());

        return "AreaDoCliente/cadastro";
    }

    @GetMapping(value = "/perfil/{id}")
    public String getPerfil(Model model, @PathVariable Long id) {
        UsuarioModel usuarioModel = usuarioRepository.getById(id);
        model.addAttribute("usuarioModel", usuarioModel);
        return "AreaDoCliente/perfil";
    }


    @PostMapping
    public String loginUsuario(@Valid @ModelAttribute UsuarioModel usuarioModel, BindingResult result, Model model) {
        UsuarioModel verificaUsuario = usuarioRepository.findByEmail(usuarioModel.getEmail());

        if (verificaUsuario == null || !verificaUsuario.getSenha().equals(usuarioModel.getSenha())) {
            result.rejectValue("email", "incorrectCredentials", "Email or password incorrect");
            return "redirect:/ AreaDoCliente/login";
        } else {
            model.addAttribute("usuarioModel", verificaUsuario);
            return "Dashboard/dashboard";
        }
    }

    @PostMapping(value = {"/salvarUsuario/"})
    public ModelAndView salvaUsuarios(@ModelAttribute @Valid UsuarioModel usuarioModel, BindingResult result) {

        if (result.hasErrors()) {

            ModelAndView modelAndView = new ModelAndView("Usuario/login");
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
        ModelAndView modelAndView = new ModelAndView("redirect:/perfil/" + usuarioModel.getId());
        return modelAndView;
    }

    @PostMapping(value = {"/alterarUsuario/"})
    public ModelAndView updateUsuario(@Valid @ModelAttribute UsuarioModel usuarioAtualizado) {
        long id = usuarioAtualizado.getId();

        UsuarioModel usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id: " + id));

        if (id != usuarioExistente.getId()) {
            throw new IllegalArgumentException("ID do usuário atualizado não corresponde ao ID do usuário existente.");
        }

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
        if (usuarioAtualizado.getSenha() != null && !usuarioAtualizado.getSenha().isEmpty()) {
            usuarioExistente.setSenha(usuarioAtualizado.getSenha());
        }

        UsuarioModel usuarioAtualizadoSalvo = usuarioRepository.save(usuarioExistente);
        ModelAndView modelAndView = new ModelAndView("AreaDoCliente/login");
        return modelAndView;
    }

    @PostMapping(value = {"/deletarUsuario/{id}"})
    public ModelAndView deleteUsuario(@PathVariable long id) {
        UsuarioModel usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id: " + id));

        usuarioRepository.deleteById(usuarioExistente.getId());
        ModelAndView modelAndView = new ModelAndView("AreaDoCliente/login");
        return modelAndView;
    }


}

