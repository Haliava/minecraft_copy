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
    public int currentChunkCoordX, currentChunkCoordY;
    float accumulatedGravity;

    public Player(float x, float y, float z, int[] size, float health, Model model) {
        super(x, y, z, size, health, model);
        currentChunkCoordX = 0;
        currentChunkCoordY = 0;
        accumulatedGravity = 0f;
        velocityX = 0;
        velocityY = 0;
        velocityZ = 0;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int[] getChunkCoords() {
        currentChunkCoordX = (int) ((int) x / (Chunk.sizeX * Block.side_size));
        currentChunkCoordY = (int) ((int) z / (Chunk.sizeX * Block.side_size));
        return new int[] {currentChunkCoordX, currentChunkCoordY};
    }

    public static Model createModel(float w, float h, float d, ModelBuilder modelBuilder) {
        return modelBuilder.createCone(w, h, d, 3,
                new Material(ColorAttribute.createDiffuse(Color.BLUE)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
    }

    public void update(Controls controls, float dTime) {
        getChunkCoords();
        if (controls.direction.x != Math.sqrt(-1) && controls.direction.z != Math.sqrt(-1)) {
            velocityX = controls.direction.x * Main.MAX_VELOCITY;
            velocityZ = controls.direction.z * Main.MAX_VELOCITY;
            velocityY = controls.direction.y;
            /*if (Main.WORLD_MAP.blockMap[(int) (x / Block.side_size) - 1][(int) (y / Block.side_size)][(int) (z / Block.side_size)].type == "air" ||
                    Main.WORLD_MAP.blockMap[(int) (x / Block.side_size) + 1][(int) (y / Block.side_size)][(int) (z / Block.side_size)].type == "air") velocityX = 0;
            if (Main.WORLD_MAP.blockMap[(int) (x / Block.side_size)][(int) (y / Block.side_size)][(int) (z / Block.side_size) - 1].type == "air" ||
                    Main.WORLD_MAP.blockMap[(int) (x / Block.side_size)][(int) (y / Block.side_size)][(int) (z / Block.side_size) + 1].type == "air") velocityZ = 0;*/
            x += velocityX;
            z += velocityZ;
            y += velocityY;
        }
        try {
            String type = Main.WORLD_MAP.blockMap[(int) (x / Block.side_size)][(int) (y / Block.side_size) - 1][(int) (z / Block.side_size)].type;
            //System.out.println((int) (x / Block.side_size) + ", " + (int) ((y / Block.side_size) - 2) + ", " + ((int) (z / Block.side_size)) + "\n");
            if (type != "air" && velocityY >= 0) {
                accumulatedGravity = 0;
            } else accumulatedGravity += Main.GRAVITY * dTime;
        } catch (Exception e) {
            //System.out.println("ERROR" + e);
            accumulatedGravity += Main.GRAVITY * dTime;
        }
        y -= accumulatedGravity * dTime;
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
