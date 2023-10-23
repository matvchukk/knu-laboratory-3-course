package tariffs.availableTariffs;

import tariffs.Tariff;
import tariffs.Characteristics;
import java.util.List;
import java.util.stream.Collector;


public class BasicTariff extends Tariff {
    public BasicTariff (String name, int fee) {
        super(name, fee, List.of(Characteristics.CALLS, Characteristics.INTERNET));
    }
}
