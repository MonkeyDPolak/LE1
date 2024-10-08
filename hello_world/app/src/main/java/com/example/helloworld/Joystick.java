package com.example.helloworld;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Joystick {
    private float baseCenterX, baseCenterY, baseRadius;
    private float stickCenterX, stickCenterY, stickRadius;
    private boolean isMoving = false;

    public Joystick(float baseCenterX, float baseCenterY, float baseRadius, float stickRadius) {
        this.baseCenterX = baseCenterX;
        this.baseCenterY = baseCenterY;
        this.baseRadius = baseRadius;
        this.stickRadius = stickRadius;
        this.stickCenterX = baseCenterX;
        this.stickCenterY = baseCenterY;
    }

    public void draw(Canvas canvas) {
        // Dessiner la base du joystick
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        canvas.drawCircle(baseCenterX, baseCenterY, baseRadius, paint);

        // Dessiner le stick du joystick
        paint.setColor(Color.BLUE);
        canvas.drawCircle(stickCenterX, stickCenterY, stickRadius, paint);
    }

    public boolean isPressed(float touchX, float touchY) {
        // Vérifier si l'utilisateur appuie sur le joystick
        float distance = (float) Math.sqrt(Math.pow(touchX - baseCenterX, 2) + Math.pow(touchY - baseCenterY, 2));
        return distance < baseRadius;
    }

    public void updatePosition(float touchX, float touchY) {
        // Mettre à jour la position du stick
        float deltaX = touchX - baseCenterX;
        float deltaY = touchY - baseCenterY;
        float distance = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        if (distance < baseRadius) {
            stickCenterX = touchX;
            stickCenterY = touchY;
        } else {
            stickCenterX = baseCenterX + (deltaX / distance) * baseRadius;
            stickCenterY = baseCenterY + (deltaY / distance) * baseRadius;
        }
    }

    public void resetPosition() {
        stickCenterX = baseCenterX;
        stickCenterY = baseCenterY;
    }

    public float getDeltaX() {
        return stickCenterX - baseCenterX;
    }

    public float getDeltaY() {
        return stickCenterY - baseCenterY;
    }
}

