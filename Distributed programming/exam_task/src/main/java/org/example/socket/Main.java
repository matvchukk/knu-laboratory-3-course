package org.example.socket;

import org.example.socket.client.ClientMain;
import org.example.socket.server.ServerMain;

public class Main {

    public static void main(String[] args) {
        Thread serverThread = new Thread(
                () -> ServerMain.main(args)
        );

        Thread clientThread = new Thread(
                () -> ClientMain.main(args)
        );


        serverThread.start();
        clientThread.start();
    }

}
