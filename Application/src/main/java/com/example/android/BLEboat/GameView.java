/*
    Copyright 2015 erz05

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
 */

package com.example.android.BLEboat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;
import java.util.Vector;


public class GameView extends android.view.SurfaceView implements android.view.SurfaceHolder.Callback {

    float width;
    float height;
    float centerX;
    float centerY;
    float min;
    float posX;
    float posY;
    float radius;
    //GameLoop gameLoop;
    Paint paint;
    Random random = new Random();
    int i;
    int size = 20;
    int minSpeed;
    int maxSpeed;
    int minRadius;
    int maxRadius;
    int maxX;
    int maxY;
    int tmpRadius;
    Bitmap droid;
    RectF rectF;
    float rotate;

    double angle;
    double power;

    double angle2;

    //Vector<Star> stars;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GameView(Context context) {
        super(context);
        init();
    }

    private void init() {
        getHolder().addCallback(this);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        droid = BitmapFactory.decodeResource(getResources(), R.drawable.droid);
        rectF = new RectF();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        if (canvas == null) return;
        super.draw(canvas);
        canvas.drawColor(Color.BLACK);

        /*if (stars != null && stars.size() > 0) {
            for (i=0; i<size; i++) {
                stars.get(i).draw(canvas, paint, width, height, maxRadius);
            }
        }*/

        posX -= Math.cos(angle) * (power/2);
        posY += Math.sin(-angle) * (power/2);
        if (posX > width - radius) posX = width - radius;
        if (posX < radius) posX = radius;
        if (posY > height - radius) posY = height - radius;
        if (posY < radius) posY = radius;

        if (angle2 == 0) rotate = 0;
        else rotate = (float) Math.toDegrees(angle2) - 90;
        canvas.rotate(rotate, posX, posY);
        rectF.set(posX - radius, posY - radius, posX + radius, posY + radius);
        canvas.drawBitmap(droid, null, rectF, paint);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //gameLoop = new GameLoop(this);
        //gameLoop.setRunning(true);
        //gameLoop.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        this.width = width;
        this.height = height;
        min = Math.min(width, height);

        centerX = width/2;
        centerY = height/2;
        posX = centerX;
        posY = centerY;
        radius = min/12;
        rectF = new RectF(posX - radius, posY - radius, posX + radius, posY + radius);

        minSpeed = (int) (min/75);
        maxSpeed = (int) (min/25);

        minRadius = (int) (min/250);
        maxRadius = (int) (min/220);

        if (maxRadius == minRadius) maxRadius += minRadius;

        /*stars = new Vector<>();
        for (i=0; i<size; i++) {
            tmpRadius = random.nextInt(maxRadius - minRadius) + minRadius;
            maxX = width - tmpRadius;
            maxY = height - tmpRadius;
            stars.add(new Star(random.nextInt(maxX - tmpRadius + (maxRadius * 4)) + (tmpRadius - (maxRadius * 4)),
                    random.nextInt(maxY - tmpRadius + (maxRadius * 4)) + (tmpRadius - (maxRadius * 4)),
                    random.nextInt(maxSpeed - minSpeed) + minSpeed,
                    tmpRadius));
        }*/
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //gameLoop.setRunning(false);
        //gameLoop = null;
    }

    public void move(double angle, double power) {
        this.angle = angle;
        this.power = power;
    }

    public void rotate(double angle2) {
        this.angle2 = angle2;
    }
}