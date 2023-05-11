package com.fortechcode.project.repository;

import com.fortechcode.project.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long>{
    UsuarioModel findByEmail(String email);
    Optional<UsuarioModel> findById(Long id);

    UsuarioModel findBySenha(String senha);

}

