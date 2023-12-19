package org.example.socket.client;

import lombok.SneakyThrows;
import org.example.utils.constants.Attributes;
import org.example.utils.constants.OperationType;
import org.example.utils.Request;
import org.example.utils.Response;
import org.example.model.Publication;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ClientSocketTask1 {

    private static final Integer PORT = 8080;

    @SneakyThrows
    public void start() {
        String host = InetAddress.getLocalHost().getHostName();
        boolean isStopped = false;

        try(
                Socket socket = new Socket(host, PORT);
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                Scanner scanner = new Scanner(System.in)
        ) {
            while(true){
                System.out.println(
                        "Choose option: \n" +
                                "1 - " + OperationType.FIND_PUBLICATIONS_BY_AUTHOR + "\n" +
                                "2 - " + OperationType.FIND_PUBLICATIONS_AFTER_YEAR + "\n" +
                                "3 - " + OperationType.FIND_PUBLICATIONS_BY_KEYWORDS + "\n" +
                                "4 - " + OperationType.FIND_AND_SORT_PUBLICATIONS + "\n" +
                                "5 - " + OperationType.CLOSE
                );

                int commandNumber = scanner.nextInt();
                scanner.nextLine();

                Request request = null;
                Map<String, Object> attributes = new HashMap<>();
                switch (commandNumber) {
                    case 1: {
                        System.out.println("Enter author: ");
                        String author = scanner.nextLine();

                        attributes.put(Attributes.AUTHOR.name(), author);

                        request = new Request(OperationType.FIND_PUBLICATIONS_BY_AUTHOR, attributes);
                        break;
                    }
                    case 2: {
                        System.out.println("Enter year: ");
                        Integer year = scanner.nextInt();

                        attributes.put(Attributes.YEAR.name(), year);

                        request = new Request(OperationType.FIND_PUBLICATIONS_AFTER_YEAR, attributes);
                        break;
                    }
                    case 3: {
                        System.out.println("Enter keywords: ");
                        String keywords = scanner.nextLine();
                        attributes.put(Attributes.KEYWORDS.name(), keywords);
                        request = new Request(OperationType.FIND_PUBLICATIONS_BY_KEYWORDS, attributes);
                        break;
                    }
                    case 4: {
                        System.out.println("Enter title: ");
                        String title = scanner.nextLine();

                        System.out.println("Enter author: ");
                        String authors = scanner.nextLine();

                        System.out.println("Enter year: ");
                        Integer year = scanner.nextInt();
                        scanner.nextLine();

                        System.out.println("Enter keywords: ");
                        String keywords = scanner.nextLine();

                        attributes.put(Attributes.TITLE.name(), title);
                        attributes.put(Attributes.AUTHOR.name(), authors);
                        attributes.put(Attributes.YEAR.name(), year);
                        attributes.put(Attributes.KEYWORDS.name(), keywords);
                        request = new Request(OperationType.FIND_PUBLICATIONS_BY_KEYWORDS, attributes);
                        break;
                    }
                    case 5: {
                        System.out.println("Client stopped!");
                        request = new Request(OperationType.CLOSE, attributes);
                        isStopped = true;
                        break;
                    }
                }

                out.writeObject(request);

                if(isStopped) break;

                System.out.println("Required publications: ");
                Response response = (Response) in.readObject();

                List<Publication> publications = response.getPublications();
                publications.forEach(System.out::println);
            }
        }

    }

}
