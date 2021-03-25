package com.mygdx.game.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.IntAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.mygdx.game.Main;
import com.mygdx.game.utils.BlocksMaterial;

import java.util.Spliterators;

public class Block extends GameObject {
    public static float side_size = 6f;
    String type;

    public Block(float x, float y, float z, int size, float health, String type, Model model) {
        super(x, y, z, size, health, model);
        this.type = type;
    }

    public static Model createModel(ModelBuilder modelBuilder) {
        int attr = (VertexAttributes.Usage.Position|VertexAttributes.Usage.Normal|VertexAttributes.Usage.TextureCoordinates);
        /*float ls = Block.side_size / 2; // short for localSize
        modelBuilder.begin();
        modelBuilder.part("top", GL20.GL_TRIANGLES, attr, new Material(BlocksMaterial.grass[0])).rect(
                -ls,ls,-ls,  -ls,ls,ls,  ls,ls,ls,  ls,ls,-ls, 0f,0f,-1f);

        modelBuilder.part("front", GL20.GL_TRIANGLES, attr, new Material(BlocksMaterial.grass[1])).rect(
                -ls,-ls,-ls,  -ls,ls,-ls,  ls,ls,-ls,  ls,-ls,-ls, 0f,0f,1f);

        modelBuilder.part("left", GL20.GL_TRIANGLES, attr, new Material(BlocksMaterial.grass[1])).rect(
                -ls,-ls,ls,  -ls,-ls,-ls,  ls,-ls,-ls,  ls,-ls,ls, 0f,-1f,0f);

        modelBuilder.part("box", GL20.GL_TRIANGLES, attr, new Material(BlocksMaterial.grass[1])).rect(
                -ls,-ls,ls, -ls,-ls,-ls, ls,-ls,-ls, ls,-ls,ls, 0f,1f,0f);

        modelBuilder.part("box", GL20.GL_TRIANGLES, attr, new Material(BlocksMaterial.grass[1])).rect( // - - . - . . - . - - - -
                -ls,-ls,ls, -ls,ls,ls, -ls,ls,-ls, -ls,-ls,-ls, -1f,0f,0f);

        modelBuilder.part("box", GL20.GL_TRIANGLES, attr, new Material(BlocksMaterial.grass[2])).rect( // . - - . . - . . . . - .
                ls,-ls,-ls, ls,ls,-ls, ls,ls,ls, ls,-ls,ls, 1f,0f,0f);
        return modelBuilder.end();*/
        return modelBuilder.createBox(Block.side_size, Block.side_size, Block.side_size, BlocksMaterial.grass[2], attr);
    }

    public void draw(ModelBatch modelBatch, Environment environment) {
        if (!this.type.equals("air")) modelBatch.render(this, environment);
    }

    public float[] getCoords() {
        return new float[] {x, y};
    }
}
