package org.example.socket.server;

import lombok.SneakyThrows;
import org.example.model.PublicationLibrary;
import org.example.utils.*;
import org.example.utils.constants.Attributes;
import org.example.utils.constants.OperationType;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;

public class RequestProcessor {

    private final ServerSocketTask1 server;
    private final Socket client;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;


    @SneakyThrows
    public RequestProcessor(ServerSocketTask1 server, Socket client) {
        this.server = server;
        this.client = client;
        this.in = new ObjectInputStream(client.getInputStream());
        this.out = new ObjectOutputStream(client.getOutputStream());
    }


    @SneakyThrows
    public void process() {
        Request request = receiveRequest();
        OperationType operationType = request.getOperationType();

        System.out.println(client.getInetAddress().getHostAddress() + ": " + operationType.name());

        Response response = null;
        Map<String, Object> attributes = request.getAttributes();

        switch (operationType) {
            case FIND_PUBLICATIONS_BY_AUTHOR: {
                String author = (String) attributes.get(Attributes.AUTHOR.name());
                response = new Response(PublicationLibrary.findPublicationsByAuthor(author));
                break;
            }
            case FIND_PUBLICATIONS_AFTER_YEAR: {
                Integer year = (Integer) attributes.get(Attributes.YEAR.name());
                response = new Response(PublicationLibrary.findPublicationsAfterYear(year));
                break;
            }
            case FIND_PUBLICATIONS_BY_KEYWORDS: {
                String keywords = (String) attributes.get(Attributes.KEYWORDS.name());
                response = new Response(PublicationLibrary.findPublicationsByKeywords(keywords));
                break;
            }
            case FIND_AND_SORT_PUBLICATIONS: {
                String title = (String) attributes.get(Attributes.TITLE.name());
                String author = (String) attributes.get(Attributes.AUTHOR.name());
                Integer year = (Integer) attributes.get(Attributes.YEAR.name());
                String keywords = (String) attributes.get(Attributes.KEYWORDS.name());
                response = new Response(PublicationLibrary.findAndSortPublications(title, author, year, keywords));
            }
            case CLOSE: {
                server.stop();
                break;
            }
        }

        sendResponse(response);
    }

    @SneakyThrows
    private Request receiveRequest() {
        return (Request) in.readObject();
    }

    @SneakyThrows
    private void sendResponse(Response response) {
        out.writeObject(response);
    }

}
