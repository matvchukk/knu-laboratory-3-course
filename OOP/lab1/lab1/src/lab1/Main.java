package lab1;

import lab1.providers.Provider;
import lab1.tariffs.Tariff;
import lab1.tariffs.Characteristics;
import lab1.clients.Client;
import lab1.tariffs.availableTariffs.BasicTariff;
import lab1.tariffs.availableTariffs.ProTariff;
import lab1.tariffs.availableTariffs.StandardTariff;
import lab1.console.ConsoleOutput;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Tariff> tariffs = new ArrayList<>();
        List<Client> clients = new ArrayList<>();

        Provider provider = new Provider("ProviderName", tariffs, clients);

        provider.addNewTariff(new Tariff("Tariff1", 100, Arrays.asList(Characteristics.CALLS, Characteristics.INTERNET), 2344));
        provider.addNewTariff(new Tariff("Tariff2", 200, Arrays.asList(Characteristics.CALLS, Characteristics.SMS, Characteristics.INTERNET), 4673));
        provider.addNewTariff(new BasicTariff("BasicTariff1", 150, 3738));
        provider.addNewTariff(new StandardTariff("StandardTariff1", 250, 3299));
        provider.addNewTariff(new ProTariff("ProTariff1", 350, 2367));
        provider.addNewTariff(new BasicTariff("BasicTariff2", 300, 2381));

        provider.addNewClient(new Client(provider.existentTariffs.get(0)));
        provider.addNewClient(new Client(provider.existentTariffs.get(1)));
        provider.addNewClient(new Client(provider.existentTariffs.get(2)));
        provider.addNewClient(new Client(provider.existentTariffs.get(3)));
        provider.addNewClient(new Client(provider.existentTariffs.get(4)));

        Scanner scanner = new Scanner(System.in);
        boolean continueSearching = true;

        while (continueSearching) {
            System.out.println("Choose search criteria (1 - by name, 2 - by fee range, 3 - by clients range, 0 - exit):");
            int criteria = scanner.nextInt();

            switch (criteria) {
                case 1:
                    System.out.println("Enter the name of the tariff:");
                    String name = scanner.next();
                    List<Tariff> foundTariffsByName = provider.findTariffsByName(name);
                    ConsoleOutput.printFoundTariffs(foundTariffsByName);
                    break;
                case 2:
                    System.out.println("Enter the minimum fee:");
                    int minFee = scanner.nextInt();
                    System.out.println("Enter the maximum fee:");
                    int maxFee = scanner.nextInt();
                    List<Tariff> foundTariffsByFeeRange = provider.findTariffsByFeeRange(minFee, maxFee);
                    ConsoleOutput.printFoundTariffs(foundTariffsByFeeRange);
                    break;
                case 3:
                    System.out.println("Enter the minimum number of clients:");
                    int minClients = scanner.nextInt();
                    System.out.println("Enter the maximum number of clients:");
                    int maxClients = scanner.nextInt();
                    List<Tariff> foundTariffsByClientsRange = provider.findTariffsByClientsRange(minClients, maxClients);
                    ConsoleOutput.printFoundTariffs(foundTariffsByClientsRange);
                    break;
                case 0:
                    continueSearching = false;
                    break;
                default:
                    System.out.println("Invalid criteria.");
                    break;
            }
        }

        scanner.close();
    }
}
