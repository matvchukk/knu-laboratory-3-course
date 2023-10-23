package users;

import tariffs.Tariff;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    public void printAllClientsInfo() {
        for (Client currClient : clients) {
            currClient.printClientInfo();
        }
    }

    public void addNewTariff(Tariff tariff) {
        existentTariffs.add(tariff);
    }

    public int getNumberOfTariffs() {
        return existentTariffs.size();
    }

    public void printAllTariffsInfo() {
        for (Tariff currTariff : existentTariffs) {
            currTariff.printTariffInfo();
        }
    }

    public List<Tariff> sortTariffsByFee() {
        List<Tariff> sortedTariffs = new ArrayList<>(existentTariffs);
        Collections.sort(sortedTariffs, (t1, t2) -> Integer.compare(t1.getFee(), t2.getFee()));
        return sortedTariffs;
    }

    public List<Tariff> findTariffsByName(String name) {
        List<Tariff> matchingTariffs = new ArrayList<>();
        for (Tariff tariff : existentTariffs) {
            if (tariff.getName().equals(name)) {
                matchingTariffs.add(tariff);
            }
        }
        return matchingTariffs;
    }

    public List<Tariff> findTariffsByFeeRange(int minFee, int maxFee) {
        List<Tariff> matchingTariffs = new ArrayList<>();
        for (Tariff tariff : existentTariffs) {
            int fee = tariff.getFee();
            if (fee >= minFee && fee <= maxFee) {
                matchingTariffs.add(tariff);
            }
        }
        return matchingTariffs;
    }

    public List<Tariff> findTariffsByNameAndFeeRange(String name, int minFee, int maxFee) {
        List<Tariff> matchingTariffs = new ArrayList<>();
        for (Tariff tariff : existentTariffs) {
            if (tariff.getName().equals(name)) {
                int fee = tariff.getFee();
                if (fee >= minFee && fee <= maxFee) {
                    matchingTariffs.add(tariff);
                }
            }
        }
        return matchingTariffs;
    }

    public List<Tariff> findTariffsByNameAndFeeRangeAndClientsRange(
            String name, int minFee, int maxFee, int minClients, int maxClients) {
        List<Tariff> matchingTariffs = new ArrayList<>();
        for (Tariff tariff : existentTariffs) {
            if (tariff.getName().equals(name)) {
                int fee = tariff.getFee();
                if (fee >= minFee && fee <= maxFee) {
                    int numClients = getNumberOfClientsForTariff(tariff);
                    if (numClients >= minClients && numClients <= maxClients) {
                        matchingTariffs.add(tariff);
                    }
                }
            }
        }
        return matchingTariffs;
    }

    private int getNumberOfClientsForTariff(Tariff tariff) {
        int count = 0;
        for (Client client : clients) {
            if (client.getTariff() == tariff) {
                count++;
            }
        }
        return count;
    }

    public List<Tariff> findTariffsByClientsRange(int minClients, int maxClients) {
        List<Tariff> matchingTariffs = new ArrayList<>();
        for (Tariff tariff : existentTariffs) {
            int numClients = getNumberOfClientsForTariff(tariff);
            if (numClients >= minClients && numClients <= maxClients) {
                matchingTariffs.add(tariff);
            }
        }
        return matchingTariffs;
    }

    public List<Tariff> findTariffsByNameAndClientsRange(String name, int minClients, int maxClients) {
        List<Tariff> matchingTariffs = new ArrayList<>();
        for (Tariff tariff : existentTariffs) {
            if (tariff.getName().equals(name)) {
                int numClients = getNumberOfClientsForTariff(tariff);
                if (numClients >= minClients && numClients <= maxClients) {
                    matchingTariffs.add(tariff);
                }
            }
        }
        return matchingTariffs;
    }

    public List<Tariff> findTariffsByFeeRangeAndClientsRange(
            int minFee, int maxFee, int minClients, int maxClients) {
        List<Tariff> matchingTariffs = new ArrayList<>();
        for (Tariff tariff : existentTariffs) {
            int fee = tariff.getFee();
            if (fee >= minFee && fee <= maxFee) {
                int numClients = getNumberOfClientsForTariff(tariff);
                if (numClients >= minClients && numClients <= maxClients) {
                    matchingTariffs.add(tariff);
                }
            }
        }
        return matchingTariffs;
    }
}
