package com.mygdx.game.model;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.mygdx.game.Main;

import java.util.HashMap;

public class Chunk {
    public static int sizeX = 16;
    public static int sizeZ = 1;
    public int chunkX;
    public int chunkY;
    private java.util.Map<Integer, Block> blockMap;

    public Chunk(java.util.Map<Integer, Block> blockMap, int chunkX, int chunkY) {
        this.blockMap = blockMap;
        this.chunkX = chunkX;
        this.chunkY = chunkY;
    }

    public Chunk() {
        this.blockMap = new HashMap<>();
    }

    public Chunk createChunk(int startI, int startY, int startZ, Model blockModel) {
        OpenSimplexNoise noise = new OpenSimplexNoise();
        for (int i = startI; i < startI + sizeX; i++)
            for (int j = startY; j < startY + sizeX; j++) {
                float noise_result = (float) (noise.eval(0.03 * i, 0.05 * j) * 12 * Block.side_size);
                blockMap.put(Main.ID, new Block(Block.side_size * i, noise_result, Block.side_size * j,
                        (int) Block.side_size, 10, Main.BLOCK_TYPES[0], blockModel));
                Main.ID++;
                //models.add(new ModelInstance(cube, 4f * i, (float) Noise.noise(0.1 * i, 0.1 * j, 0) * 16,4f * j));    НЕОФИЦИАЛЬНАЯ РЕАЛИЗАЦИЯ ШУМА ПЕРЛИНА
                //models.add(new ModelInstance(cube, 4f * i, (float) NoisePerlin.noise(0.08 * i, 0.08 * j) * 10,4f * j)); ОФИЦИАЛЬНАЯ РЕАЛИЗАЦИЯ ШУМА ПЕРЛИНА
            }
        chunkX = startI / sizeX;
        chunkY = startY / sizeX;
        return this;
    }

    public void drawBlocks(ModelBatch modelBatch, Environment environment) {
        for (Block block : blockMap.values())
            block.draw(modelBatch, environment);
    }

    public Block getBlock(int ID) {
        return blockMap.get(ID);
    }

    public void addBlock(Block block) {
        blockMap.put(block.getID(), block);
    }

    public void removeBlock(int ID) {
        blockMap.remove(ID);
    }
}
