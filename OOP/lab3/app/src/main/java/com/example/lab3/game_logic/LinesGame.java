package com.example.lab3.game_logic;

import android.widget.Toast;
import android.graphics.Color;
import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

import com.example.lab3.drawing.Drawer;
import com.example.lab3.components.BricksManager;
import com.example.lab3.components.Grid;
import com.example.lab3.components.Vector;

public class LinesGame extends View{
    private Context context;
    private static final int FINGER_OFFSET = 150;
    public static final int GRID_S = 10;
    public static final int BAR_S = 3;

    Grid grid = new Grid(GRID_S);
    BricksManager bar = new BricksManager(BAR_S);
    private int[][] selBrick = null;
    private int selIndex = -1;
    Vector p = new Vector();

    public LinesGame(Context context) {
        super(context);
        this.context = context;
        setBackgroundColor(Color.rgb(247, 239, 215));
        Drawer.assets = new Drawer.Assets(context.getResources());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawer.drawGrid(canvas, grid);
        Drawer.drawBar(canvas, bar);

        if (selBrick != null) {
            Drawer.drawBrickCenter(canvas, selBrick, p, 1f);
        }
        invalidate();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (event.getY() > this.getWidth() + Drawer.TOP_MARGIN) {
                selIndex = (int) (event.getX() / this.getWidth() * BAR_S);
                selBrick = bar.get(selIndex);
                p.set(event.getX(), event.getY());
            } else if (event.getY() < Drawer.TOP_MARGIN) {
                restart();
            }
        }
        else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            p.set(event.getX(), event.getY() - FINGER_OFFSET);
        }
        else if (event.getAction() == MotionEvent.ACTION_UP && selBrick != null) {
            Vector pa = p.plusY(- Drawer.TOP_MARGIN).mult((float) GRID_S / getWidth()).
                    plus(-0.5f * selBrick.length, -0.5f * selBrick[0].length);

            if (grid.put(selBrick, Math.round(pa.x), Math.round(pa.y)))
                bar.remove(selIndex);

            selBrick = null;
            selIndex = -1;
        }
        else if (event.getAction() == MotionEvent.ACTION_UP && selBrick != null) {
            Vector pa = p.plusY(-Drawer.TOP_MARGIN).mult((float) GRID_S / getWidth()).
                    plus(-0.5f * selBrick.length, -0.5f * selBrick[0].length);

            if (grid.put(selBrick, Math.round(pa.x), Math.round(pa.y))) {
                bar.remove(selIndex);
            } else {
                if (grid.isGameOver(bar)) {
                    Toast.makeText(context, "Game over!", Toast.LENGTH_LONG).show();
                    restart();
                }
            }

            selBrick = null;
            selIndex = -1;
        }
        return true;
    }

    public void restart() {
        grid.restart();
        bar.restart();
    }
}

