package com.mygdx.game.model;
import java.util.ArrayList;
import java.util.HashMap;


public class Map {
    public int sizeX, sizeY;
    public ArrayList<Chunk> chunkMap;

    public Map(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        chunkMap = new ArrayList<>();
    }

    public void add_chunk(Chunk chunk) {
        if (!chunkMap.contains(chunk)) {
            chunkMap.add(chunk);
        }
    }

    public void clear() {
        this.chunkMap.clear();
    }

    public String toString() {
        return chunkMap.toString();
    }
}
