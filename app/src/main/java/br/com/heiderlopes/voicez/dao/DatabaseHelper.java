package br.com.heiderlopes.voicez.dao;

import java.sql.SQLException;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import br.com.heiderlopes.voicez.model.People;
import br.com.heiderlopes.voicez.model.Phrase;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "voicez.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        Log.i(DatabaseHelper.class.getName(), "onCreate");
        CreateAllTables();
    }

    private void CreateAllTables()
    {
        try {
            TableUtils.createTableIfNotExists(connectionSource, People.class);
            TableUtils.createTableIfNotExists(connectionSource, Phrase.class);
            seedPhrase();
            seedPeople();
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    private void seedPeople() throws SQLException {
        getDao(People.class).create(new People(1, "Heider"));
        getDao(People.class).create(new People(2, "Cassiano"));
        getDao(People.class).create(new People(3, "Wellington"));
        getDao(People.class).create(new People(4, "Clauber"));
        getDao(People.class).create(new People(5, "Wilton"));
        getDao(People.class).create(new People(6, "Igor"));
        getDao(People.class).create(new People(7, "Alexandre"));
        getDao(People.class).create(new People(8, "Leandro"));


    }

    private void seedPhrase() throws SQLException {
        getDao(Phrase.class).create(new Phrase(1, " parabéns"));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            dropAllTables();
            dropAllTables();
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    private void dropAllTables() throws SQLException
    {
        TableUtils.dropTable(connectionSource, People.class,true);
    }

    @Override
    public void close() {
        super.close();
    }
}