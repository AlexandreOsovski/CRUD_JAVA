package com.fortechcode.project.model;

import jakarta.persistence.*;
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

    private Long    id;
    private String  nome;
    private String  senha;

    @Column(name = "sobre_nome")
    private String  sobreNome;

    public String getNome() {
        return nome;
    }

    public String setNome(String nome) {
        this.nome = nome;
        return nome;
    }

    public String getSenha() {
        return senha;
    }

    public String setSenha(String senha) {
        this.senha = senha;
        return senha;
    }


    public String getSobreNome() {
        return sobreNome;
    }

    public String setSobreNome(String sobreNome) {
        this.sobreNome = sobreNome;
        return sobreNome;
    }

}
