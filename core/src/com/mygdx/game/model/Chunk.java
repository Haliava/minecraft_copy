package com.mygdx.game.model;

public class Chunk {
    int sizeX = 10;
    int sizeY = 10;
    int sizeZ = 1;
    int chunkX;
    int chunkY;
    private java.util.Map<Integer, Block> blockMap;

    public Chunk(java.util.Map<Integer, Block> blockMap, int chunkX, int chunkY) {
        this.blockMap = blockMap;
        this.chunkX = chunkX;
        this.chunkY = chunkY;
    }

    public void drawBlocks() {
        for (Block block: blockMap.values())
            block.draw();
    }

    public Block getBlock(int ID) { return blockMap.get(ID); }

    public void addBlock(Block block) { blockMap.put(block.getID(), block); }

    public void removeBlock(int ID) { blockMap.remove(ID); }
}
