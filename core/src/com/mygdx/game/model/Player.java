package com.mygdx.game.model;

public class Player extends GameObject {
    Inventory inventory;

    public Player(int x, int y, int z, int[] size, float health, Inventory inventory) {
        super(x, y, z, size, health);
        this.inventory = inventory;
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
