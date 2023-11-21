package lab1.providers;

import lab1.clients.Client;
import lab1.tariffs.Tariff;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class Provider {
    private final String providerName;
    public final List<Tariff> existentTariffs;
    private final List<Client> clients;

    public Provider (String providerName, List<Tariff> existentTariffs, List<Client> clients) {
        this.providerName = providerName;
        this.clients = clients;
        this.existentTariffs = existentTariffs;
    }

    public void addNewClient(Client client) {
        clients.add(client);
    }

    public int getNumberOfClients() {
        return clients.size();
    }

    public List<Client> getClients() {
        return clients;
    }

    public void addNewTariff(Tariff tariff) {
        existentTariffs.add(tariff);
    }

    public int getNumberOfTariffs() {
        return existentTariffs.size();
    }

    public List<Tariff> getExistentTariffs() {
        return existentTariffs;
    }

    public List<Tariff> sortTariffsByFee() {
        List<Tariff> sortedTariffs = new ArrayList<>(existentTariffs);
        Collections.sort(sortedTariffs, (t1, t2) -> Integer.compare(t1.getFee(), t2.getFee()));
        return sortedTariffs;
    }

    public List<Tariff> findTariffsByName(String name) {
        return existentTariffs.stream()
                .filter(tariff -> tariff.getName().equals(name))
                .collect(Collectors.toList());
    }

    public List<Tariff> findTariffsByFeeRange(int minFee, int maxFee) {
        return existentTariffs.stream()
                .filter(tariff -> tariff.getFee() >= minFee && tariff.getFee() <= maxFee)
                .collect(Collectors.toList());
    }

    public List<Tariff> findTariffsByClientsRange(int minClients, int maxClients) {
        return existentTariffs.stream()
                .filter(tariff -> tariff.getNumberOfClients() >= minClients && tariff.getNumberOfClients() <= maxClients)
                .collect(Collectors.toList());
    }
}
