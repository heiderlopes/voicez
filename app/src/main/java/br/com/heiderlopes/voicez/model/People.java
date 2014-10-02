package br.com.heiderlopes.voicez.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by heiderlopes on 11/09/14.
 */
@DatabaseTable(tableName = "people")
public class People {

    @DatabaseField(columnName = "id_nome", generatedId = true, allowGeneratedIdInsert=true)
    private int id;
    @DatabaseField(columnName = "name")
    private String name;


    public People() {

    }

    public People(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
