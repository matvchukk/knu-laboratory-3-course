package org.example.socket.server;

import lombok.SneakyThrows;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServerSocketTask1 {

    private static final Integer PORT = 8080;
    private static final AtomicBoolean isStopped = new AtomicBoolean(false);

    @SneakyThrows
    public void start() {
        System.out.println("Server started!");

        try (ServerSocket server = new ServerSocket(PORT)) {
            server.setReuseAddress(true);

            while (!isStopped.get()) {
                Socket client = server.accept();

                Runnable clientRunnable = () -> {
                    RequestProcessor requestProcessor = new RequestProcessor(this, client);
                    while(!isStopped.get()){
                        requestProcessor.process();
                    }
                };

                new Thread(clientRunnable).start();
            }
        }

        System.out.println("Server stopped!");
    }

    public void stop() {
        isStopped.set(true);
    }

    public int getPort() {
        return PORT;
    }

}
