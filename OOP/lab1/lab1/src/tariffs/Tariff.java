package tariffs;

import java.util.List;

public class Tariff {
    protected final int fee;
    public final String name;
    protected final List<Characteristics> characteristicsList;

    public Tariff(String name, int fee, List<Characteristics> characteristicsList) {
        this.name = name;
        this.fee = fee;
        this.characteristicsList = characteristicsList;
    }

    public String getName() {
        return this.name;
    }

    public int getFee() {
        return this.fee;
    }

    public void printTariffInfo() {
        System.out.println("--------------------");
        System.out.println("Tariff Name: " + this.name);
        System.out.println("Fee: " + this.fee);
        System.out.println("Characteristics:");

        for (Characteristics characteristic : this.characteristicsList) {
            System.out.println("- " + characteristic.getName());
        }
        System.out.println("--------------------");
    }
}
