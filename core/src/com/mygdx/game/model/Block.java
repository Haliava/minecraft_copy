package com.mygdx.game.model;

public class Block extends GameObject {
    Object material;

    public Block(int x, int y, int z, int[] size, float health) {
        super(x, y, z, size, health);
    }
}
