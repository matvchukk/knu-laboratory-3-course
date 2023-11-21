package lab1.console;

import lab1.tariffs.Tariff;
import lab1.clients.Client;
import lab1.tariffs.Characteristics;
import java.util.List;

public class ConsoleOutput {
    public static void printFoundTariffs(List<Tariff> foundTariffs) {
        if (foundTariffs.isEmpty()) {
            System.out.println("Tariff not found.");
        } else {
            for (Tariff wantedTariff : foundTariffs) {
                printTariffInfo(wantedTariff);
            }
        }
    }

    public static void printTariffInfo(Tariff tariff) {
        System.out.println("--------------------");
        System.out.println("Tariff Name: " + tariff.getName());
        System.out.println("Fee: " + tariff.getFee());
        System.out.println("Number of Clients: " + tariff.getNumberOfClients());
        System.out.println("Characteristics:");

        for (Characteristics characteristic : tariff.getCharacteristicsList()) {
            System.out.println("- " + characteristic.getName());
        }
        System.out.println("--------------------");
    }

    public static void printAllTariffsInfo(List<Tariff> tariffs) {
        for (Tariff currTariff : tariffs) {
            printTariffInfo(currTariff);
        }
    }

    public static void printClientInfo(Client client) {
        System.out.println("Client: " + client.getId() + " Active tariff: " + client.getTariff().getName());
    }

    public static void printAllClientsInfo(List<Client> clients) {
        for (Client currClient : clients) {
            printClientInfo(currClient);
        }
    }
}
