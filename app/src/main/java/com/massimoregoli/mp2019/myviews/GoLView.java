package com.massimoregoli.mp2019.myviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.massimoregoli.mp2019.R;

import java.util.Random;

public class GoLView extends View {
    private Paint paint = new Paint();
    private int gridX;
    private int gridY;
    private float parentWidth;
    private float parentHeight;
    private Random rnd = new Random();
    private int posX, posY;
    private int shape;
    private GoL world;

    Thread thread;
    public GoLView(Context context) {
        super(context);
    }

    public GoLView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.GoLView,
                0, 0);
        try {
            gridX = a.getInt(R.styleable.GoLView_gridX, 50);
            gridY = a.getInt(R.styleable.GoLView_gridY, 50);
            shape = a.getInt(R.styleable.GoLView_cellShape, 1);
            paint.setColor(a.getColor(R.styleable.GoLView_aliveCellColor, Color.BLACK));
        } finally {
            a.recycle();
        }
        posX = gridX / 2;
        posY = gridY / 2;
        world = new GoL(gridX, gridY);
    }

    public GoLView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GoLView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        parentHeight = MeasureSpec.getSize(heightMeasureSpec);
        this.setMeasuredDimension((int)parentWidth, (int)parentHeight);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void drawGrid(Canvas canvas, float dx, float dy) {
        Paint gridPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        Log.w("GOL", "DRAWING GRID");
        borderPaint.setColor(Color.BLACK);
        gridPaint.setColor(Color.DKGRAY);
        gridPaint.setStyle(Paint.Style.STROKE);
        gridPaint.setPathEffect(new DashPathEffect(new float[] {1,1}, 0));
        gridPaint.setStrokeWidth(2);
        for (int i = 0; i <= gridX; i++) {
            canvas.drawLine(dx * i, 0, dx * i, parentHeight, gridPaint);
        }
        for (int i = 0; i <= gridY; i++) {
            canvas.drawLine(0, dy * i, parentWidth, dy * i, gridPaint);
        }
        canvas.drawLine(0,0,parentWidth, 0, borderPaint);
        canvas.drawLine(0,0, 0, parentHeight, borderPaint);
        canvas.drawLine(parentWidth,0, parentWidth, parentHeight, borderPaint);
        canvas.drawLine(0,parentHeight,parentWidth, parentHeight, borderPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float dx, dy;
        Paint textPaint = new Paint(paint);

        if(isInEditMode())
            return;
        dx = parentWidth / gridX;
        dy = parentHeight / gridY;
        drawGrid(canvas, dx, dy);
        textPaint.setTextSize(32);

        String text = "AGE: " + world.getAge();
        canvas.drawText(text, 32, parentHeight - 32,textPaint);

        for(int i = 0; i < gridX; i++) {
            for(int j = 0; j < gridY; j++) {
                if(world.isAlive(i, j)) {
                    if(shape == 0)
                        canvas.drawRect(dx * i,dy * j, dx * (i+1), dy*(j+1), paint);
                    else if(shape == 1)
                        canvas.drawOval(dx * i,dy * j, dx * (i+1), dy*(j+1), paint);
                    else
                        canvas.drawText("+", dx*i, dy*j,textPaint);
                }
            }
        }
        invalidate();
    }

    public void run() {
        thread = new Thread() {
            @Override
            public void run() {
                while(true) {
                    try {
                        sleep(200);
                        world.nextGeneration();
//                        invalidate();
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }
        };
        thread.start();
    }
    public void step() {
        world.nextGeneration();
        invalidate();
    }

    public void stop() {
        thread.interrupt();
    }

    public void switchAction() {
        if(thread != null && thread.isAlive())
            stop();
        else {
            run();
        }
    }

    public void changeState(float x, float y) {
        int px, py;

        px = (int) ((x / parentWidth) * gridX);
        py = (int) ((y / parentHeight) * gridY);

        world.changeState(px, py);
        invalidate();
    }

    public void clear() {
        world.clear();
        invalidate();
    }

    public boolean isRunning() {
        if(thread == null)
            return false;
        return thread.isAlive();
    }

    class GoL {
        int age = 0;
        private Random rnd = new Random();
        private int sX, sY;
        private int [][] world;
        public GoL(int sizeX, int sizeY) {
            world = new int[sizeX][sizeY];
            sX = sizeX;
            sY = sizeY;
        }

        public void random(float p) {
            for(int i = 0; i < sX; i++) {
                for(int j = 0; j < sY; j++) {
                    if(rnd.nextFloat() <= p)
                        world[i][j] = 1;
                    else
                        world[i][j] = 0;
                }
            }
        }

        public boolean isAlive(int i, int j) {
            return world[i][j] == 1;
        }

        public void nextGeneration() {
            int [][] newWorld = new int[sX][sY];

            age++;
            Log.w("GOL", "AGE: " + age);
            for(int i = 0; i < sX; i++) {
                for(int j = 0; j < sY; j++) {
                    int living = count(i, j);
                    newWorld[i][j] = rule(living, world[i][j]);
                }
            }
            for(int i = 0; i < sX; i++) {
                for (int j = 0; j < sY; j++) {
                    world[i][j] = newWorld[i][j];
                }
            }
        }


        int rule(int living, int state) {
            if(state == 1) {
                if(living < 2 || living > 3)
                    return 0;
                return 1;
            } else {
                if(living == 3)
                    return 1;
            }
            return 0;
        }

        private int count(int x, int y) {
            int ret = 0;
            for(int i = -1; i < 2; i++) {
                for(int j = -1; j < 2; j++) {
                    int px = x + i;
                    int py = y + j;
                    if(true) {
                        px = (px + gridX) % gridX;
                        py = (py + gridY) % gridY;
                    }
                    if(px < 0 || px >= sX || py < 0 || py >= sY )
                        continue;
                    if(i != 0 || j != 0)
                        ret += world[px][py];
                }
            }
            return ret;
        }

        public void changeState(int px, int py) {
            if(world[px][py] == 1)
                world[px][py] = 0;
            else
                world[px][py] = 1;
        }

        public void clear() {
            world = new int[gridX][gridY];
        }

        public int getAge() {
            return age;
        }
    }
}
