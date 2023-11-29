package main.constants;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Utils {
    public static String readMessage(AsynchronousSocketChannel channel) throws ExecutionException, InterruptedException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        Future<Integer> readResult = channel.read(buffer);
        readResult.get();
        String message = new String(buffer.array());
        buffer.clear();
        return message.chars().takeWhile(symbol -> symbol != 0).collect(StringBuilder::new,
                StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public static void writeMessage(AsynchronousSocketChannel channel, String message) throws ExecutionException, InterruptedException {
        ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
        channel.write(buffer).get();
        buffer.clear();
    }

    public static AsynchronousSocketChannel setupSocketChannel() throws IOException, ExecutionException, InterruptedException {
        AsynchronousSocketChannel socketChannel = AsynchronousSocketChannel.open();
        InetSocketAddress hostAddress = new InetSocketAddress(Constants.IP, Constants.PORT);
        socketChannel.connect(hostAddress).get();
        return socketChannel;
    }

    public static String constructFunctionClassName(String functionName) {
        return "clients.Process" + functionName.toUpperCase();
    }
}
