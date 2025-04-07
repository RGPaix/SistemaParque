package dao;

import config.ConnectionFactory;
import model.Cliente;

import java.sql.*;
import java.util.List;

public class ClienteDAO implements iClienteDAO{

    @Override
    public Cliente create(Cliente cliente) throws SQLException {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String query = "INSERT INTO cliente (nome, cpf, telefone, endereco, senha) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getCpf());
            ps.setString(3, cliente.getTelefone());
            ps.setString(4, cliente.getEndereco());
            ps.setString(5, cliente.getSenha());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                cliente.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("cpf_unico")) {
                throw new SQLException("CPF já cadastrado.");
            } else {
                throw e;
            }
        }
        return cliente;
    }

    @Override
    public void update(Cliente cliente) {

    }

    @Override
    public void delete(Cliente cliente) {

    }

    public Cliente findByCpfAndSenha(String cpf, String senha) {
        Cliente cliente = null;
        try (Connection connection = ConnectionFactory.getConnection()) {
            String query = "SELECT * FROM cliente WHERE cpf = ? AND senha = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, cpf);
            ps.setString(2, senha);
            var rs = ps.executeQuery();
            if (rs.next()) {
                cliente = new Cliente(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("telefone"),
                        rs.getString("endereco"),
                        rs.getString("senha")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cliente;
    }

    @Override
    public List<Cliente> findAll() {
        return null;
    }
}