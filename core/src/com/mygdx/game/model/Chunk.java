package com.mygdx.game.model;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.mygdx.game.Main;

import java.util.HashMap;
import java.util.Map;

public class Chunk {
    public static int sizeX = 16;
    public static int sizeZ = 16;
    public int chunkX;
    public int chunkY;
    private Map<String, Block> blockMap;
    public java.util.Map<Integer, Block> coordsBlockMap;

    public Chunk(Map<String, Block> blockMap, int chunkX, int chunkY) {
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
        /*for (int i = startI; i < startI + sizeX; i++) {
            for (int j = startY; j < startY + sizeX; j++) {
                for (int k = 0; k < sizeZ; k++) {
                    float perlin_official = (float) NoisePerlin.noise(0.08 * i, 0.05 * j) * 12 * Block.side_size;
                }
            }
        }*/

        int counterX = 0, counterY = 0, counterZ = 0;
        Model voidModel = new Model();
        chunkX = startI / sizeX;
        chunkY = startY / sizeX;
        OpenSimplexNoise noise = new OpenSimplexNoise();
        for (int i = startI; i < startI + sizeX; i++) {
            for (int j = startY; j < startY + sizeX; j++) {
                float noise_result = (float) (noise.eval(0.03 * i, 0.05 * j) * 12 * Block.side_size); // OPENSIMPLEX NOISE
                float perlin_unofficial = (float) (Noise.noise(0.03 * i, 0.05 * j, 0.05 * (i + j)) * 12 * Block.side_size); // НЕОФИЦИАЛЬНАЯ РЕАЛИЗАЦИЯ ШУМА ПЕРЛИНА
                float perlin_official = (float) NoisePerlin.noise(0.08 * i, 0.05 * j) * 12 * Block.side_size; // ОФИЦИАЛЬНАЯ РЕАЛИЗАЦИЯ ШУМА ПЕРЛИНА
                System.out.println(perlin_official);
                Block block = new Block(Block.side_size * i, perlin_official - (perlin_official % Block.side_size), Block.side_size * j,
                        (int) Block.side_size, 10, Main.BLOCK_TYPES[1], blockModel);
                blockMap.put(Main.ID, block);
                coordsBlockMap.put(Math.abs((int) Block.side_size * ((int) (Block.side_size / ((perlin_official - (perlin_official % Block.side_size)) + 1)))), block);
                Main.ID = chunkX + "" + chunkY + "" + counterX + "" + counterY + "" + counterZ;
                counterX++;
                for (int k = -sizeZ; k < sizeZ; k++) {
                    if (k * Block.side_size < block.y) {
                        blockMap.put(Main.ID, createBlock(i, j, k, perlin_official, 1, 0, Main.BLOCK_TYPES[1], voidModel));
                    } else if (k * Block.side_size > block.y && k * Block.side_size > Main.MIN_HEIGHT / Block.side_size) {
                        blockMap.put(Main.ID, createBlock(i, j, k, perlin_official, -1, 0, Main.BLOCK_TYPES[0], blockModel));
                    }
                    Main.ID = chunkX + "" + chunkY + "" + counterX + "" + counterY + "" + counterZ;
                    counterY++;
                }
            }
            counterZ++;
        }
    }

    public Block createBlock(int i, int j, int k, float noise_result, int mult, int health, String type, Model blockModel) {
        return new Block(Block.side_size * i, noise_result - (noise_result % Block.side_size)
                + k * Block.side_size * mult, Block.side_size * j, (int) Block.side_size, health, type, blockModel);
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
