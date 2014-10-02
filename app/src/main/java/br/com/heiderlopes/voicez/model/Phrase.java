package br.com.heiderlopes.voicez.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by heiderlopes on 11/09/14.
 */
@DatabaseTable(tableName = "phrase")
public class Phrase {

    @DatabaseField(columnName = "id_phrase", generatedId = true, allowGeneratedIdInsert=true)
    private int id;
    @DatabaseField(columnName = "phrase")
    private String phrase;


    public Phrase() {}

    public Phrase(int id, String phrase) {
        this.id = id;
        this.phrase = phrase;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public String toString() {
        return phrase;
    }
}
