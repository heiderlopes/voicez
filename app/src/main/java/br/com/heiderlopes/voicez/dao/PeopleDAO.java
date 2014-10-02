package br.com.heiderlopes.voicez.dao;

import java.sql.SQLException;
import java.util.List;
import br.com.heiderlopes.voicez.model.People;

/**
 * Created by heiderlopes on 11/09/14.
 */
public interface PeopleDAO {

    public int save(People people) throws SQLException;

    public int update(People people) throws SQLException;

    public int delete(People people) throws SQLException;

    public List<People> findAll() throws SQLException;

    public People findById(int id) throws SQLException;

    public List<People> findAllInOrder(String columnName, boolean asc) throws SQLException;
}
