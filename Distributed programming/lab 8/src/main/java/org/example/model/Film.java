package org.example.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Film implements Serializable {
    public Integer id;
    public String name;
    public Boolean isInTop; //is the film in the top 100 of its genre?
    public Integer filmGenreId;
    public String filmDirector;
    public Film(Integer id, String name, Boolean isInTop, Integer filmGenreId, String filmDirector) {
        this.id = id;
        this.name = name;
        this.isInTop = isInTop;
        this.filmGenreId = filmGenreId;
        this.filmDirector = filmDirector;
    }

    public Film(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFilmGenreId() {
        return filmGenreId;
    }

    public void setFilmGenreId(Integer filmGenreId) {
        this.filmGenreId = filmGenreId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void name(String name) {
        this.name = name;
    }

    public Boolean getTopStatus() {
        return isInTop;
    }

    public void setTopStatus(Boolean isInTop) {
        this.isInTop = isInTop;
    }

    public String getFilmDirector() {
        return filmDirector;
    }

    public void setFilmDirector(String filmDirector) {this.filmDirector = filmDirector;}

    public static String printFilm(ArrayList<Film> films){
        StringBuilder result = new StringBuilder();
        for (Film film :films){
            result.append(">>").append(film.getId()).append("\n")
                    .append("Name: ").append(film.getName()).append("\n")
                    .append("Genre: ").append(film.getFilmGenreId()).append("\n")
                    .append("Director: ").append(film.getFilmDirector()).append("\n")
                    .append("Top: ").append(film.getTopStatus()).append("\n")
            ;
        }
        return result.toString();
    }
}
