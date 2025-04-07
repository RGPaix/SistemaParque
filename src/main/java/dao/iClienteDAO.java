package dao;

import model.Cliente;

import java.sql.SQLException;
import java.util.List;

public interface iClienteDAO {
    Cliente create(Cliente cliente) throws SQLException;
    void update(Cliente cliente);
    void delete(Cliente cliente);
    List<Cliente> findAll();
}
