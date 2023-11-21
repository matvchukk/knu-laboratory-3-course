package lab1.tariffs.availableTariffs;

import lab1.tariffs.Tariff;
import lab1.tariffs.Characteristics;

import java.util.List;

public class StandardTariff extends Tariff {
    public StandardTariff(String name, int fee, int numberOfClients) {
        super(name, fee, List.of(Characteristics.CALLS, Characteristics.INTERNET, Characteristics.SMS, Characteristics.SUPER_POWER), numberOfClients);
    }
}
