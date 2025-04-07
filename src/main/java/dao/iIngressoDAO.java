package dao;

import model.Ingresso;

import java.util.List;

public interface iIngressoDAO {
    Ingresso create(Ingresso ingresso);
    void update(Ingresso ingresso);
    void delete(Ingresso ingresso);
    Ingresso findById(int id);
    List<Ingresso> findAll();
}
