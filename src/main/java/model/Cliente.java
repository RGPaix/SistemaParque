package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Cliente {
    private Long id;
    private String nome;
    private String cpf;
    private String telefone;
    private String endereco;
    private String senha;

    public Cliente(String nome, String cpf, String endereco, String telefone, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
        this.telefone = telefone;
        this.senha = senha;
    }
}