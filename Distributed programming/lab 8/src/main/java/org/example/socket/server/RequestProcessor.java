package org.example.socket.server;

import java.io.StringWriter;
import java.sql.*;
import java.util.ArrayList;

import org.example.model.Film;
import org.example.model.Genre;
import org.example.db.VideoStoreDB;

public class RequestProcessor {

    private VideoStoreDB service;
    private StringWriter result;
    public RequestProcessor()
            throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        service = new VideoStoreDB("jdbc:mysql://localhost:3306/shop", "root", "06102003");
        result = new StringWriter();
    }

    public String showGenres() {
        ArrayList<Genre> genreArrayList = service.showGenres();
        result.append("All genres: #");
        for (Genre genre :genreArrayList){
            result.append(">>" + genre.getId() + "-" + genre.getName()).append("#");
        }
        return result.toString();
    }

    public String showFilmsInGenre(int genre_id) {
        ArrayList<Film> filmArrayList = service.showFilmsInGenre(genre_id);
        result.append("All films in genre id=" + genre_id + " :#");
        for (Film film :filmArrayList){
            result.append(">>" + film.getId() + "-" + film.getName() + "by" + film.getFilmDirector()).append("#");
            if (film.getTopStatus()) {
                result.append(" is in top 100 in this genre").append("#");
            } else {
                result.append(" is not in top 100 in this genre").append("#");
            }
        }
        return result.toString();
    }

    public String addGenre(int id, String name) {
        try {
            service.addGenre(id, name);
            result.append("Genre ").append(name).append(" successfully added#");
        } catch (Exception e) {
            result.append("Failed to add genre ").append(name).append("#");
        }
        return result.toString();
    }


    public String updateGenre(int id, String name) {
        try {
            service.updateGenre(id, name);
            result.append("Genre ").append(name).append(" successfully updated#");
        } catch (Exception e) {
            result.append("Failed to update genre ").append(name).append("#");
        }
        return result.toString();
    }

    public String updateFilm(int film_id, int genre_id, String name, Boolean isOnTop, String director) {
        try {
            service.updateFilm(film_id, genre_id, name, isOnTop, director);
            result.append("Film ").append(name).append(" successfully updated#");
        } catch (Exception e) {
            result.append("Failed to update film ").append(name).append("#");
        }
        return result.toString();
    }

    public String addFilm(int film_id, int genre_id, String name, Boolean isOnTop, String director) {
        try {
            service.addFilm(film_id, genre_id, name, isOnTop, director);
            result.append("Film ").append(name).append(" successfully added#");
        } catch (Exception e) {
            result.append("Failed to add film ").append(name).append("#");
        }
        return result.toString();
    }

    public String deleteGenre(int id) {
        try {
            service.deleteGenre(id);
            result.append("Genre ").append((char) id).append(" successfully deleted#");
        } catch (Exception e) {
            result.append("Failed to delete genre ").append((char) id).append("#");
        }
        return result.toString();
    }

    public String deleteFilm(int id) {
        try {
            service.deleteFilm(id);
            result.append("Film ").append((char) id).append(" successfully deleted#");
        } catch (Exception e) {
            result.append("Failed to delete film ").append((char) id).append("#");
        }
        return result.toString();
    }

    public String showFilmCountInGenre(int genre_id) {
        char productCount = (char) service.showFilmCountInGenre(genre_id);
        result.append("There films in genre: ").append((char) genre_id).append(": ").append(productCount).append("#");
        return result.toString();
    }

    public String getFilmByName(String name) {
        Film filmByName = service.getFilmByName(name);
        result.append("Film with name ").append(name);
        result.append("#Id: ").append(filmByName.getId().toString())
                .append("#Genre: id=").append(filmByName.getFilmGenreId().toString())
                .append("#Film director: ").append(filmByName.getFilmDirector()).append("#");
        if (filmByName.getTopStatus()) {
            result.append(" is in top 100 in this genre").append("#");
        } else {
            result.append(" is not in top 100 in this genre").append("#");
        }
        return result.toString();
    }
}
