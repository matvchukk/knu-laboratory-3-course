package org.example.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Genre implements Serializable {
    public Integer id;
    public String name;
    public Genre(Integer id, String name){
        this.id = id;
        this.name = name;
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

    public static String printGenre(ArrayList<Genre> genres){
        StringBuilder result = new StringBuilder();
        for (Genre genre :genres){
            result.append(">>").append(genre.getId())
                    .append("-").append(genre.getName()).append("\n");
        }
        return result.toString();
    }
}
