package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Bilheteria {
    private Long id;
    private double preco;
    private String horario_fechamento;
    private boolean funcionamento;
    private int quantidade_disponivel;

    public boolean estaFechada() {
        return !funcionamento;
    }
    @Override
    public String toString() {
        return "Bilheteria " + id + " - R$" + preco;
    }
}
