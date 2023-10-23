package tariffs.availableTariffs;

import tariffs.Tariff;
import tariffs.Characteristics;
import java.util.List;
import java.util.stream.Collector;


public class ProTariff extends Tariff {
    public ProTariff (String name, int fee) {
        super(name, fee, List.of(Characteristics.UNLIMITED_CALLS, Characteristics.UNLIMITED_INTERNET, Characteristics.SMS, Characteristics.SUPER_POWER));
    }
}
