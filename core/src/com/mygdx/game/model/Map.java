package com.mygdx.game.model;

import java.util.HashMap;

public class Map {
    int sizeX, sizeY;
    HashMap<Integer[], Block> blockMap;

    public Map(int sizeX, int sizeY, Block[] blocks) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < this.sizeX; j++) {
                for (int k = 0; k < this.sizeY; k++) {
                    this.add_block(j, k, 1,blocks[i]); // Честно говоря не знаю что делать с этим ужасным циклом за O(n^3)
                }
            }
        }
    }

    public Map(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    private void add_block(int x, int y, int z, Block block) {
        /*if (this.blockMap.containsKey(new Integer[] {x, y, z})) если надо будет, чтобы новый блок не заменял уже существующий
            System.out.println("Блок уже добавлен");*/
        this.blockMap.put(new Integer[] {x, y, z}, block);
    }
}
