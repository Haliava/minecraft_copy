package com.mygdx.game.model;
import java.util.ArrayList;
import java.util.HashMap;


public class Map {
    public int sizeX, sizeY;
    public Chunk[][] chunkMap;

    public Map(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        chunkMap = new Chunk[sizeX][sizeY];
    }

    public void add_chunk(Chunk chunk) {
        chunkMap[chunk.chunkX][chunk.chunkY] = chunk;
    }

    public Chunk get_chunk(int x, int y) {
        return chunkMap[x][y];
    }

    public String toString() {
        return chunkMap.toString();
    }
}
