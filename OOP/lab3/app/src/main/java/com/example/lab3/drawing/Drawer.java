package com.example.lab3.drawing;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.lab3.components.BricksManager;
import com.example.lab3.components.Grid;
import com.example.lab3.components.Vector;
import com.example.lab3.game_logic.LinesGame;

public class Drawer {
    public static final int TOP_MARGIN = 500;
    private static final float WELCOME_TEXT_FONT_SIZE = 100;
    private static final float BOMB_FONT_SIZE = 50;
    private static final float SCORE_FONT_SIZE = 150;

    public static class Assets {
        Paint tileP;
        Paint brickP;
        Paint brickPaint;
        Paint bombTextP;
        Paint scoreTextP;
        Paint welcomeTextP;

        private static Paint getPaint(int c, Paint.Style s) {
            Paint p = new Paint();
            p.setColor(c);
            p.setStyle(s);
            return p;
        }

        public Assets(Resources res) {
            tileP = getPaint(Color.rgb(140, 138, 156), Paint.Style.STROKE);
            tileP.setStrokeWidth(8);
            brickP = getPaint(Color.rgb(177, 177, 240), Paint.Style.FILL);
            brickPaint = getPaint(Color.rgb(181, 53, 53), Paint.Style.FILL);
            bombTextP = getPaint(Color.rgb(10, 8, 31), Paint.Style.FILL);
            bombTextP.setTextSize(BOMB_FONT_SIZE);
            scoreTextP = getPaint(Color.rgb(36, 36, 112), Paint.Style.FILL);
            scoreTextP.setTextSize(SCORE_FONT_SIZE);
            welcomeTextP = getPaint(Color.rgb(36, 36, 112), Paint.Style.FILL);
            welcomeTextP.setTextSize(WELCOME_TEXT_FONT_SIZE);
        }
    }

    public static Assets assets;

    private static void drawTile(Canvas canvas, Vector p, float sz) {
        canvas.drawRect(p.x, p.y, p.x + sz, p.y + sz, assets.tileP);
    }

    private static void drawBrickTile(Canvas canvas, Vector p, float sz) {
        canvas.drawRect(p.x, p.y, p.x + sz, p.y + sz, assets.brickP);
    }

    private static void drawBomb(Canvas canvas, Vector p, float sz, int time) {
        canvas.drawCircle(p.x + sz / 2, p.y + sz / 2, sz / 3, assets.brickPaint);

        canvas.drawText(String.valueOf(time),
                p.x + sz / 2 - BOMB_FONT_SIZE / 3, p.y + sz / 2 + BOMB_FONT_SIZE / 3,
                assets.bombTextP);
    }

    private static void drawScore(Canvas canvas, int score) {
        String text = "Total score: " + score;
        float textWidth = assets.scoreTextP.measureText(text);

        float x = (canvas.getWidth() - textWidth) / 2;
        float y = TOP_MARGIN - 50;

        canvas.drawText(text, x, y, assets.scoreTextP);
    }

    private static void drawWelcomeText(Canvas canvas) {
        String text = "✨Good luck in Lines!✨";
        float textWidth = assets.welcomeTextP.measureText(text);

        float x = (canvas.getWidth() - textWidth) / 2;
        float y = TOP_MARGIN - 240;

        canvas.drawText(text, x, y, assets.welcomeTextP);
    }


    private static void drawBrick(Canvas canvas,
                                 int[][] brick, Vector p, float scale, boolean isGrid) {
        float size = canvas.getWidth() * scale;
        float tSize = size / LinesGame.GRID_S;

        for (int i = 0; i < brick.length; i++) {
            for (int j = 0; j < brick[i].length; j++) {
                Vector np = p.plus(tSize * i, tSize * j);

                if (brick[i][j] == 1)
                    Drawer.drawBrickTile(canvas, np, tSize);
                else if (brick[i][j] >= 2 && isGrid)
                    Drawer.drawBomb(canvas, np, tSize, 8 - brick[i][j]);

                if (isGrid || brick[i][j] > 0)
                    Drawer.drawTile(canvas, np, tSize);
            }
        }
    }

    public static void drawBrickCenter(Canvas canvas, int[][] brick, Vector p, float scale) {
        float w = canvas.getWidth();
        float tileS = w / LinesGame.GRID_S * scale;

        Vector d = new Vector(brick.length, brick[0].length).mult(- tileS / 2);
        drawBrick(canvas, brick, p.plus(d), scale, false);
    }


    public static void drawGrid(Canvas canvas, Grid grid) {
        int gridBackgroundColor = Color.rgb(252, 250, 242);
        int gridSize = LinesGame.GRID_S;
        float tileSize = canvas.getWidth() / (float) gridSize;
        float gridWidth = tileSize * gridSize;
        float gridHeight = tileSize * gridSize;

        Paint gridBackgroundPaint = new Paint();
        gridBackgroundPaint.setColor(gridBackgroundColor);
        gridBackgroundPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, TOP_MARGIN, gridWidth, TOP_MARGIN + gridHeight, gridBackgroundPaint);
        drawBrick(canvas, grid.grid, new Vector(0, TOP_MARGIN), 1f, true);
        drawScore(canvas, grid.score);
        drawWelcomeText(canvas);
    }


    public static void drawBar(Canvas canvas, BricksManager bar) {
        final float scale = 0.6f;

        float w = canvas.getWidth();
        float barS = w / LinesGame.BAR_S;
        Vector p = new Vector(barS / 2, TOP_MARGIN + w + 200);

        for (int i = 0; i < LinesGame.BAR_S; i++) {
            drawBrickCenter(canvas, bar.bricks[i], p.plusX(barS * i), scale);
        }
    }
}
