package org.example.rmi.server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;

public class ServerMain {
    public static void main(String[] args) throws RemoteException, InterruptedException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, AlreadyBoundException {
        final VideoStoreServer server = new VideoStoreServer();
        Registry registry = LocateRegistry.createRegistry(123);
        registry.rebind("Video_store", server);
        Thread.sleep(Integer.MAX_VALUE);
    }
}