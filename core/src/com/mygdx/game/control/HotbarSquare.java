package com.mygdx.game.control;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Main;
import com.mygdx.game.utils.BlocksMaterial;
import com.mygdx.game.utils.Material2D;


public class HotbarSquare {
    public int x;
    public int y;
    public Rectangle boundingBox;
    public Material posessedMaterial = BlocksMaterial.listOfMaterials[3];

    public HotbarSquare(int x, int y) {
        this.boundingBox = new Rectangle(x, y, Material2D.TEXTURE_2D_SIZE, Material2D.TEXTURE_2D_SIZE);
    }

    public HotbarSquare(TextureRegion textureRegion) {
        this.boundingBox = new Rectangle(textureRegion.getRegionX(), 100, Material2D.TEXTURE_2D_SIZE, Material2D.TEXTURE_2D_SIZE);
        this.x = Main.WIDTH / 3 +  (int) boundingBox.x;
        this.y = Main.HEIGHT - (int) boundingBox.y;
    }

    public boolean isTouched(int touchX, int touchY) {
        return (touchX <= this.x + Material2D.TEXTURE_2D_SIZE && touchX >= this.x) &&
                (touchY >= this.y - Material2D.TEXTURE_2D_SIZE && touchY <= this.y);
    }

    public void setMaterial(Material material) {
        this.posessedMaterial = material;
    }
}
