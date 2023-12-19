package org.example.rmi.server;

import org.example.model.PublicationLibrary;
import org.example.model.Publication;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ServerRmiTask1 extends UnicastRemoteObject implements InterfaceServerRmiTask1 {


    protected ServerRmiTask1() throws RemoteException {
    }

    @Override
    public List<Publication> findPublicationsByAuthor(String author) throws RemoteException {
        return PublicationLibrary.findPublicationsByAuthor(author);
    }

    @Override
    public List<Publication> findPublicationsAfterYear(int year) throws RemoteException {
        return PublicationLibrary.findPublicationsAfterYear(year);
    }

    @Override
    public List<Publication> findPublicationsByKeywords(String keywords) throws RemoteException {
        return PublicationLibrary.findPublicationsByKeywords(keywords);
    }
    @Override
    public List<Publication> findAndSortPublications(String title, String authors, int year, String keywords) throws RemoteException {
        return PublicationLibrary.findAndSortPublications(title, authors, year, keywords);
    }


}
