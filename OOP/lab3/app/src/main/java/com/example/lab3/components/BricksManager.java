package com.example.lab3.components;

public class BricksManager {
    public int[][][] bricks;

    public BricksManager(int size) {
        bricks = new int[size][][];
        for (int i = 0; i < size; i++)
            bricks[i] = Generator.generate();
    }

    public int[][] get(int index) {
        return bricks[index];
    }

    public void remove(int index) {
        if (index >= 0 && index < bricks.length)
            bricks[index] = Generator.generate();
    }

    public void restart() {
        for (int i = 0; i < bricks.length; i++) {
            bricks[i] = Generator.generate();
        }
    }
}
