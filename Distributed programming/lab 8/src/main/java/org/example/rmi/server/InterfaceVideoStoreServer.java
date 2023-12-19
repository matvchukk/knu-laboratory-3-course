package org.example.rmi.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import org.example.model.Film;
import org.example.model.Genre;


public interface InterfaceVideoStoreServer extends Remote {
    public void addGenre(int id, String name) throws RemoteException;

    public void deleteGenre(int genre_id) throws RemoteException;

    public void updateGenre(int id, String name) throws RemoteException;

    public void addFilm(int film_id, int genre_id, String name, Boolean isOnTop, String director) throws RemoteException;
    public void deleteFilm(int film_id) throws RemoteException;

    public void updateFilm(int film_id, int genre_id, String name, Boolean isOnTop, String director) throws RemoteException;

    public int showFilmCountInGenre(int genre_id) throws RemoteException;

    public Film getFilmByName(String name) throws RemoteException;

    public ArrayList<Film> showFilms() throws RemoteException;
    public ArrayList<Film> showFilmsInGenre(int id_genre) throws RemoteException;

    public ArrayList<Genre> showGenres() throws RemoteException;

}
