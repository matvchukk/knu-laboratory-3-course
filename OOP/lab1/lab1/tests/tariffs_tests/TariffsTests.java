package tariffs_tests;

import lab1.tariffs.Characteristics;
import lab1.tariffs.Tariff;
import org.junit.jupiter.api.Test;
import lab1.tariffs.availableTariffs.BasicTariff;
import lab1.tariffs.availableTariffs.ProTariff;
import lab1.tariffs.availableTariffs.StandardTariff;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TariffsTests {
    @Test
    void testTariffGetName() {
        List<Characteristics> characteristicsList = List.of(Characteristics.CALLS, Characteristics.INTERNET);
        Tariff newTariff = new Tariff("Basic", 90, characteristicsList);

        assertEquals("Basic", newTariff.getName());
    }

    @Test
    void testTariffGetFee() {
        List<Characteristics> characteristicsList = List.of(Characteristics.CALLS, Characteristics.INTERNET);
        Tariff newTariff = new Tariff("Pro", 120, characteristicsList);

        assertEquals(120, newTariff.getFee());
    }

    @Test
    void testBasicTariffCharacteristics() {
        BasicTariff basicTariff = new BasicTariff("Basic", 90);

        assertTrue(basicTariff.characteristicsList.contains(Characteristics.CALLS));
        assertTrue(basicTariff.characteristicsList.contains(Characteristics.INTERNET));
        assertFalse(basicTariff.characteristicsList.contains(Characteristics.SMS));
        assertFalse(basicTariff.characteristicsList.contains(Characteristics.UNLIMITED_CALLS));
        assertFalse(basicTariff.characteristicsList.contains(Characteristics.UNLIMITED_INTERNET));
        assertFalse(basicTariff.characteristicsList.contains(Characteristics.SUPER_POWER));
    }

    @Test
    void testStandardTariffCharacteristics() {
        StandardTariff standardTariff = new StandardTariff("Pro", 120);

        assertTrue(standardTariff.characteristicsList.contains(Characteristics.CALLS));
        assertTrue(standardTariff.characteristicsList.contains(Characteristics.INTERNET));
        assertTrue(standardTariff.characteristicsList.contains(Characteristics.SMS));
        assertFalse(standardTariff.characteristicsList.contains(Characteristics.UNLIMITED_CALLS));
        assertFalse(standardTariff.characteristicsList.contains(Characteristics.UNLIMITED_INTERNET));
        assertTrue(standardTariff.characteristicsList.contains(Characteristics.SUPER_POWER));
    }

    @Test
    void testProTariffCharacteristics() {
        ProTariff proTariff = new ProTariff("Pro", 120);

        assertFalse(proTariff.characteristicsList.contains(Characteristics.CALLS));
        assertFalse(proTariff.characteristicsList.contains(Characteristics.INTERNET));
        assertTrue(proTariff.characteristicsList.contains(Characteristics.SMS));
        assertTrue(proTariff.characteristicsList.contains(Characteristics.UNLIMITED_CALLS));
        assertTrue(proTariff.characteristicsList.contains(Characteristics.UNLIMITED_INTERNET));
        assertTrue(proTariff.characteristicsList.contains(Characteristics.SUPER_POWER));
    }
}
