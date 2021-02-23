package com.mygdx.game.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

public class Block extends GameObject {
    public static float side_size = 4f;
    private static ModelBuilder modelBuilder = new ModelBuilder();
    Object material;

    public Block(float x, float y, float z, int size, float health, Model model) {
        super(x, y, z, size, health, model);
    }


    public static Model createModel(float w, float h, float d) {
        return modelBuilder.createBox(w, h, d,
                new Material(ColorAttribute.createDiffuse(Color.OLIVE)),
                VertexAttributes.Usage.Position|VertexAttributes.Usage.Normal);
    }
}
