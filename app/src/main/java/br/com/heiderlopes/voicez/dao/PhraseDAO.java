package br.com.heiderlopes.voicez.dao;

import java.sql.SQLException;
import java.util.List;

import br.com.heiderlopes.voicez.model.Phrase;

/**
 * Created by heiderlopes on 11/09/14.
 */
public interface PhraseDAO {

    public int save(Phrase phrase) throws SQLException;
    public int update(Phrase phrase)throws SQLException;
    public int delete(Phrase phrase)throws SQLException;
    public List<Phrase> findAll()throws SQLException;
    public Phrase findById(int id)throws SQLException;
    public List<Phrase> findAllInOrder(String columnName, boolean asc) throws SQLException;
}