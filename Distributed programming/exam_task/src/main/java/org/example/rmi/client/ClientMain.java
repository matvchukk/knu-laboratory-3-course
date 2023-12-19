package org.example.rmi.client;

public class ClientMain {

    public static void main(String[] args) {
        Thread thread = new Thread(
                () -> ClientRmiTask1.start(args)
        );

        thread.start();
    }

}
