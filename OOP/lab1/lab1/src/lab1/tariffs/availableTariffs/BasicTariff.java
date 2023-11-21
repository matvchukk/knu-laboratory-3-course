package lab1.tariffs.availableTariffs;

import lab1.tariffs.Tariff;
import lab1.tariffs.Characteristics;

import java.util.List;

public class BasicTariff extends Tariff {
    public BasicTariff(String name, int fee, int numberOfClients) {
        super(name, fee, List.of(Characteristics.CALLS, Characteristics.INTERNET), numberOfClients);
    }
}
