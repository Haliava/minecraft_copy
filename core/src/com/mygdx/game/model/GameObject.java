package com.mygdx.game.model;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;

public abstract class GameObject extends ModelInstance {
    public float x, y, z;
    protected int ID;
    int size;
    int[] diff_size;
    float health;

    public GameObject(int x, int y, int z, int size, float health, Model model) {
        super(model, x, y, z);
        ID = 0;
        this.x = x;
        this.y = y;
        this.z = z;
        this.size = size;
        this.health = health;
    }

    public GameObject(float x, float y, float z, int size, float health, Model model) {
        super(model, x, y, z);
        this.x = x;
        this.y = y;
        this.z = z;
        this.size = size;
        this.health = health;
    }

    public GameObject(float x, float y, float z, int[] size, float health, Model model) {
        super(model, x, y, z);
        this.x = x;
        this.y = y;
        this.z = z;
        this.diff_size = size;
        this.health = health;
    }

    public int getID() { return ID; }

    public void setID(int ID) { this.ID = ID; }

    public void take_damage(int damage) { this.health -= damage; }

    public boolean is_broken() {
        return this.health <= 0;
    }
}
