package main.server;

import main.constants.Utils;

import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;

public class ServerThread extends Thread {
    private final AsynchronousSocketChannel client;

    public ServerThread(String argument, AsynchronousSocketChannel client) throws ExecutionException, InterruptedException {
        this.client = client;
        Utils.writeMessage(client, argument);
    }

    @Override
    public void run() {
        boolean stopReceivingMessages = false;
        while (!stopReceivingMessages) {
            try {
                String message = Utils.readMessage(client);
                stopReceivingMessages = onMessageReceived(message);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    private boolean onMessageReceived(String message) {
        if(message == null) {
            return false;
        }
        if(message.contains("F" + "=") || message.contains("G" + "=")) {
            Manager.writeResult(message.substring(0, 1), Integer.parseInt(message.substring(2)));
            return true;
        }
        if(message.contains("STATUS: SOFT FAIL")) {
            System.out.println(message);
            return false;
        }
        if(message.contains("STATUS: HARD FAIL")) {
            Manager.handleHardFail(message.substring("STATUS: HARD FAIL".length()));
            return true;
        }
        return false;
    }
}
