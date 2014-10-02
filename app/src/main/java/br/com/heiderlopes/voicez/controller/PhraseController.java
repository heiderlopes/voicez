package br.com.heiderlopes.voicez.controller;

import android.content.Context;

import java.sql.SQLException;
import java.util.List;

import br.com.heiderlopes.voicez.dao.PhraseDAO;
import br.com.heiderlopes.voicez.dao.orm.PhraseDAOORM;
import br.com.heiderlopes.voicez.model.Phrase;

/**
 * Created by heiderlopes on 11/09/14.
 */
public class PhraseController {

    private final PhraseDAO phraseDao;
    private final Context context;

    public PhraseController(Context context) {
        this.context = context;
        phraseDao = new PhraseDAOORM(context);
    }

    public List<Phrase> findAllInOrderByPhrase(boolean asc) {
        List<Phrase> alPhrase = null;
        try {
            alPhrase = phraseDao.findAllInOrder("phrase", asc);
        } catch (SQLException sqlException) {
            //logger.error(sqlException.getMessage());
        }
        return alPhrase;
    }

    public int save(Phrase phrase) throws SQLException{
        if (phrase.getPhrase().equals("")) {
            return 0;
        } else {
            return phraseDao.save(phrase);
        }
    }
}
