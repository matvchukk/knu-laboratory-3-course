package lab_c;

public class Main {
    public static boolean running = true;
    private static int size = 150;
    private static int teams = 3;
    public static void main(String[] args) {
        new Task(teams,size);
    }
    public static void stop() {
        running = false;
    }
}