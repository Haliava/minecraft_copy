package com.mygdx.game.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.mygdx.game.Main;

public class Material2D {
    public static int TEXTURE_2D_SIZE = 100;

    public static TextureRegion[] hotbar_squares = new TextureRegion[] {
            Main.hotbar_atlas.findRegion("square1"),
            Main.hotbar_atlas.findRegion("square2"),
            Main.hotbar_atlas.findRegion("square3"),
            Main.hotbar_atlas.findRegion("square4"),
            Main.hotbar_atlas.findRegion("square5"),
            Main.hotbar_atlas.findRegion("square6"),
            Main.hotbar_atlas.findRegion("square7"),
            Main.hotbar_atlas.findRegion("square8"),
            Main.hotbar_atlas.findRegion("square9"),
    };
}
