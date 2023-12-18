package xml.model;

import xml.model.Film;

import java.util.ArrayList;

public class Genre {
    public Integer id;
    public String name;
    ArrayList<Film> filmArrayList;
    public Genre(Integer id, String name){
        this.id = id;
        this.name = name;
        //filmArrayList = new ArrayList<>();
    }

    public Genre(){}
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
