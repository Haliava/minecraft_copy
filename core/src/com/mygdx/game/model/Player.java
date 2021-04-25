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
import com.mygdx.game.control.HotbarSquare;

public class Player extends GameObject {
    Inventory inventory;
    public boolean isMoving;
    public float velocityX, velocityY, velocityZ;
    public int coordX, coordY, coordZ;
    public int flaggedX, flaggedZ;
    public int currentChunkCoordX, currentChunkCoordY;
    public float accumulatedGravity;

    public Player(float x, float y, float z, int[] size, float health, Model model) {
        super(x, y, z, size, health, model);
        currentChunkCoordX = 0;
        currentChunkCoordY = 0;
        accumulatedGravity = 0f;
        velocityX = 0;
        velocityY = 0;
        velocityZ = 0;
        isMoving = false;
        coordX = 0;
        coordY = 0;
        coordZ = 0;
        flaggedX = 0;
        flaggedZ = 0;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int[] getChunkCoords() {
        currentChunkCoordX = (int) ((int) x / (Chunk.sizeX * Block.side_size));
        currentChunkCoordY = (int) ((int) z / (Chunk.sizeX * Block.side_size));
        return new int[] {currentChunkCoordX, currentChunkCoordY};
    }

    public void update_postition() {
        coordX = (int) (x / Block.side_size);
        coordY = (int) ((y - Block.side_size) / Block.side_size) - 1;
        coordZ = (int) (z / Block.side_size);
    }

    public static Model createModel(float w, float h, float d, ModelBuilder modelBuilder) {
        return modelBuilder.createCone(w, h, d, 3,
                new Material(ColorAttribute.createDiffuse(Color.BLUE)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
    }

    public void update(Controls controls, float dTime) {
        if (controls.direction.x != Math.sqrt(-1) && controls.direction.z != Math.sqrt(-1)) {
            velocityX = controls.direction.x * Main.MAX_VELOCITY;
            velocityZ = controls.direction.z * Main.MAX_VELOCITY;
            velocityY = controls.direction.y * 2;
            if (velocityX != 0 && velocityY != 0 && velocityZ != 0) isMoving = true;

            try {
                if (Main.WORLD_MAP.blockMap[coordX][coordY][coordZ].type != "air") {
                    if (Main.WORLD_MAP.blockMap[coordX][coordY][coordZ + 1].type == "air" && controls.direction.z < 0) velocityZ = 0;
                    else if (Main.WORLD_MAP.blockMap[coordX][coordY][coordZ - 1].type == "air" && controls.direction.z > 0) velocityZ = 0;
                    if (Main.WORLD_MAP.blockMap[coordX + 1][coordY][coordZ].type == "air" && controls.direction.x < 0) velocityX = 0;
                    else if (Main.WORLD_MAP.blockMap[coordX - 1][coordY][coordZ].type == "air" && controls.direction.x > 0) velocityX = 0;
                }
            } catch (Exception ignored) {}

            x += velocityX;
            z += velocityZ;
            y += velocityY;
        }
        update_postition();
        try {
            String type = Main.WORLD_MAP.blockMap[coordX][coordY - 1][coordZ].type;
            if (type != "air" || coordY <= 1)
                accumulatedGravity = 0;
            else
                accumulatedGravity += Main.GRAVITY * dTime;
        } catch (Exception e) { }
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
