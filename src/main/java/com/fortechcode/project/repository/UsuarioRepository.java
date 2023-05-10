package com.fortechcode.project.repository;

import com.fortechcode.project.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long>{
    UsuarioModel findByEmail(String email);
}

