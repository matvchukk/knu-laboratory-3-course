package main.server;

import main.constants.Constants;
import main.constants.Utils;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Manager {
    private static AsynchronousServerSocketChannel server;
    private static final List<Process> processes = new LinkedList<>();
    private static final List<ServerThread> servers = new LinkedList<>();
    private static final Map<String, Integer> results = new HashMap<>();

    public static void start(String argument) throws IOException, ExecutionException, InterruptedException, NativeHookException {
        System.out.println("Starting Server!");
        initNativeKeyListener();

        server = AsynchronousServerSocketChannel.open();
        server.bind(new InetSocketAddress(Constants.IP, Constants.PORT));
        runProcess("F", argument);
        runProcess("G", argument);
        System.out.println("Succesfull!");
    }

    private static double getMax(double resultF, double resultG){
        return Math.max(resultF, resultG);
    }

    static void writeResult(String functionName, int result) {
        synchronized (results) {
            results.put(functionName, result);
            if (results.size() == processes.size()) {
                int firstResult = results.get("F");
                int secondResult = results.get("G");
                System.out.println("The result of execution of process F: " + firstResult);
                System.out.println("The result of execution of process G: " + secondResult);
                System.out.println("Maximum: " + getMax(firstResult, secondResult));

                finish();
            }
        }
    }

    static void handleHardFail(String functionName) {
        synchronized (results) {
            System.out.println("STATUS: HARD FAIL occurred in function " + "Function" + functionName + "interrupts");
            finish();
        }
    }

    private static void initNativeKeyListener() throws NativeHookException {
        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeKeyListener(new NativeKeyListener() {
            @Override
            public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
            }

            @Override
            public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
                if(nativeKeyEvent.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
                    Manager.cancel();
                }
            }

            @Override
            public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
            }
        });
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        logger.setUseParentHandlers(false);
        System.out.println("In order to stop the program, press esc");
    }

    private static void cancel() {
        synchronized (results) {
            System.out.println("Cancellation");
            finish();
        }
    }

    private static void runProcess(String functionName, String argument) throws IOException, ExecutionException, InterruptedException {
        ProcessBuilder processBuilder =
                new ProcessBuilder("java", "-cp",
                        "/Users/Анастасія/Desktop/Роботи 3 курс/ОС/lab1/lab1/src/main/resources/lab1.jar",
                        Utils.constructFunctionClassName(functionName))
                        .directory(new File("/Users/Анастасія/Desktop/Роботи 3 курс/ОС/lab1/lab1/target/classes"));
        processes.add(processBuilder.start());
        AsynchronousSocketChannel client = server.accept().get();
        ServerThread serverThread = new ServerThread(argument, client);
        serverThread.start();
        servers.add(serverThread);
    }

    private static void finish() {
        processes.forEach(Process::destroy);
        servers.forEach(Thread::interrupt);
        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException ignored) {}
        System.exit(0);
    }
}
