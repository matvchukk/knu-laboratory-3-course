package users;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import tariffs.Tariff;
import tariffs.availableTariffs.BasicTariff;
import tariffs.availableTariffs.ProTariff;

import java.util.ArrayList;
import java.util.List;


public class ProviderTests {

    @Test
    void testAddNewTariff() {
        List<Tariff> tariffs = new ArrayList<>();
        List<Client> clients = new ArrayList<>();
        Provider providerKyivstar = new Provider("Kyivstar Provider", tariffs, clients);

        Tariff basicTariff = new BasicTariff("Basic", 90);
        providerKyivstar.addNewTariff(basicTariff);

        assertEquals(1, providerKyivstar.getNumberOfTariffs());
        assertEquals(basicTariff, providerKyivstar.existentTariffs.get(0));
    }

    @Test
    void testAddNewClient() {
        List<Tariff> tariffs = new ArrayList<>();
        List<Client> clients = new ArrayList<>();
        Provider providerKyivstar = new Provider("Kyivstar Provider", tariffs, clients);

        Tariff basicTariff = new BasicTariff("Basic", 90);
        Client client = new Client(basicTariff);

        providerKyivstar.addNewClient(client);

        assertEquals(1, providerKyivstar.getNumberOfClients());
    }

    @Test
    void testSortTariffsByFee() {
        List<Tariff> tariffs = new ArrayList<>();
        List<Client> clients = new ArrayList<>();
        Provider providerVodafone = new Provider("Test Provider", tariffs, clients);

        Tariff basicTariff = new BasicTariff("Basic", 70);
        Tariff proTariff = new ProTariff("Pro", 130);

        providerVodafone.addNewTariff(basicTariff);
        providerVodafone.addNewTariff(proTariff);

        List<Tariff> sortedTariffs = providerVodafone.sortTariffsByFee();

        assertEquals(basicTariff, sortedTariffs.get(0));
        assertEquals(proTariff, sortedTariffs.get(1));
    }
}
