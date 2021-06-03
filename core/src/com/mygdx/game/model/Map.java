package com.mygdx.game.model;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.mygdx.game.Main;
import com.mygdx.game.utils.BlocksMaterial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class Map {
    public int sizeX, sizeY;
    public Chunk[][] chunkMap;
    public Block[][][] blockMap;

    public Map(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        blockMap = new Block[sizeX * Chunk.sizeX][Main.MAX_HEIGHT][sizeY * Chunk.sizeX];
        chunkMap = new Chunk[sizeX][sizeY];
    }

    public void add_chunk(Chunk chunk) {
        chunkMap[chunk.chunkX][chunk.chunkY] = chunk;
    }

    public Chunk get_chunk(int x, int y) {
        return chunkMap[x][y];
    }

    public Chunk get_chunk(int[] x) { return chunkMap[x[0]][x[1]]; }

    public void clearChunkMap() { chunkMap = new Chunk[sizeX][sizeY]; }

    public void clearBlockMap() { blockMap = new Block[sizeX * Chunk.sizeX][Main.MAX_HEIGHT][sizeY * Chunk.sizeX]; }
}
