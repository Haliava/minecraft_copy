package com.mygdx.game.control;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Main;


public class Controls {
    public Vector2 direction;
    Texture circleImg, stickImg;
    Circle circleBounds, stickBounds;
    Vector2 initialStickCords;
    float circleR, stickR;
    int fingerNumber = -1;

    public Controls(Texture circleImg, Texture stickImg, Vector2 vector2, float size) {
        this.circleImg = circleImg;
        this.stickImg = stickImg;
        this.circleR = size / 2;
        this.stickR = size / 4;
        this.initialStickCords = vector2;
        this.circleBounds = new Circle(vector2, this.circleR);
        this.stickBounds = new Circle(vector2, this.stickR);
        direction = new Vector2(0, 0);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(circleImg, circleBounds.x - circleR, circleBounds.y - circleR, circleR * 2, circleR * 2);
        batch.draw(stickImg, stickBounds.x - stickR, stickBounds.y - stickR, stickR * 2, stickR * 2);
    }

    public void update(float touchX, float touchY, boolean isTouched, int pointer) {
        if (fingerNumber == -1 && isTouched && isInsideControls(touchX, touchY))
            fingerNumber = pointer;
        if (fingerNumber == pointer && isTouched && isOverlapping(circleBounds, stickBounds, touchX, touchY))
            control(touchX, touchY);
        if ((fingerNumber == pointer && !isTouched) || (fingerNumber == pointer && isTouched && !isOverlapping(circleBounds, stickBounds, touchX, touchY))) {
            control(initialStickCords.x, initialStickCords.y);
            fingerNumber = -1;
        }
    }

    public void control(float x, float y) {
        stickBounds.setPosition(x, y);
        float deltaX = circleBounds.x - stickBounds.x;
        float deltaY = circleBounds.y - stickBounds.y;
        float distance = getDistance(deltaX, deltaY);
        if (distance == 0) direction.set(0, 0);
        else direction.set(-(deltaX / distance), -(deltaY / distance));
        System.out.println(direction);
    }

    public float getDistance(float dx, float dy) { return (float) Math.sqrt(dx * dx + dy * dy); }

    public boolean isInsideControls(float touchX, float touchY) {
        return touchX * touchX + (Main.HEIGHT - touchY * touchY) < (circleBounds.x) * (circleBounds.y);
    }

    public boolean isOverlapping(Circle c1, Circle c2, float targetX, float targetY) {
        return c1.radius + c2.radius > getDistance(c1.x - targetX, c1.y -  targetY) + 20;
    }
}
