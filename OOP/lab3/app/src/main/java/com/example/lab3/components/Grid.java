package com.example.lab3.components;

import java.util.Random;

public class Grid {
    private static final Random rand = new Random(System.currentTimeMillis());
    private int size;
    public int[][] grid;
    public int score = 0;

    public Grid(int size) {
        this.size = size;
        restart();
    }

    private void step() {
        for (int i = 0; i < size; i++) {
            boolean v = true, h = true;
            for (int j = 0; j < size; j++) {
                if (grid[i][j] != 1) v = false;
                if (grid[j][i] != 1) h = false;
            }
            if (v)  {
                for (int j = 0; j < size; j++) grid[i][j] = 0;
                score += size;
            }
            if (h) {
                for (int j = 0; j < size; j++) grid[j][i] = 0;
                score += size;
            }
        }
    }

    private boolean in(int v) {
        return v >= 0 && v < size;
    }

    public boolean put(int[][] brick, int x, int y) {
        System.out.println(x + " " + y);
        int dx = brick.length;
        int dy = brick[0].length;

        if (in(x) && in(y) && in(x + dx - 1) && in(y + dy - 1)) {
            for (int i = 0; i < dx; i++) {
                for (int j = 0; j < dy; j++) {
                    if (grid[x + i][y + j] > 0 && brick[i][j] > 0)
                        return false;
                }
            }

            for (int i = 0; i < dx; i++) {
                for (int j = 0; j < dy; j++) {
                    score += brick[i][j];
                    grid[x + i][y + j] += brick[i][j];
                }
            }
            step();
            return true;
        }
        return false;
    }

    public void gen() {
        for (int i = 0; i < 5;) {
            if (put(Generator.generate(),
                    rand.nextInt(size), rand.nextInt(size))) {
                i++;
            };
        }
    }

    public void restart() {
        grid = new int[size][size];
        for (int i = 0; i < size; i++) {
            grid[i] = new int[size];
            for (int j = 0; j < size; j++)
                grid[i][j] = 0; //rand.nextInt(2);
        }
        gen();
        score = 0;
    }
    public boolean isGameOver(BricksManager bar) {
        for (int i = 0; i < bar.bricks.length; i++) {
            int[][] brick = bar.get(i);
            for (int x = 0; x <= size - brick.length; x++) {
                for (int y = 0; y <= size - brick[0].length; y++) {
                    boolean canPlace = true;
                    for (int bx = 0; bx < brick.length; bx++) {
                        for (int by = 0; by < brick[0].length; by++) {
                            if (brick[bx][by] == 1 && grid[x + bx][y + by] != 0) {
                                canPlace = false;
                                break;
                            }
                        }
                        if (!canPlace) {
                            break;
                        }
                    }
                    if (canPlace) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
