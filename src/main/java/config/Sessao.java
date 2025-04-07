package config;

import lombok.Getter;
import model.Cliente;

public class Sessao {
    @Getter
    public static Cliente usuarioLogado;

    public static void setUsuarioLogado(Cliente cliente) {
        usuarioLogado = cliente;
    }

}