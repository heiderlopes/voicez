package br.com.heiderlopes.voicez.dao.orm;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

import br.com.heiderlopes.voicez.dao.DatabaseHelper;
import br.com.heiderlopes.voicez.dao.PeopleDAO;
import br.com.heiderlopes.voicez.model.People;

/**
 * Created by heiderlopes on 11/09/14.
 */
public class PeopleDAOORM implements PeopleDAO {
    private Context context;
    private DatabaseHelper databaseHelper = null;
    private Dao<People, Integer> peopleDao = null;

    public PeopleDAOORM(Context context) {
        this.context = context;
    }

    private Dao<People, Integer> getPeopleORM() throws SQLException {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(context,
                    DatabaseHelper.class);
        }
        try {
            peopleDao = databaseHelper.getDao(People.class);
        } catch (SQLException sql) {

        }
        return peopleDao;
    }

    @Override
    public int save(People people) throws SQLException {
        Dao.CreateOrUpdateStatus status = getPeopleORM().createOrUpdate(people);
        return status.getNumLinesChanged();
    }

    @Override
    public int update(People people) throws SQLException {
        return getPeopleORM().update(people);
    }

    @Override
    public int delete(People people) throws SQLException {
        return getPeopleORM().delete(people);
    }

    @Override
    public List<People> findAll() throws SQLException {
        List<People> alPeople = null;
        QueryBuilder<People, Integer> qb = getPeopleORM().queryBuilder();
        alPeople = qb.query();
        return alPeople;
    }

    @Override
    public People findById(int id) throws SQLException {
        People people;
        people = getPeopleORM().queryForId(id);
        return people;
    }

    @Override
    public List<People> findAllInOrder(String columnName, boolean asc)
            throws SQLException {
        List<People> alPeople = null;
        QueryBuilder<People, Integer> qb = getPeopleORM().queryBuilder();
        qb.orderBy(columnName, asc);
        alPeople = qb.query();
        return alPeople;
    }
}