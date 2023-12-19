package org.example.db;

import org.example.model.Film;
import org.example.model.Genre;

import java.sql.*;
import java.util.ArrayList;

public class VideoStoreDB {
    private final Connection con;
    private final Statement statement;

    private final StringBuilder result;

    public VideoStoreDB(String DBName, String ip, String port) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        String url = "jdbc:mysql://localhost:3306/video_store";
        result = new StringBuilder();
        con = DriverManager.getConnection(url, "root", "my_password07");
        statement = con.createStatement();
    }

    public ArrayList<Genre> showGenres() {
        String sql = "SELECT * FROM GENRE";
        try {
            ResultSet rs = statement.executeQuery(sql);
            ArrayList<Genre> genreArrayList = new ArrayList<Genre>();
            while (rs.next()) {
                genreArrayList.add(new Genre(rs.getInt("ID_CO"), rs.getString("NAME")));
            }
            rs.close();
            return genreArrayList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Film> showFilms() {
        String sql = "SELECT * FROM FILM";
        try {
            ResultSet rs = statement.executeQuery(sql);
            ArrayList<Film> filmArrayList = new ArrayList<Film>();
            while (rs.next()) {
                filmArrayList.add(new Film(rs.getInt("ID_F"), rs.getString("NAME"),
                        rs.getBoolean("ISONTOP"),
                        rs.getInt("ID_CO"), rs.getString("FILMDIRECTOR")));
            }
            rs.close();
            return filmArrayList;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Film> showFilmsInGenre(int id_genre) {
        String sql = "SELECT ID_F, NAME, FILMDIRECTOR, ISONTOP FROM FILM WHERE ID_CO = " + id_genre;
        try {
            ResultSet rs = statement.executeQuery(sql);
            ArrayList<Film> filmArrayList = new ArrayList<Film>();
            while (rs.next()) {
                filmArrayList.add(new Film(rs.getInt("ID_F"), rs.getString("NAME"),
                        rs.getBoolean("ISONTOP"),
                        id_genre, rs.getString("FILMDIRECTOR")));
            }
            rs.close();
            return filmArrayList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() throws SQLException {
        con.close();
    }

    public void addGenre(int id, String name) {
        String sql = "INSERT INTO GENRE (ID_CO, NAME) " +
                "VALUES (" + id + ", '" + name + "')";
        try {
            statement.executeUpdate(sql);
//            System.out.println("Genre " + name + " was added");
//            return true;
        } catch (SQLException e) {
//            System.out.println("Error! Genre " + name + " was not added");
            throw new RuntimeException(e);
        }
    }

    public void updateGenre(int id, String name) {
        String sql = "UPDATE GENRE SET NAME = '" + name + "' WHERE ID_CO = " + id;
        try {
            statement.executeUpdate(sql);
//            System.out.println("Genre " + name + " was updated");
        } catch (SQLException e) {
//            System.out.println("Error! Genre " + name + " was not updated");
            throw new RuntimeException(e);
        }
    }

    public void updateFilm(int film_id, int genre_id, String name, Boolean isOnTop, String director) {
        String sql = "UPDATE FILM SET ID_CO =  " + genre_id + ", NAME = '" + name + "', FILMDIRECTOR = " + director + ", ISONTOP = " + isOnTop +" WHERE ID_F = " + film_id;

        try {
            statement.executeUpdate(sql);
            //System.out.println("Film " + name + " was updated");
        } catch (SQLException e) {
            //System.out.println("Error! Film " + name + " was not updated");
            throw new RuntimeException(e);
        }
    }

    public void addFilm(int film_id, int genre_id, String name, Boolean isOnTop, String director) {

        String sql = "INSERT INTO FILM (ID_F, ID_CO, NAME, ISONTOP, FILMDIRECTOR) " +
                "VALUES (" + film_id + ", '" + genre_id + "' , '" + name + "' , '" + isOnTop + "' , '" + director + "')";
        try {
            statement.executeUpdate(sql);
            //System.out.println("Film " + name + " was added");
        } catch (SQLException e) {
            //System.out.println("Error! Film " + name + " was not added");
            throw new RuntimeException(e);
        }
    }

    public void deleteFilm(int film_id) {
        String sql = "DELETE FROM FILM WHERE ID_F =" + film_id;
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void deleteGenre(int genre_id) {
        String sql = "DELETE FROM GENRE WHERE ID_CO =" + genre_id;
        try {
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Film getFilmByName(String name) {
        String sql = "SELECT * FROM FILM WHERE NAME = '" + name +"'";
        try {
            ResultSet rs = statement.executeQuery(sql);
            rs.next();
            Film film = new Film(rs.getInt("ID_F"), rs.getString("NAME"),
                    rs.getBoolean("ISONTOP"),
                    rs.getInt("ID_CO"), rs.getString("FILMDIRECTOR"));
            rs.close();
            return film;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int showFilmCountInGenre(int genre_id) {
        String sql = "SELECT COUNT(*) AS FILM_COUNT FROM FILM WHERE ID_CO = " + genre_id;
        try {
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt("FILM_COUNT");
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }


}
