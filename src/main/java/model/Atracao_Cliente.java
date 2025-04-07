package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Atracao_Cliente {
    private Long id;
    private Long id_atracao;
    private Long id_ingresso;
    private LocalDateTime horario_uso;
}
