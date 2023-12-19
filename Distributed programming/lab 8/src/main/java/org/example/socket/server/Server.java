package org.example.socket.server;

//import Database.DatabaseService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class Server {
    private static ServerSocket server = null;
    private static Socket sock = null;
    private static PrintWriter out = null;
    private static BufferedReader in = null;

    public static void main(String[] args) {
        try {
            Server srv = new Server();
            srv.start(12345);
        } catch (IOException | SQLException | ClassNotFoundException | InstantiationException |
                 IllegalAccessException e) {
            System.out.println("Error");
        }
    }

    public void start(int port) throws IOException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        server = new ServerSocket(port);
        while (true) {
            sock = server.accept();
            in = new BufferedReader(new
                    InputStreamReader(sock.getInputStream()));
            out = new PrintWriter(sock.getOutputStream(), true);
            while (processQuery()) ;
        }
    }

    public static boolean processQuery() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        RequestProcessor service = new RequestProcessor();
        try {
            String query = in.readLine();
            String[] parameters = query.split("#");
            String command = parameters[0];
            String response = "";
            switch (command) {
                case "1" -> response = service.addGenre(Integer.parseInt(parameters[1]), parameters[2]);
                case "2" -> response = service.deleteGenre(Integer.parseInt(parameters[1]));
                case "3" -> response = service.updateGenre(Integer.parseInt(parameters[1]), parameters[2]);
                case "4" -> response = service.addFilm(Integer.parseInt(parameters[1]), Integer.parseInt(parameters[2]), parameters[3], Boolean.parseBoolean(parameters[4]), parameters[5]);
                case "5" -> response = service.deleteFilm(Integer.parseInt(parameters[1]));
                case "6" -> response = service.updateFilm(Integer.parseInt(parameters[1]), Integer.parseInt(parameters[2]), parameters[3], Boolean.parseBoolean(parameters[4]), parameters[5]);
                case "7" -> response = service.showFilmCountInGenre(Integer.parseInt(parameters[1]));
                case "8" -> response = service.getFilmByName(parameters[1]);
                case "9" -> response = service.showFilmsInGenre(Integer.parseInt(parameters[1]));
                case "10" -> response = service.showGenres();
                case "11" -> {
                    sock.close();
                    in.close();
                    out.close();
                    return false;
                }
            }
            out.println(response);
            out.flush();
            return true;
        } catch (Exception e) {
            System.out.println("SERVER: Error: " + e.getMessage());
            return false;
        }
    }
}