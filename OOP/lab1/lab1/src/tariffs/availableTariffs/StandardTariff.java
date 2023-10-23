package tariffs.availableTariffs;

import tariffs.Tariff;
import tariffs.Characteristics;
import java.util.List;
import java.util.stream.Collector;


public class StandardTariff extends Tariff {
    public StandardTariff (String name, int fee) {
        super(name, fee, List.of(Characteristics.CALLS, Characteristics.INTERNET, Characteristics.SMS, Characteristics.SUPER_POWER));
    }
}
