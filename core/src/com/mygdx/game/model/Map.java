package com.mygdx.game.model;
import java.util.ArrayList;


public class Map {
    public int sizeX, sizeY;
    public ArrayList<Block> blockMap;

    public Map(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        blockMap = new ArrayList<>();
    }

    public void fill_with_blocks(Block[] blocks) {
        for (int i = 0; i < this.sizeY; i++) {
            for (int j = 0; j < this.sizeX; j++) {
                this.add_block(blocks[i]);
            }
        }
    }

    public void add_block(Block block) {
        if (!this.blockMap.contains(block))
            this.blockMap.add(block);
    }
}
