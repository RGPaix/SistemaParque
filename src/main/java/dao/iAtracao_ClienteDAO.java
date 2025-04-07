package dao;

import model.Atracao;
import model.Atracao_Cliente;

import java.util.List;

public interface iAtracao_ClienteDAO {
    Atracao_Cliente create(Atracao_Cliente atracao_cliente);
    void update(Atracao_Cliente atracao_cliente);
    void delete(Atracao_Cliente atracao_cliente);
    Atracao_Cliente findById(int id);
    List<Atracao_Cliente> findAll();
}