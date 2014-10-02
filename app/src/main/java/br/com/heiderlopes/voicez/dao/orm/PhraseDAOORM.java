package br.com.heiderlopes.voicez.dao.orm;

import android.content.Context;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import java.sql.SQLException;
import java.util.List;
import br.com.heiderlopes.voicez.dao.DatabaseHelper;
import br.com.heiderlopes.voicez.dao.PhraseDAO;
import br.com.heiderlopes.voicez.model.Phrase;

/**
 * Created by heiderlopes on 11/09/14.
 */
public class PhraseDAOORM implements PhraseDAO {
    private Context context;
    private DatabaseHelper databaseHelper = null;
    private Dao<Phrase, Integer> phraseDao = null;

    public PhraseDAOORM(Context context) {
        this.context = context;
    }

    private Dao<Phrase, Integer> getPhraseORM() throws SQLException {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(context,
                    DatabaseHelper.class);
        }
        try {
            phraseDao = databaseHelper.getDao(Phrase.class);
        } catch (SQLException sql) {

        }
        return phraseDao;
    }

    @Override
    public int save(Phrase phrase) throws SQLException {
        Dao.CreateOrUpdateStatus status = getPhraseORM().createOrUpdate(phrase);
        return status.getNumLinesChanged();
    }

    @Override
    public int update(Phrase phrase) throws SQLException {
        return getPhraseORM().update(phrase);
    }

    @Override
    public int delete(Phrase phrase) throws SQLException {
        return getPhraseORM().delete(phrase);
    }

    @Override
    public List<Phrase> findAll() throws SQLException {
        List<Phrase> alPhrase = null;
        QueryBuilder<Phrase, Integer> qb = getPhraseORM().queryBuilder();
        alPhrase = qb.query();
        return alPhrase;
    }

    @Override
    public Phrase findById(int id) throws SQLException {
        Phrase phrase = getPhraseORM().queryForId(id);
        return phrase;
    }

    @Override
    public List<Phrase> findAllInOrder(String columnName, boolean asc)
            throws SQLException {
        List<Phrase> alPhrase = null;
        QueryBuilder<Phrase, Integer> qb = getPhraseORM().queryBuilder();
        qb.orderBy(columnName, asc);
        alPhrase = qb.query();
        return alPhrase;
    }
}