package dao;

import config.ConnectionFactory;
import model.Atracao;
import model.Atracao_Cliente;

import java.sql.*;
import java.util.List;

public class Atracao_ClienteDAO implements iAtracao_ClienteDAO {

    @Override
    public Atracao_Cliente create(Atracao_Cliente atracao_cliente) {
        String sql = "INSERT INTO atracao_cliente (id_atracao, id_ingresso, horario_uso) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, atracao_cliente.getId_atracao());
            ps.setLong(2, atracao_cliente.getId_ingresso());
            ps.setTimestamp(3, Timestamp.valueOf(atracao_cliente.getHorario_uso()));
            ps.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return atracao_cliente;
    }

    public boolean ingressoJaUsadoNaAtracao(Long id_atracao, Long id_ingresso) {
        String sql = "SELECT COUNT(*) FROM atracao_cliente WHERE id_atracao = ? AND id_ingresso = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id_atracao);
            ps.setLong(2, id_ingresso);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // se já existe, já foi usado nessa atração
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar ingresso", e);
        }
        return false;
    }

    public void registrarUso(Atracao_Cliente ac) {
        String sql = "INSERT INTO atracao_cliente (id_atracao, id_ingresso, horario_uso) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, ac.getId_atracao());
            ps.setLong(2, ac.getId_ingresso());
            ps.setTimestamp(3, Timestamp.valueOf(ac.getHorario_uso()));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao registrar uso", e);
        }
    }

    @Override
    public void update(Atracao_Cliente atracao_cliente){

    }
    @Override
    public void delete(Atracao_Cliente atracao_cliente) {

    }

    @Override
    public Atracao_Cliente findById(int id) {
        return null;
    }

    @Override
    public List<Atracao_Cliente> findAll() {
        return null;
    }
}