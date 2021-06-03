package com.mygdx.game.model;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.mygdx.game.Main;
import com.mygdx.game.utils.BlocksMaterial;
import com.mygdx.game.utils.NoisePerlin;

import java.util.HashMap;
import java.util.Map;

public class Chunk {
    public static int sizeX = 16;
    public static int sizeY = 256;
    public int chunkX;
    public int chunkY;

    public Chunk(int startI, int startY, Model blockModel, boolean init) {
        this.chunkY = 0;
        this.chunkX = 0;

        NoisePerlin.init();
        if (init) initialise(startI, startY, blockModel);
    }

    /**
     * Генерирует блоки в чанке в интервале
     * @param startI - номер строки, с которой нужно начать генерацию
     * @param startY - номер столбца, с которого нужно начать генерацию
     * @param blockModel - {@link Model} блоков
     */
    public void initialise(int startI, int startY, Model blockModel) {
        chunkX = startI / sizeX;
        chunkY = startY / sizeX;
        for (int i = startI; i < startI + sizeX; i++) {
            for (int j = startY; j < startY + sizeX; j++) {
                float perlin_official = (float) (NoisePerlin.noise(0.08 * i, 0.05 * j) + 1) * 12 * Block.side_size;
                int yCoord = (int) ((perlin_official - (perlin_official % Block.side_size)) / Block.side_size);
                if (yCoord > Main.MAX_HEIGHT) yCoord = Main.MAX_HEIGHT;
                Block block = new Block(Block.side_size * i,
                        yCoord * Block.side_size,
                        Block.side_size * j, (int) Block.side_size, 10, BlocksMaterial.blockTypes[3], blockModel);
                Main.WORLD_MAP.blockMap[i][yCoord][j] = block;
                for (int k = 0; k <= yCoord - 1; k++) {
                    block = new Block(Block.side_size * i,
                            (yCoord - k) * Block.side_size,
                            Block.side_size * j, (int) Block.side_size, 10, BlocksMaterial.blockTypes[3], blockModel);
                    block.isVisible = false; // выключить прорисовку у всех блоков, кроме верхнего пласта
                    Main.WORLD_MAP.blockMap[i][k][j] = block;
                }
                for (int k = yCoord + 1; k < Main.MAX_HEIGHT; k++) {
                    block = new Block(Block.side_size * i,
                            (yCoord - k) * Block.side_size,
                            Block.side_size * j, (int) Block.side_size, 0, BlocksMaterial.blockTypes[0], blockModel);
                    Main.WORLD_MAP.blockMap[i][k][j] = block;
                }
            }
        }
    }

    public void drawBlocks(ModelBatch modelBatch, Environment environment) {
        try {
            for (int i = this.chunkX * sizeX; i < Main.WORLD_MAP.sizeX * Chunk.sizeX; i++)
                for (int j = this.chunkX * sizeX; j < Main.WORLD_MAP.sizeY * Chunk.sizeX; j++)
                    for (int k = 0; k < Main.MAX_HEIGHT; k++)
                        Main.WORLD_MAP.blockMap[i][k][j].draw(modelBatch, environment);
        } catch (NullPointerException ignored) { }
    }

    public void setBlockType(int indexI, int indexY, int indexZ, String type) {
        Main.WORLD_MAP.blockMap[indexI][indexY][indexZ].setType(type);
    }

    public void removeBlock(int indexI, int indexY, int indexZ) {
        Main.WORLD_MAP.blockMap[indexI][indexY][indexZ].setType("air");
    }
}
