package com.fortechcode.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor //cria automaticamente um construtor com todas os atributos da classe como argumento.
@NoArgsConstructor  //cria automaticamente um construtor vazio (sem argumentos).
@Data               //cria automaticamente os métodos toString, equals, hashCode, getters e setters.
@Entity             // pertence ao JPA e isso significa que a classe será automaticamente mapeada à tabela com o mesmo nome (classe Usuario e tabela usuario).
@Table (name = "usuario")
public class UsuarioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //serve para identificar como a coluna id será gerada. Nesse caso será definido pelo próprio banco de dados

    private Long   id;

    @NotBlank(message = "{nome.not.blank}")
    private String nome;

    @NotBlank(message = "{sobrenome.not.blank}")
    private String sobrenome;

//    @NotBlank(message = "{senha.not.blank}")
    private String senha;

    @Email(message = "{email.not.valid}")
    @NotBlank(message = "{email.not.blank}")
    private String email;

    public String getNome() {
        return nome;
    }

    public String setNome(String nome) {

        this.nome = nome;
        return nome;


    }

    public String getSobrenome() {
        return sobrenome;
    }

    public String setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
        return sobrenome;
    }

    public String getSenha() {
        return senha;
    }

    public String setSenha(String senha) {
        this.senha = senha;
        return senha;
    }




}
