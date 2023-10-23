import tariffs.Tariff;
import tariffs.Characteristics;
import users.Client;
import users.Provider;
import tariffs.availableTariffs.BasicTariff;
import tariffs.availableTariffs.ProTariff;
import tariffs.availableTariffs.StandardTariff;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Tariff> tariffs = new ArrayList<>();
        List<Client> clients = new ArrayList<>();

        Provider provider = new Provider("ProviderName", tariffs, clients);

        provider.addNewTariff(new Tariff("Tariff1", 100, Arrays.asList(Characteristics.CALLS, Characteristics.INTERNET)));
        provider.addNewTariff(new Tariff("Tariff2", 200, Arrays.asList(Characteristics.CALLS, Characteristics.SMS, Characteristics.INTERNET)));
        provider.addNewTariff(new BasicTariff("BasicTariff1", 150));
        provider.addNewTariff(new StandardTariff("StandardTariff1", 250));
        provider.addNewTariff(new ProTariff("ProTariff1", 350));
        provider.addNewTariff(new BasicTariff("BasicTariff2", 300));

        provider.addNewClient(new Client(provider.existentTariffs.get(0)));
        provider.addNewClient(new Client(provider.existentTariffs.get(1)));
        provider.addNewClient(new Client(provider.existentTariffs.get(2)));
        provider.addNewClient(new Client(provider.existentTariffs.get(3)));
        provider.addNewClient(new Client(provider.existentTariffs.get(4)));

        List<Tariff> foundTariffsByName = provider.findTariffsByName("Tariff1");
        List<Tariff> foundTariffsByFeeRange = provider.findTariffsByFeeRange(150, 250);
        List<Tariff> foundTariffsByNameAndFeeRange = provider.findTariffsByNameAndFeeRange("Tariff2", 150, 250);
        List<Tariff> foundTariffsByNameAndFeeRangeAndClientsRange = provider.findTariffsByNameAndFeeRangeAndClientsRange("Tariff2", 150, 250, 1, 10);
        List<Tariff> foundTariffsByClientsRange = provider.findTariffsByClientsRange(1, 10);
        List<Tariff> foundTariffsByNameAndClientsRange = provider.findTariffsByNameAndClientsRange("Tariff1", 1, 10);
        List<Tariff> foundTariffsByFeeRangeAndClientsRange = provider.findTariffsByFeeRangeAndClientsRange(150, 250, 1, 10);

        System.out.println("Tariffs found by name:");
        for (Tariff wantedTariff : foundTariffsByName) {
            wantedTariff.printTariffInfo();
        }

        System.out.println("Tariffs found by fee range:");
        for (Tariff wantedTariff : foundTariffsByFeeRange) {
            wantedTariff.printTariffInfo();
        }

        System.out.println("Tariffs found by name and fee range:");
        for (Tariff wantedTariff : foundTariffsByNameAndFeeRange) {
            wantedTariff.printTariffInfo();
        }

        System.out.println("Tariffs found by name, fee range and clients range:");
        for (Tariff wantedTariff : foundTariffsByNameAndFeeRangeAndClientsRange) {
            wantedTariff.printTariffInfo();
        }

        System.out.println("Tariffs found by clients range:");
        for (Tariff wantedTariff : foundTariffsByClientsRange) {
            wantedTariff.printTariffInfo();
        }

        System.out.println("Tariffs found by name and clients range:");
        for (Tariff wantedTariff : foundTariffsByNameAndClientsRange) {
            wantedTariff.printTariffInfo();
        }

        System.out.println("Tariffs found by fee range and clients range:");
        for (Tariff wantedTariff : foundTariffsByFeeRangeAndClientsRange) {
            wantedTariff.printTariffInfo();
        }
    }
}
