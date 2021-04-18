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
    public int chunkX;
    public int chunkY;
    private Block[][][] bMap;

    public Chunk(int startI, int startY, Block[][][] map, Model blockModel) {
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
                Block block = new Block(Block.side_size * i,
                        yCoord * Block.side_size,
                        Block.side_size * j, (int) Block.side_size, 10, BlocksMaterial.blockTypes[2], blockModel);
                bMap[i][yCoord][j] = block;
                Main.WORLD_MAP.blockMap[i][yCoord][j] = block;
                for (int k = yCoord - 1; k >= 0; k--) {
                    block = new Block(Block.side_size * i,
                            (yCoord - k) * Block.side_size,
                            Block.side_size * j, (int) Block.side_size, 10, BlocksMaterial.blockTypes[2], blockModel);
                    if (k != yCoord - 2) block.isVisible = false; // выключить прорисовку у всех блоков, кроме 2 пластов блоков
                    bMap[i][k][j] = block;
                    Main.WORLD_MAP.blockMap[i][k][j] = block;
                }
                for (int k = yCoord + 1; k < Main.MAX_HEIGHT; k++) {
                    block = new Block(Block.side_size * i,
                            (yCoord - k) * Block.side_size,
                            Block.side_size * j, (int) Block.side_size, 0, BlocksMaterial.blockTypes[0], blockModel);
                    bMap[i][k][j] = block;
                    Main.WORLD_MAP.blockMap[i][k][j] = block;
                }
            }
        }
    }

    public void drawBlocks(ModelBatch modelBatch, Environment environment) {
        for (int i = this.chunkX * sizeX; i < Main.WORLD_MAP.sizeX * Chunk.sizeX; i++)
            for (int j = this.chunkX * sizeX; j < Main.MAX_HEIGHT; j++)
                for (int k = 0; k < Main.WORLD_MAP.sizeY * Chunk.sizeX; k++)
                    Main.WORLD_MAP.blockMap[i][j][k].draw(modelBatch, environment);

        for (Block[][] block: bMap)
            for (Block[] block2: block)
                for (Block block3: block2) block3.draw(modelBatch, environment);
    }

    public void setBlockType(int indexI, int indexY, int indexZ, String type) {
        this.bMap[indexI][indexY][indexZ].setType(type);
    }

    public void removeBlock(int indexI, int indexY, int indexZ) {
        this.bMap[indexI][indexY][indexZ].setType("air");
    }
}
