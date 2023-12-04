package main;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private Socket clientSocket;
    private ObjectOutputStream output;
    private BufferedReader input;

    public void connect(String host, int port) throws IOException {
        clientSocket = new Socket(host,port);
        output = new ObjectOutputStream(clientSocket.getOutputStream());
        input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public void stop() throws IOException {
        input.close();
        output.close();
        clientSocket.close();
    }

    public String sendObject(Cat cat) throws IOException {
        output.writeObject(cat);
        output.flush();
        return input.readLine();
    }

    public static void main(String[] args) {
        try {
            Client client = new Client();
            client.connect("localhost", 8888);

            Cat cat = new Cat("Kit",2);

            String response = client.sendObject(cat);
            System.out.println(response);

            client.stop();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}