package tariffs;

public enum Characteristics {
    CALLS("calls"),
    INTERNET("internet"),
    SMS("sms"),
    UNLIMITED_CALLS("unlimited calls"),
    UNLIMITED_INTERNET("unlimited internet"),
    SUPER_POWER("super power - additional internet or calls or transferring money to charity");

    private final String name;

    Characteristics(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
