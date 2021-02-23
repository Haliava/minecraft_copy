package com.mygdx.game.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

public class Player extends GameObject {
    Inventory inventory;
    ModelBuilder modelBuilder;

    public Player(int x, int y, int z, int[] size, float health, Inventory inventory, Model model) {
        super(x, y, z, size, health, model);
        this.inventory = inventory;
        modelBuilder = new ModelBuilder();
    }

    public Model get_model(float w, float h, float d) {
        // new Material(ColorAttribute.createDiffuse(Color.WHITE)), VertexAttributes.Usage.Position|VertexAttributes.Usage.Normal)
        return modelBuilder.createRect(0f, 0f, 0f, 4f, 4f, 0f, 4f, 4f, 12f, 4f, 4f, 0f, 0f, 0f, 0f,
                new Material(ColorAttribute.createDiffuse(Color.WHITE)), VertexAttributes.Usage.Position|VertexAttributes.Usage.Normal);
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
