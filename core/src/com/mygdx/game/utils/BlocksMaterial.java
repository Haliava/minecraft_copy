package com.mygdx.game.utils;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.mygdx.game.Main;
import com.mygdx.game.model.Block;

public class BlocksMaterial {
    public static int TEXTURE_SIZE = 16;

    public static Material[] listOfMaterials = new Material[] {
            new Material(TextureAttribute.createDiffuse(Main.atlas.findRegion("grass"))),
            new Material(TextureAttribute.createDiffuse(Main.atlas.findRegion("dirt"))),
            new Material(TextureAttribute.createDiffuse(Main.atlas.findRegion("stone"))),
            new Material(TextureAttribute.createDiffuse(Main.atlas.findRegion("planks_oak")))
    };

    public static Material[] grass = new Material[] {
            new Material(TextureAttribute.createDiffuse(new TextureRegion(Main.atlas.findRegion("grass"), 0, 0, TEXTURE_SIZE, TEXTURE_SIZE))), // TOP
            new Material(TextureAttribute.createDiffuse(new TextureRegion(Main.atlas.findRegion("grass"), 0, TEXTURE_SIZE, TEXTURE_SIZE, TEXTURE_SIZE))), // SIDE
            new Material(TextureAttribute.createDiffuse(new TextureRegion(Main.atlas.findRegion("grass"), TEXTURE_SIZE, TEXTURE_SIZE, TEXTURE_SIZE, TEXTURE_SIZE))), // BOTTOM
    };
}
