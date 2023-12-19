package org.example.socket.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private  final Socket sock;
    private static PrintWriter out;
    private static  BufferedReader in;

    public Client(String ip, int port) throws IOException {
        sock = new Socket(ip, port);
        in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        out = new PrintWriter(sock.getOutputStream(), true);
    }

    public static void main(String[] args) {
        try {
            Client client = new Client("localhost", 12345);
            showGenres();
            readResponse();


            addFilm("11","1","Some_title", "1", "Someone_director");
            readResponse();

            showFilmsInGenre("1");
            readResponse();

            stop();
            client.disconnect();
        } catch (IOException e) {
            System.out.println("CLIENT: Error");
            e.printStackTrace();
        }
    }

    private static String sendRequest(String... requests) {
        StringBuilder request = new StringBuilder();
        for (String arg : requests) {
            request.append(arg).append("#");
        }
        return request.toString();
    }

    private static void readResponse() throws IOException {
        String response = in.readLine();
        String[] fields = response.split("#");
        for (String field : fields) {
            System.out.println(field);
        }
        System.out.println();
    }

    public static void addGenre(String id, String name) throws IOException {
        out.println(sendRequest("1", id, name));
    }

    public static void deleteGenre(String id) throws IOException {
        out.println(sendRequest("2", id));
    }

    public static void updateGenre(String id, String name) throws IOException {
        out.println(sendRequest("3", id, name));
    }

    public static void addFilm(String film_id, String genre_id, String name, String isOnTop, String director) throws IOException {
        out.println(sendRequest("4", film_id, genre_id, name, isOnTop, director));
    }

    public static void deleteFilm(String id) throws IOException {
        out.println(sendRequest("5", id));
    }

    public static void updateFilm(String film_id, String genre_id, String name, String isOnTop, String director) throws IOException {
        out.println(sendRequest("6", film_id, genre_id, name, isOnTop, director));
    }

    public static void showFilmCountInGenre(String genre_id) throws IOException {
        out.println(sendRequest("7", genre_id));
    }

    public static void getFilmByName(String name) throws IOException {
        out.println(sendRequest("8",name));
    }

    public static void showFilmsInGenre(String id) throws IOException {
        out.println(sendRequest("9", id));
    }

    public static void showGenres() throws IOException {
        out.println(sendRequest("10"));
    }

    public static void stop() throws IOException {
        out.println(sendRequest("11"));
    }


    public void disconnect() throws IOException {
        sock.close();
    }
}