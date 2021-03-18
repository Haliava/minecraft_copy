package com.mygdx.game.model;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Main;
import com.mygdx.game.control.Controls;

public class Player extends GameObject {
    Inventory inventory;
    public float velocityX, velocityY, velocityZ;

    public Player(float x, float y, float z, int[] size, float health, Model model) {
        super(x, y, z, size, health, model);
        velocityX = 0;
        velocityY = 0;
        velocityZ = 0;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Model createModel(float w, float h, float d, ModelBuilder modelBuilder) {
        return modelBuilder.createCone(w, h, d, 3,
                new Material(ColorAttribute.createDiffuse(Color.BLUE)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
    }

    public void update(Controls controls) {
        if (controls.direction.x != Math.sqrt(-1) && controls.direction.z != Math.sqrt(-1)) {
            velocityX = controls.direction.x * Main.MAX_VELOCITY;
            velocityZ = controls.direction.z * Main.MAX_VELOCITY;
            velocityY = controls.direction.y;
            x += velocityX;
            z += velocityZ;
            y += velocityY;
            //System.out.println("\n" + "x:" + x + "\n" + "y:" + y + "\n" + "z:" + z);
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
