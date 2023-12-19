package org.example.rmi.client;


import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.util.ArrayList;


import org.example.model.Film;
import org.example.rmi.server.InterfaceVideoStoreServer;

import static org.example.model.Film.printFilm;
import static org.example.model.Genre.printGenre;


public class ClientMain {
    private static InterfaceVideoStoreServer storeServer;
    public static void main(String[] args) throws IOException, NotBoundException {
        String url = "//localhost:123/Video_store";
        storeServer = (InterfaceVideoStoreServer) Naming.lookup(url);
        System.out.println("Connected to the Video_store service.");
        System.out.println(showGenre());

        addGenre("1","Fantasy");
        System.out.println(showFilmsInGenre("1"));
    }

    public static void addGenre(String id, String name) throws IOException {
        storeServer.addGenre(Integer.parseInt(id), name);
    }

    public static void deleteGenre(String id) throws IOException {
        storeServer.deleteGenre(Integer.parseInt(id));
    }

    public static void updateGenre(String id, String name) throws IOException {
        storeServer.updateGenre(Integer.parseInt(id), name);
    }

    public void addFilm(String film_id, String genre_id, String name, String isOnTop, String director) throws IOException {
        storeServer.addFilm(Integer.parseInt(film_id), Integer.parseInt(genre_id), name, Boolean.parseBoolean(isOnTop), director);
    }

    public static void deleteFilm(String id) throws IOException {
        storeServer.deleteFilm(Integer.parseInt(id));
    }

    public static void updateFilm(String film_id, String genre_id, String name, String isOnTop, String director) throws IOException {
        storeServer.updateFilm(Integer.parseInt(film_id), Integer.parseInt(genre_id), name, Boolean.parseBoolean(isOnTop), director);
    }

    public static String showFilmCountInGenre(String id) throws IOException {
        StringBuilder result = new StringBuilder();
        result.append("We have " + storeServer.showFilmCountInGenre(Integer.parseInt(id)) + " films in this genre");
        return result.toString();
    }

    public static String getFilmByName(String name) throws IOException {
        StringBuilder result = new StringBuilder();
        result.append("FILM: \n");
        ArrayList<Film> filmArrayList = new ArrayList<Film>();
        filmArrayList.add(storeServer.getFilmByName(name));
        result.append(printFilm(filmArrayList));
        return result.toString();
    }

    public static String showFilmsInGenre(String id) throws IOException {
        StringBuilder result = new StringBuilder();
        result.append("Films in genre id=" + id + " :\n");
        result.append(printFilm(storeServer.showFilmsInGenre(Integer.parseInt(id))));
        return result.toString();
    }

    public static String showGenre() throws IOException {
        StringBuilder result = new StringBuilder();
        result.append("ALL SECTIONS:\n");
        result.append(printGenre(storeServer.showGenres()));
        return result.toString();
    }


}
