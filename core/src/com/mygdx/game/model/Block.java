package com.mygdx.game.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.mygdx.game.Main;

public class Block extends GameObject {
    public static float side_size = 6f;
    String type = Main.BLOCK_TYPES[0];

    public Block(float x, float y, float z, int size, float health, String type, Model model) {
        super(x, y, z, size, health, model);
        this.type = type;
    }

    public static Model createModel(float w, float h, float d, ModelBuilder modelBuilder) {
        return modelBuilder.createBox(w, h, d,
                new Material(ColorAttribute.createDiffuse(Color.OLIVE)),
                VertexAttributes.Usage.Position|VertexAttributes.Usage.Normal);
    }

    public void draw() {

    }
}
