package main.clients.processF;

import main.constants.Utils;
import main.functions.IntOps;

import java.io.IOException;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class FProcess {

    private static final int maxAttempts = 30;
    private static int currentAttempt = 0;

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        System.out.println("Connected, starting F process...");

        AsynchronousSocketChannel socketChannel = Utils.setupSocketChannel();
        int argument = Integer.parseInt(Utils.readMessage(socketChannel));
        while (currentAttempt < maxAttempts) {
            Optional<Optional<Integer>> result = IntOps.trialF(argument);
            if(result.isPresent()) {
                Optional<Integer> answer = result.get();
                if(answer.isPresent()) {
                    Utils.writeMessage(socketChannel, "F" + "=" + answer.get());
                } else {
                    Utils.writeMessage(socketChannel, "STATUS: HARD FAIL " + "F");
                }
                return;
            }
            Utils.writeMessage(socketChannel, "STATUS: SOFT FAIL " + "F"
                    + ", attempt " + (currentAttempt + 1) + " of " + maxAttempts);
            ++currentAttempt;
            Thread.sleep(10);
        }
        Utils.writeMessage(socketChannel, "STATUS: HARD FAIL " + "F");
    }
}