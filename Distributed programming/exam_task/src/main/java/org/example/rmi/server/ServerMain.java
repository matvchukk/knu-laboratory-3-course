package org.example.rmi.server;

import lombok.SneakyThrows;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerMain {

    @SneakyThrows
    public static void main(String[] args) {
        System.out.println("==> Server started");

        Registry registry = LocateRegistry.createRegistry(8080);
        InterfaceServerRmiTask1 service = new ServerRmiTask1();
        registry.rebind("server", service);
    }

}
