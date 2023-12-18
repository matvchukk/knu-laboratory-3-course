package org.example;

import java.sql.*;

public class VideoStore {
    private final Connection con;
    private final Statement statement;

    public VideoStore(String DBName, String ip, int port) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        String url = "jdbc:mysql://localhost:3306/video_store";
        con = DriverManager.getConnection(url, "root", "my_password07");
        statement = con.createStatement();
    }

    public void showGenres() {
        String sql = "SELECT ID_CO, NAME FROM GENRE";
        try {
            ResultSet rs = statement.executeQuery(sql);
            System.out.println("All genres in the database:");
            while (rs.next()) {
                int id = rs.getInt("ID_CO");
                String name = rs.getString("NAME");
                System.out.println(">>" + id + "-" + name);
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error!");
            System.out.println(" >> " + e.getMessage());
        }
    }

    public void showFilms() {
        String sql = "SELECT ID_F, NAME, FILMDIRECTOR, ISONTOP FROM FILM";
        try {
            ResultSet rs = statement.executeQuery(sql);
            System.out.println("All films in the database:");
            while (rs.next()) {
                int id = rs.getInt("ID_F");
                String name = rs.getString("NAME");
                String director = rs.getString("FILMDIRECTOR");
                Boolean isOnTop = rs.getBoolean("ISONTOP");
                String top;
                if (isOnTop) {
                    top = "is in the top 100 of its genre";
                } else {
                    top = "is not in the top 100 of its genre";
                }

                System.out.println(">>" + id + "-" + name + " by " + director + " " + top);
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(
                    "Error!");
            System.out.println(" >> " + e.getMessage());
        }
    }

    public void showFilmsInGenre(int id_genre) {
        String sql = "SELECT ID_F, NAME, FILMDIRECTOR, ISONTOP FROM FILM WHERE ID_CO = " + id_genre;
        try {
            ResultSet rs = statement.executeQuery(sql);
            System.out.println("All films in genre id=" + id_genre + " in the database:");
            while (rs.next()) {
                int id = rs.getInt("ID_F");
                String name = rs.getString("NAME");
                String director = rs.getString("FILMDIRECTOR");
                Boolean isOnTop = rs.getBoolean("ISONTOP");
                String top;
                if (isOnTop) {
                    top = "is in the top 100 of its genre";
                } else {
                    top = "is not in the top 100 of its genre";
                }

                System.out.println(">>" + id + "-" + name + " by " + director + " " + top);
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(
                    "Error!");
            System.out.println(" >> " + e.getMessage());
        }
    }

    public void stop() throws SQLException {
        con.close();
    }

    public boolean addGenre(int id, String name) {
        String sql = "INSERT INTO GENRE (ID_CO, NAME) " +
                "VALUES (" + id + ", '" + name + "')";
        try {
            statement.executeUpdate(sql);
            System.out.println("Genre " + name + " was added");
            return true;
        } catch (SQLException e) {
            System.out.println("Error! Genre " + name + " was not added");
            System.out.println(" >> " + e.getMessage());
            return false;
        }
    }

    public boolean updateGenre(int id, String name) {
        String sql = "UPDATE GENRE SET NAME = '" + name + "' WHERE ID_CO = " + id;
        try {
            statement.executeUpdate(sql);
            System.out.println("Genre " + name + " was updated");
            return true;
        } catch (SQLException e) {
            System.out.println("Error! Genre " + name + " was not updated");
            System.out.println(" >> " + e.getMessage());
            return false;
        }
    }

    public boolean updateFilm(int film_id, String name, int genre_id, String director) {
        String sql = "UPDATE FILM SET ID_CO =  " + genre_id + ", NAME = '" + name + "', FILMDIRECTOR = " + director + " WHERE ID_F = " + film_id;

        try {
            statement.executeUpdate(sql);
            System.out.println("Film " + name + " was updated");
            return true;
        } catch (SQLException e) {
            System.out.println("Error! Film " + name + " was not updated");
            System.out.println(" >> " + e.getMessage());
            return false;
        }
    }

    public boolean addFilm(int film_id, String name, int genre_id, String director) {

        String sql = "INSERT INTO FILM (ID_F, NAME, ID_CO, FILMDIRECTOR) " +
                "VALUES (" + film_id + ", '" + name + "' , '" + genre_id + "' , '" + director + "')";
        try {
            statement.executeUpdate(sql);
            System.out.println("Film " + name + " was added");
            return true;
        } catch (SQLException e) {
            System.out.println("Error! Film " + name + " was not added");
            System.out.println(" >> " + e.getMessage());
            return false;
        }
    }

    public boolean deleteFilm(int film_id) {
        String sql = "DELETE FROM FILM WHERE ID_F =" + film_id;
        try {
            int c = statement.executeUpdate(sql);
            if (c > 0) {
                System.out.println("Film id="
                        + film_id + " was deleted");
                return true;
            } else {
                System.out.println("Film id="
                        + film_id + " not found!");

                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error! Film id"
                    + film_id + " was not deleted");
            System.out.println(" >> " + e.getMessage());
            return false;
        }
    }


    public boolean deleteGenre(int genre_id) {
        String sql = "DELETE FROM GENRE WHERE ID_CO =" + genre_id;
        try {
            int c = statement.executeUpdate(sql);
            if (c > 0) {
                System.out.println("Genre id="
                        + genre_id + " was deleted");
                return true;
            } else {
                System.out.println("Genre id"
                        + genre_id + " was not found");

                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error! Genre id"
                    + genre_id + " was not deleted");
                    System.out.println(" >> " + e.getMessage());
            return false;
        }
    }

    public static void main(String[] args) throws Exception {
        VideoStore videoStoreData = new VideoStore("video_store", "localhost", 3306);

        //videoStoreData.addGenre(8, "Science fiction");
        //videoStoreData.addFilm(5, "Wednesday", 1, "Someone");
        videoStoreData.showGenres();
        videoStoreData.showFilms();
        videoStoreData.showFilmsInGenre(2);
        videoStoreData.deleteFilm(5);
        videoStoreData.deleteGenre(4);
        videoStoreData.deleteGenre(8);

        videoStoreData.stop();
    }
}
