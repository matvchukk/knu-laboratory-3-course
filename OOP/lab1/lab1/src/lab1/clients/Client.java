package lab1.clients;

import lab1.tariffs.Tariff;

public class Client {
    private final int personalId = (int)(Math.random() * 100_000);;
    private Tariff personalTariff;

    public Client(Tariff personalTariff) {
        this.personalTariff = personalTariff;
    }
    public int getId() {
        return personalId;
    }

    public void setTariff(Tariff newTariff) {
        this.personalTariff = newTariff;
    }

    public Tariff getTariff() {
        return this.personalTariff;
    }
}
