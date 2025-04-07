package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Atracao {
    private Long id;
    private String nome;
    private String descricao;
    private LocalTime horario_inicio;
    private LocalTime horario_fim;
    private int capacidade;

    @Override
    public String toString() {
        return nome;
    }
}
