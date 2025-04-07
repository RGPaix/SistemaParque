package dao;

import config.ConnectionFactory;
import model.Bilheteria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BilheteriaDAO implements iBilheteriaDAO {

    @Override
    public Bilheteria create(Bilheteria bilheteria) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String query = "INSERT INTO bilheteria (preco, horario_fechamento, funcionamento, quantidade_disponivel) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setDouble(1, bilheteria.getPreco());
            ps.setString(2, bilheteria.getHorario_fechamento());
            ps.setBoolean(3, bilheteria.isFuncionamento());
            ps.setInt(4, bilheteria.getQuantidade_disponivel());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void update(Bilheteria bilheteria) {
    }

    @Override
    public void delete(Bilheteria bilheteria) {
    }

    @Override
    public Bilheteria findById(int id) {
        return null;
    }

    public static List<Bilheteria> findAll() {
        List<Bilheteria> bilheterias = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection()) {
            String sql = "SELECT * FROM bilheteria";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Bilheteria b = new Bilheteria();
                b.setId((long) rs.getInt("id"));
                b.setPreco(rs.getDouble("preco"));
                b.setHorario_fechamento(rs.getString("horario_fechamento"));
                b.setFuncionamento(rs.getBoolean("funcionamento"));
                b.setQuantidade_disponivel(rs.getInt("quantidade_disponivel"));
                bilheterias.add(b);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bilheterias;
    }
    public void registrarVenda(Bilheteria bilheteria) {
        String updateSql = "UPDATE bilheteria SET quantidade_disponivel = quantidade_disponivel - 1 WHERE id = ?";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement  ps =  connection.prepareStatement(updateSql)) {
            ps.setLong(1, bilheteria.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
