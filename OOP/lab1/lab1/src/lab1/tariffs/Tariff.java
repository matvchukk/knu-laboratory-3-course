package lab1.tariffs;

import java.util.List;

public class Tariff {
    protected final int fee;
    public final String name;
    protected final List<Characteristics> characteristicsList;
    private int numberOfClients;

    public Tariff(String name, int fee, List<Characteristics> characteristicsList, int numberOfClients) {
        this.name = name;
        this.fee = fee;
        this.characteristicsList = characteristicsList;
        this.numberOfClients = numberOfClients;
    }

    public String getName() {
        return this.name;
    }

    public int getFee() {
        return this.fee;
    }

    public int getNumberOfClients() {
        return this.numberOfClients;
    }

    public void setNumberOfClients(int numberOfClients) {
        this.numberOfClients = numberOfClients;
    }

    public List<Characteristics> getCharacteristicsList() {
        return this.characteristicsList;
    }
}
