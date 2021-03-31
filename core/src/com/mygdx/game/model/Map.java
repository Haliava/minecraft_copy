package com.mygdx.game.model;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.mygdx.game.Main;

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
        blockMap = new Block[sizeX * Chunk.sizeX][sizeY * Chunk.sizeX][Main.MAX_HEIGHT + 1];
        chunkMap = new Chunk[sizeX][sizeY];
    }

    public void initialiseBlockMap() {
        Model voidModel = new Model();
        for (int i = 0; i < sizeX * Chunk.sizeX; i++)
            for (int j = 0; j < sizeY * Chunk.sizeX; j++)
                for (int k = 0; k < Main.MAX_HEIGHT; k++)
                    blockMap[i][j][k] = new Block(Block.side_size * i, Block.side_size * j,
                            Block.side_size * k, (int) Block.side_size, 10, Main.BLOCK_TYPES[0], voidModel);
    }

    public void add_chunk(Chunk chunk) {
        chunkMap[chunk.chunkX][chunk.chunkY] = chunk;
    }

    public Chunk get_chunk(int x, int y) {
        return chunkMap[x][y];
    }

    public String toString() {
        return Arrays.deepToString(chunkMap);
    }
}
