package com.mygdx.game.model;

public abstract class Item2d {
    int sizeX, sizeY, x, y;
    Texture texture;

    public Item2d(int x, int y, int sizeX, int sizeY, Texture texture) {
        this.x = x;
        this.y = y;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.texture = texture;
    }
}
