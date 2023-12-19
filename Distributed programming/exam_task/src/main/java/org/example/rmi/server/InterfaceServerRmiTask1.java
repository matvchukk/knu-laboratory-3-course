package org.example.rmi.server;

import org.example.model.Publication;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface InterfaceServerRmiTask1 extends Remote {

    List<Publication> findPublicationsByAuthor(String author) throws RemoteException;

    List<Publication> findPublicationsAfterYear(int year) throws RemoteException;

    List<Publication> findPublicationsByKeywords(String keywords) throws RemoteException;

    List<Publication> findAndSortPublications(String title, String authors, int year, String keywords) throws RemoteException;


}
