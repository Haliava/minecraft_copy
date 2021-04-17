package com.mygdx.game.control;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.mygdx.game.Main;
import com.mygdx.game.model.Block;

public class Hotbar {
    public HotbarSquare[] squares;

    public Hotbar(HotbarSquare[] inp) {
        this.squares = inp;
    }

    public Hotbar() {
        this.squares = new HotbarSquare[9];
    }

    public HotbarSquare isInsideOfASquare(int touchX, int touchY) {
        for (HotbarSquare sq: this.squares)
            if (sq.isTouched(touchX, touchY)) return sq;
        return null;
    }

    public void setSquare(int index, HotbarSquare sq) {
        this.squares[index] = sq;
    }

    public void selectHotbarSquare(HotbarSquare square) {
        Main.selectedSquareX = square.x;
    }

    public void setSelectedBlock(HotbarSquare square) {
        //this.squares[1].texture = square.texture;
    }
}
