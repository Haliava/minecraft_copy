package com.mygdx.game.control;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.mygdx.game.Main;
import com.mygdx.game.model.Block;
import com.mygdx.game.model.Player;

import java.util.HashMap;

import javax.swing.text.html.HTMLDocument;


public class Controls {
    public Vector3 direction;
    Texture circleImg, stickImg, jumpImg;
    Circle circleBounds, stickBounds, jumpBounds;
    Vector2 initialStickCords;
    Vector3 touchedBlock;
    Camera camera;
    float circleR, stickR;
    int fingerNumber = -1;
    boolean isJumping = false;

    public Controls(Texture circleImg, Texture stickImg, Texture jumpImg, Vector2 vector2, float size, Camera camera) {
        Vector2 jumpCoords = new Vector2(Main.WIDTH - vector2.x, vector2.y);
        this.circleImg = circleImg;
        this.stickImg = stickImg;
        this.jumpImg = jumpImg;
        this.circleR = size / 2;
        this.stickR = size / 4;
        this.initialStickCords = vector2;
        this.circleBounds = new Circle(vector2, this.circleR);
        this.stickBounds = new Circle(vector2, this.stickR);
        this.jumpBounds = new Circle(jumpCoords, this.stickR);
        this.touchedBlock = new Vector3(0, 0, 0);
        this.camera = camera;
        direction = new Vector3(0, 0,0);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(circleImg, circleBounds.x - circleR, circleBounds.y - circleR, circleR * 2, circleR * 2);
        batch.draw(stickImg, stickBounds.x - stickR, stickBounds.y - stickR, stickR * 2, stickR * 2);
        batch.draw(jumpImg, jumpBounds.x - stickR, jumpBounds.y - stickR, stickR * 2, stickR * 2);
    }

    public void update(float touchX, float touchY, boolean isTouched, int pointer) {
        if (fingerNumber == -1 && isTouched && (isInsideControls(touchX, touchY, circleBounds) || isInsideControls(touchX, touchY, jumpBounds)))
            fingerNumber = pointer;
        if (jumpBounds.contains(touchX, touchY)) {
            control(0, 0, Main.MAX_VELOCITY);
        }
        if (fingerNumber == pointer && isTouched && isOverlapping(circleBounds, stickBounds, touchX, touchY))
            control(touchX, touchY, 0);
        if ((fingerNumber == pointer && !isTouched) || (fingerNumber == pointer && isTouched && !isOverlapping(circleBounds, stickBounds, touchX, touchY))) {
            control(initialStickCords.x, initialStickCords.y, 0);
            fingerNumber = -1;
        }
    }

    public void control(float x, float y, float z) {
        if (z == 0) stickBounds.setPosition(x, y);
        float deltaX = circleBounds.x - stickBounds.x;
        float deltaZ = circleBounds.y - stickBounds.y;
        float distance = getDistance(deltaX, deltaZ);
        if (z != 0 && !isJumping) {
            direction.set(0, Main.MAX_VELOCITY / 2, 0);
        } else if (distance == 0) direction.set(0, 0 ,0);
        else {
            direction.set(((deltaX / distance) * camera.direction.z - (deltaZ / distance) * camera.direction.x),
                    z, -((deltaX / distance) * camera.direction.x + (deltaZ / distance) * camera.direction.z));
        }
    }

    public float getDistance(float dx, float dy) { return (float) Math.sqrt(dx * dx + dy * dy); }

    public boolean isInsideControls(float touchX, float touchY, Circle obj) {
        return touchX * touchX + (Main.HEIGHT - touchY * touchY) < obj.x * obj.y;
    }

    public boolean isInsideJumpControls(float touchX, float touchY) {
        return ((touchX >= (jumpBounds.x - 2 * jumpBounds.radius) && touchX <= (jumpBounds.x + 2 * jumpBounds.radius))
                && (touchY >= (jumpBounds.y - 2 * jumpBounds.radius) && touchY <= (jumpBounds.y + 2 * jumpBounds.radius)));
    }

    public boolean isOverlapping(Circle c1, Circle c2, float targetX, float targetY) {
        return c1.radius + c2.radius > getDistance(c1.x - targetX, c1.y -  targetY) + 20;
    }
}
