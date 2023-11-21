package lab1.tariffs.availableTariffs;

import lab1.tariffs.Tariff;
import lab1.tariffs.Characteristics;

import java.util.List;

public class ProTariff extends Tariff {
    public ProTariff(String name, int fee, int numberOfClients) {
        super(name, fee, List.of(Characteristics.UNLIMITED_CALLS, Characteristics.UNLIMITED_INTERNET, Characteristics.SMS, Characteristics.SUPER_POWER), numberOfClients);
    }
}
