package com.mygdx.game.model;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.control.Controls;

public class Player extends GameObject {
    Inventory inventory;
    float MAX_SPEED = 2f;
    public float velocityX, velocityY, velocityZ;

    public Player(float x, float y, float z, int[] size, float health, Model model) {
        super(x, y, z, size, health, model);
        velocityX = 0;
        velocityY = 0;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Model createModel(float w, float h, float d, ModelBuilder modelBuilder) {
        return modelBuilder.createCone(w, h, d, 3,
                new Material(ColorAttribute.createDiffuse(Color.BLUE)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
    }

    public void update(Controls controls, Camera camera) {
        if (controls.direction.x != Math.sqrt(-1) && controls.direction.y != Math.sqrt(-1)) {
            velocityX = controls.direction.x * MAX_SPEED;
            velocityZ = controls.direction.y * MAX_SPEED;
            x += velocityX;
            z += velocityZ;
            //System.out.println("\n" + "x:" + x + "\n" + "y:" + y + "\n" + "z:" + z);
            camera.translate(x, y, z);
        }
    }

    public boolean is_attacking() {
        return false;
    }

    public boolean is_moving() {
        return false;
    }

    public boolean is_mining() {
        return false;
    }
}
