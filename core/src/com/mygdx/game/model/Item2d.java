package com.mygdx.game.model;

public abstract class Item2d {
    int sizeX, sizeY, x, y;
    com.badlogic.gdx.graphics.Texture texture;

    public Item2d(int x, int y, int sizeX, int sizeY, com.badlogic.gdx.graphics.Texture texture) {
        this.x = x;
        this.y = y;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.texture = texture;
    }
}
