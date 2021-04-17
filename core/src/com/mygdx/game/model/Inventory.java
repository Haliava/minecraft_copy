package com.mygdx.game.model;

import com.badlogic.gdx.graphics.Texture;

import java.util.Arrays;

public class Inventory extends Item2d {
    int row, col;
    Item[][] contents;
    Item selected_item;

    public Inventory(int x, int y, int sizeX, int sizeY, Texture texture, int row, int col) {
        super(x, y, sizeX, sizeY, texture);
        this.row = row;
        this.col = col;
        this.contents = new Item[this.row][this.col];
        this.selected_item = this.contents[0][0];
        this.clear();
    }

    private void clear() {
        Arrays.fill(this.contents, null);
    }

    private void add_item(int row, int col, Item item) {
        if (this.contents[row][col] == null)
            this.contents[row][col] = item;
    }

    private void remove_item(int row, int col) {
        this.contents[row][col] = null;
    }

    private void select_item(int row, int col) {
        this.selected_item = this.contents[row][col];
    }

    private Item get_selected_item() {
        return this.selected_item;
    }
}
