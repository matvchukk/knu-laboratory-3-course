package task_a;

public class Main {

    public static void main(String[] args) {
        Forest myForestArea = new Forest();
        Bees myBeesGroups = new Bees(0, myForestArea);
        myBeesGroups.search();
    }
}