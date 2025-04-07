package dao;

import model.Atracao;
import java.util.List;

public interface iAtracaoDAO {
    Atracao create(Atracao atracao);
    void update(Atracao atracao);
    void delete(Atracao atracao);
    Atracao findById(int id);
    List<Atracao> findAll();
}