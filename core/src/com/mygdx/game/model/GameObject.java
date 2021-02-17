package com.mygdx.game.model;

public abstract class GameObject {
    int x, y, z;
    int[] size;
    float health;
    public GameObject(int x, int y, int z, int[] size, float health) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.size = size;
        this.health = health;
    }

    public void draw() {
    }

    public void take_damage() {
    }

    public boolean is_broken() {
        return this.health <= 0;
    }
}
