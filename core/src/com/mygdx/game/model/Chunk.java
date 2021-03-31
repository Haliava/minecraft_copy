package com.mygdx.game.model;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.mygdx.game.Main;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Chunk {
    public static int sizeX = 16;
    public static int sizeY = 16;
    public int chunkX;
    public int chunkY;
    private Map<String, Block> blockMap;
    private Block[][][] bMap;
    public Map<Integer, Block> coordsBlockMap;

    public Chunk(Map<String, Block> blockMap, int chunkX, int chunkY) {
        this.blockMap = blockMap;
        this.chunkX = chunkX;
        this.chunkY = chunkY;
    }

    public Chunk(int startI, int startY, Block[][][] map, Model blockModel) {
        blockMap = new HashMap<>();
        coordsBlockMap = new HashMap<>();
        this.chunkY = 0;
        this.chunkX = 0;
        this.bMap = map;

        initialise(startI, startY, blockModel);
    }

    public void initialise(int startI, int startY, Model blockModel) {
        chunkX = startI / sizeX;
        chunkY = startY / sizeX;
        for (int i = startI; i < startI + sizeX; i++) {
            for (int j = startY; j < startY + sizeX; j++) {
                float perlin_official = (float) (NoisePerlin.noise(0.08 * i, 0.05 * j) + 1) * 12 * Block.side_size;
                int yCoord = (int) ((perlin_official - (perlin_official % Block.side_size)) / Block.side_size);
                if (yCoord > Main.MAX_HEIGHT) yCoord = Main.MAX_HEIGHT;
                for (int k = yCoord; k >= 0; k--) {
                    Block block = new Block(Block.side_size * i,
                            (yCoord - k) * Block.side_size,
                            Block.side_size * j, (int) Block.side_size, 10, Main.BLOCK_TYPES[1], blockModel);
                    bMap[i][yCoord][k] = block;
                    blockMap.put(Main.ID, block);
                    Main.ID = chunkX + "" + chunkY + "" + i + "" + j + "" + k;
                }
                for (int k = yCoord; k < Main.MAX_HEIGHT; k++) {
                    Block block = new Block(Block.side_size * i,
                            (yCoord - k) * Block.side_size,
                            Block.side_size * j, (int) Block.side_size, 0, Main.BLOCK_TYPES[0], blockModel);
                    bMap[i][yCoord][k] = block;
                    blockMap.put(Main.ID, block);
                    Main.ID = chunkX + "" + chunkY + "" + i + "" + j + "" + k;
                }
            }
        }
    }

    public void drawBlocks(ModelBatch modelBatch, Environment environment) {
        for (Block block : blockMap.values())
            block.draw(modelBatch, environment);
    }

    public Block getBlock(String ID) {
        return blockMap.get(ID);
    }

    public Block getBlockByYCoord(int y) {
        return coordsBlockMap.get(y);
    }

    public void removeBlock(String ID) {
        blockMap.remove(ID);
    }
}
