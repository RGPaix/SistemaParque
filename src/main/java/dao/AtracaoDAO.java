package dao;

import config.ConnectionFactory;
import model.Atracao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AtracaoDAO implements iAtracaoDAO {

    @Override
    public Atracao create(Atracao atracao){
        try (Connection connection = ConnectionFactory.getConnection()){
            String query = "INSERT INTO atracao (nome, descricao, horario_inicio, horario_fim, capacidade) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, atracao.getNome());
            ps.setString(2, atracao.getDescricao());
            ps.setTime(3, Time.valueOf(atracao.getHorario_inicio()));
            ps.setTime(4, Time.valueOf(atracao.getHorario_fim()));
            ps.setString(5, String.valueOf(atracao.getCapacidade()));
            ps.executeUpdate();
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void update(Atracao atracao){

    }
    @Override
    public void delete(Atracao atracao) {

    }

    @Override
    public Atracao findById(int id) {
        return null;
    }

    @Override
    public List<Atracao> findAll() {
        List<Atracao> atracoes = new ArrayList<>();

        try (Connection connection = ConnectionFactory.getConnection()) {
            String query = "SELECT * FROM atracao";
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Atracao atracao = new Atracao();
                atracao.setId(rs.getLong("id"));
                atracao.setNome(rs.getString("nome"));
                atracao.setDescricao(rs.getString("descricao"));
                atracao.setHorario_inicio(rs.getTime("horario_inicio").toLocalTime());
                atracao.setHorario_fim(rs.getTime("horario_fim").toLocalTime());
                atracao.setCapacidade(rs.getInt("capacidade"));

                atracoes.add(atracao);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return atracoes;
    }
}