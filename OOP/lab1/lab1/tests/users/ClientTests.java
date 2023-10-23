package users;

//import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import tariffs.Tariff;
import tariffs.availableTariffs.BasicTariff;
import tariffs.availableTariffs.ProTariff;

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
