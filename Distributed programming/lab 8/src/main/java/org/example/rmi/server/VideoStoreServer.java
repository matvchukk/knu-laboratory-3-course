package org.example.rmi.server;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.rmi.server.UnicastRemoteObject;
import org.example.model.Film;
import org.example.model.Genre;
import org.example.db.VideoStoreDB;

public class VideoStoreServer extends UnicastRemoteObject implements InterfaceVideoStoreServer {
    private static VideoStoreDB service;

    public VideoStoreServer() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, RemoteException {
        service = new VideoStoreDB("jdbc:mysql://localhost:3306/video_store", "root", "06102003");

    }

    @Override
    public void addGenre(int id, String name) throws RemoteException {
        service.addGenre(id, name);
    }


    @Override
    public void deleteGenre(int genre_id) throws RemoteException{
        service.deleteGenre(genre_id);
    }


    @Override
    public void updateGenre(int id, String name) throws RemoteException{
        service.updateGenre(id, name);
    }
    @Override
    public void addFilm(int film_id, int genre_id, String name, Boolean isOnTop, String director) throws RemoteException{
        service.addFilm(film_id, genre_id, name, isOnTop, director);
    }
    @Override
    public void deleteFilm(int film_id) throws RemoteException{
        service.deleteFilm(film_id);
    }
    @Override
    public void updateFilm(int film_id, int genre_id, String name, Boolean isOnTop, String director) throws RemoteException{
        service.updateFilm(film_id, genre_id, name, isOnTop, director);
    }
    @Override
    public int showFilmCountInGenre(int genre_id) throws RemoteException {
        return service.showFilmCountInGenre(genre_id);
    }
    @Override
    public ArrayList<Film> showFilmsInGenre(int id_genre) throws RemoteException{
        return service.showFilmsInGenre(id_genre);
    }
    @Override
    public Film getFilmByName(String name) throws RemoteException{
        return service.getFilmByName(name);
    }
    @Override
    public ArrayList<Film> showFilms() throws RemoteException{
        return service.showFilms();
    }

    @Override
    public ArrayList<Genre> showGenres() throws RemoteException{
        return service.showGenres();
    }
}
