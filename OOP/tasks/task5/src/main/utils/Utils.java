package main.utils;
import java.util.Random;

public class Utils {
    private static final Random levelRandom = new Random(0);

    public static int getRandomLevel(int maxLevel){
        int newLevel = 0;
        while (newLevel < maxLevel - 1 && levelRandom.nextFloat() < 0.5f) {
            newLevel += 1;
        }
        return newLevel;
    }
}