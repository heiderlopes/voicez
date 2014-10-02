package br.com.heiderlopes.voicez.controller;

import android.content.Context;

import java.sql.SQLException;
import java.util.List;

import br.com.heiderlopes.voicez.dao.PeopleDAO;
import br.com.heiderlopes.voicez.dao.orm.PeopleDAOORM;
import br.com.heiderlopes.voicez.model.People;

/**
 * Created by heiderlopes on 11/09/14.
 */
public class PeopleController {

    private final PeopleDAO peopleDao;
    private final Context context;

    public PeopleController(Context context) {
        this.context = context;
        peopleDao = new PeopleDAOORM(context);
    }

    public List<People> findAllInOrderByNome(boolean asc) {
        List<People> alPeople = null;
        try {
            alPeople = peopleDao.findAllInOrder("name", asc);
        } catch (SQLException sqlException) {
            //logger.error(sqlException.getMessage());
        }
        return alPeople;
    }

    public int save(People people) throws SQLException{
        if (people.getName().equals("")) {
            return 0;
        } else {
            return peopleDao.save(people);
        }
    }

    public int delete(People people) throws SQLException{
        return peopleDao.delete(people);
    }
}
