package org.example.rmi.client;

import lombok.SneakyThrows;
import org.example.utils.constants.OperationType;
import org.example.model.Publication;
import org.example.rmi.server.InterfaceServerRmiTask1;

import java.rmi.Naming;
import java.util.List;
import java.util.Scanner;

public class ClientRmiTask1 {

    @SneakyThrows
    public static void start(String[] args) {
        try(Scanner scanner = new Scanner(System.in)) {
            InterfaceServerRmiTask1 server = (InterfaceServerRmiTask1) Naming.lookup("//localhost:8080/server");
            boolean isStopped = false;

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

                List<Publication> publications = null;
                switch (commandNumber) {
                    case 1: {
                        System.out.println("Enter author: ");
                        String author = scanner.nextLine();

                        publications = server.findPublicationsByAuthor(author);
                        break;
                    }
                    case 2: {
                        System.out.println("Enter year: ");
                        int year = scanner.nextInt();

                        publications = server.findPublicationsAfterYear(year);
                        break;
                    }
                    case 3: {
                        System.out.println("Enter keywords: ");
                        String keywords = scanner.nextLine();

                        publications = server.findPublicationsByKeywords(keywords);
                        break;
                    }
                    case 4: {
                        System.out.println("Enter title (press Enter if not applicable): ");
                        String title = scanner.nextLine();

                        System.out.println("Enter author: ");
                        String authors = scanner.nextLine();

                        System.out.println("Enter year: ");
                        int year = scanner.nextInt();
                        scanner.nextLine();

                        System.out.println("Enter keywords: ");
                        String keywords = scanner.nextLine();

                        publications = server.findAndSortPublications(title, authors, year, keywords);
                        break;
                    }
                    case 5: {
                        System.out.println("==> ClientMain stopped");
                        isStopped = true;
                        break;
                    }
                }

                if(isStopped) break;

                System.out.println("Result: ");

                publications.forEach(System.out::println);
            }
        }
    }

}
