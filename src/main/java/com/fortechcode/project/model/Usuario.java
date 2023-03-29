package com.fortechcode.project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor //cria automaticamente um construtor com todas os atributos da classe como argumento.
@NoArgsConstructor  //cria automaticamente um construtor vazio (sem argumentos).
@Data               //cria automaticamente os métodos toString, equals, hashCode, getters e setters.
@Entity             // pertence ao JPA e isso significa que a classe será automaticamente mapeada à tabela com o mesmo nome (classe Usuario e tabela usuario).
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //serve para identificar como a coluna id será gerada. Nesse caso será definido pelo próprio banco de dados

    private Long    id;
    private String  nome;
    private String  sobreNome;
    private String  senha;
}
