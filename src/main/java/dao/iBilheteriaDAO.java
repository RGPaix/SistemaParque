package dao;

import model.Bilheteria;

import java.util.List;

public interface iBilheteriaDAO {
    Bilheteria create(Bilheteria bilheteria);
    void update(Bilheteria bilheteria);
    void delete(Bilheteria bilheteria);
    Bilheteria findById(int id);
}