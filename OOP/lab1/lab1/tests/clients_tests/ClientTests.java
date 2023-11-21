package clients_tests;

//import org.junit.jupiter.api.Assertions;
import lab1.clients.Client;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import lab1.tariffs.Tariff;
import lab1.tariffs.availableTariffs.BasicTariff;
import lab1.tariffs.availableTariffs.ProTariff;

public class ClientTests {
    @Test
    void testSetTariff() {
        Tariff basicTariff = new BasicTariff("Basic", 10);
        Client client = new Client(basicTariff);

        Tariff proTariff = new ProTariff("Pro", 20);
        client.setTariff(proTariff);

        assertEquals(proTariff, client.getTariff());
    }

    @Test
    void testChangeTariff() {
        Tariff basicTariff = new BasicTariff("Basic", 10);
        Client client = new Client(basicTariff);

        Tariff proTariff = new ProTariff("Pro", 20);
        client.setTariff(proTariff);

        assertEquals(proTariff, client.getTariff());

        // Change to a different tariff
        Tariff newTariff = new BasicTariff("Basic Plus", 15);
        client.setTariff(newTariff);

        assertEquals(newTariff, client.getTariff());
    }

    @Test
    void testGetId() {
        Tariff basicTariff = new BasicTariff("Basic", 10);
        Client client = new Client(basicTariff);

        int id = client.getId();

        assertTrue(id >= 0 && id <= 100_000);
    }
}
