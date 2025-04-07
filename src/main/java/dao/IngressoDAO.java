package dao;

import config.ConnectionFactory;
import model.Ingresso;

import java.sql.*;
import java.util.List;

public class IngressoDAO implements iIngressoDAO{

    @Override
    public Ingresso create(Ingresso ingresso) {
        try (Connection connection = ConnectionFactory.getConnection()){
            String query = "INSERT INTO ingresso (id_cliente, id_bilheteria, data_venda, pagamento) VALUES (?, ?, ?, ?::forma_pagamento)";
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, ingresso.getId_cliente());
            ps.setLong(2, ingresso.getId_bilheteria());
            ps.setDate(3, Date.valueOf(ingresso.getData_venda()));
            ps.setString(4, ingresso.getPagamento().getNomePostgres().trim());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                ingresso.setId(rs.getLong(1));
            }

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return ingresso;
    }

    @Override
    public void update(Ingresso ingresso) {

    }

    @Override
    public void delete(Ingresso ingresso) {

    }

    @Override
    public Ingresso findById(int id) {
        return null;
    }

    @Override
    public List<Ingresso> findAll() {
        return null;
    }
}