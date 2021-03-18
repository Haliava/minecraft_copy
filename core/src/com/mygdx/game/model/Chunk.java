package com.mygdx.game.model;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.mygdx.game.Main;

import java.util.HashMap;

public class Chunk {
    public static int sizeX = 16;
    public static int sizeZ = 20;
    public int chunkX;
    public int chunkY;
    private java.util.Map<Integer, Block> blockMap;
    public java.util.Map<Integer, Block> coordsBlockMap;

    public Chunk(java.util.Map<Integer, Block> blockMap, int chunkX, int chunkY) {
        this.blockMap = blockMap;
        this.chunkX = chunkX;
        this.chunkY = chunkY;
    }

    public Chunk(int startI, int startY, Model blockModel) {
        blockMap = new HashMap<>();
        coordsBlockMap = new HashMap<>();
        this.chunkY = 0;
        this.chunkX = 0;

        initialise(startI, startY, blockModel);
    }

    public void initialise(int startI, int startY, Model blockModel) {
        OpenSimplexNoise noise = new OpenSimplexNoise();
        for (int i = startI; i < startI + sizeX; i++)
            for (int j = startY; j < startY + sizeX; j++) {
                float noise_result = (float) (noise.eval(0.03 * i, 0.05 * j) * 12 * Block.side_size);
                float perlin_unofficial = (float) (Noise.noise(0.03 * i, 0.05 * j, 0.05 * (i + j)) * 12 * Block.side_size); // НЕОФИЦИАЛЬНАЯ РЕАЛИЗАЦИЯ ШУМА ПЕРЛИНА
                float perlin_official = (float) NoisePerlin.noise(0.08 * i, 0.05 * j) * 12 * Block.side_size; // ОФИЦИАЛЬНАЯ РЕАЛИЗАЦИЯ ШУМА ПЕРЛИНА
                Block block = new Block(Block.side_size * i, perlin_official - (perlin_official % Block.side_size), Block.side_size * j,
                        (int) Block.side_size, 10, Main.BLOCK_TYPES[1], blockModel);
                blockMap.put(Main.ID, block);
                coordsBlockMap.put((int) ((int)perlin_official - (perlin_official % Block.side_size)), block);
                Main.ID++;
                /*for (int k = -sizeZ; k < sizeZ; k++) {
                    if (k * Block.side_size < block.y) {
                        blockMap.put(Main.ID, new Block(Block.side_size * i,
                                perlin_official - (perlin_official % Block.side_size) + k * Block.side_size, Block.side_size * j,
                                (int) Block.side_size, 10, Main.BLOCK_TYPES[0], blockModel));
                    } else {
                        blockMap.put(Main.ID, new Block(Block.side_size * i,
                                perlin_official - (perlin_official % Block.side_size) - k * Block.side_size, Block.side_size * j,
                                (int) Block.side_size, 0, Main.BLOCK_TYPES[0], blockModel));
                    }
                    Main.ID++;
                }*/
            }
        chunkX = startI / sizeX;
        chunkY = startY / sizeX;
    }

    public void drawBlocks(ModelBatch modelBatch, Environment environment) {
        for (Block block : blockMap.values())
            block.draw(modelBatch, environment);
    }

    public Block getBlock(int ID) {
        return blockMap.get(ID);
    }

    public Block getBlockByYCoord(int y) {
        return coordsBlockMap.get(y);
    }

    public void addBlock(Block block) {
        blockMap.put(block.getID(), block);
    }

    public void removeBlock(int ID) {
        blockMap.remove(ID);
    }
}
