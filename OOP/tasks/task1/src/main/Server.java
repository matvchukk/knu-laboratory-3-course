package main;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter output;
    private ObjectInputStream input;

    public void start(int port){
        try {
            serverSocket = new ServerSocket(port);
            while (true){
                clientSocket = serverSocket.accept();
                output = new PrintWriter(clientSocket.getOutputStream(), true);
                input = new ObjectInputStream(clientSocket.getInputStream());

                System.out.println("Client has been connected");

                try {
                    deserializing();
                } catch (SocketException e){
                    System.out.println("Socket reset");
                } catch (ClassNotFoundException e) {
                    System.out.println("Not found");
                } catch (EOFException e){
                    System.out.println("Serialized");
                }

                System.out.println("Client has been disconnected");
            }
        } catch (IOException e) {
            System.out.println("Something wrong with server");
            e.printStackTrace();
        }
    }

    public void stop() throws IOException {
        input.close();
        output.close();
        clientSocket.close();
    }

    private void deserializing() throws IOException, ClassNotFoundException {
        while (!clientSocket.isClosed()){
            Cat cat = (Cat) input.readObject();
            if(cat == null){
                System.out.println("Get null");
                output.println("Error, null object!");
            } else {
                System.out.println(cat);
                output.println("Deserialized");
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start(8888);
    }
}