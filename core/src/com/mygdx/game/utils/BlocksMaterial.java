package com.mygdx.game.utils;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.mygdx.game.Main;
import com.mygdx.game.model.Block;

import java.util.HashMap;

public class BlocksMaterial {
    public static int TEXTURE_SIZE = 16;

    public static Material[] listOfMaterials = new Material[]{
            new Material(TextureAttribute.createDiffuse(Main.atlas.findRegion("grass"))),
            new Material(TextureAttribute.createDiffuse(Main.atlas.findRegion("dirt"))),
            new Material(TextureAttribute.createDiffuse(Main.atlas.findRegion("stone"))),
            new Material(TextureAttribute.createDiffuse(Main.atlas.findRegion("planks_oak"))),
            new Material(TextureAttribute.createDiffuse(Main.atlas.findRegion("bedrock")))
    };

    public static Material[] grass = new Material[]{
            new Material(TextureAttribute.createDiffuse(new TextureRegion(Main.atlas.findRegion("grass"), 0, 0, TEXTURE_SIZE, TEXTURE_SIZE))), // TOP
            new Material(TextureAttribute.createDiffuse(new TextureRegion(Main.atlas.findRegion("grass"), 0, TEXTURE_SIZE, TEXTURE_SIZE, TEXTURE_SIZE))), // SIDE
            new Material(TextureAttribute.createDiffuse(new TextureRegion(Main.atlas.findRegion("grass"), TEXTURE_SIZE, TEXTURE_SIZE, TEXTURE_SIZE, TEXTURE_SIZE))), // BOTTOM
    };

    public static TextureRegion[] textures = new TextureRegion[]{
            Main.atlas.findRegion("grass"),
            Main.atlas.findRegion("dirt"),
            Main.atlas.findRegion("stone"),
            Main.atlas.findRegion("planks_oak"),
            Main.atlas.findRegion("bedrock")
    };

    public static String[] blockTypes = new String[]{
            "air",
            "grass",
            "dirt",
            "stone",
            "planks_oak",
            "bedrock"
    };

    public static HashMap<String, Material> materialMap = new HashMap() {{
        put("grass", listOfMaterials[1]);
        put("dirt", listOfMaterials[1]);
        put("stone", listOfMaterials[2]);
        put("planks_oak", listOfMaterials[3]);
        put("bedrock", listOfMaterials[4]);
    }};

    public static HashMap<String, TextureRegion> textureMap = new HashMap() {{
        put("grass", textures[1]);
        put("dirt", textures[1]);
        put("stone", textures[2]);
        put("planks_oak", textures[3]);
        put("bedrock", textures[4]);
    }};
}
