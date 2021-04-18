package com.mygdx.game.control;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.collision.Ray;
import com.mygdx.game.Main;
import com.mygdx.game.model.Block;
import com.mygdx.game.model.Chunk;
import com.mygdx.game.model.Player;
import com.mygdx.game.utils.BlocksMaterial;
import com.mygdx.game.view.GameScreen;

public class CameraControl extends CameraInputController {
    Camera camera;
    Player player;
    Controls controls;
    Hotbar hotbar;
    Vector3 touchedBlock = new Vector3(0, 0, 0);
    int deltaT = 0;
    Model model;
    int indexI, indexY, indexZ;

    public CameraControl(GameScreen screen) {
        super(screen.camera);
        this.camera = screen.camera;
        this.player = screen.player;
        this.controls = screen.controls;
        this.hotbar = screen.hotbar;
        this.model = screen.cube;
    }

    public void multitouch(int touchX, int touchY, boolean isTouched, int pointer) {
        for (int i = 0; i < 3; i++) {
            controls.update(touchX, touchY, isTouched, pointer);
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        HotbarSquare tmpSquare = hotbar.isInsideOfASquare(screenX, screenY);
        if (tmpSquare == null && !(controls.isInsideControls(screenX, screenY, controls.circleBounds)) &&
                !(controls.isInsideJumpControls(screenX, screenY))) {
            super.touchDown(screenX, screenY, pointer, button);
            getCameraRay(screenX, screenY, hotbar);
        } else if (tmpSquare != null) {
            hotbar.selectHotbarSquare(tmpSquare);
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (!(controls.isInsideControls(screenX, screenY, controls.circleBounds)))
            super.touchUp(screenX, screenY, pointer, button);
        multitouch(screenX, Main.HEIGHT - screenY, false, pointer);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (!(controls.isInsideControls(screenX, screenY, controls.circleBounds)))
            super.touchDragged(screenX, screenY, pointer);
        multitouch(screenX, Main.HEIGHT - screenY, true, pointer);
        return false;
    }

    public void getCameraRay(float x, float y, Hotbar hotbar) {
        Ray ray = camera.getPickRay(x, y);
        // camera.direction.cpy() | controls.direction.cpy()
        /*Vector3 tmp = new Vector3();
        BoundingBox out = new BoundingBox();
        for (int i = (int) ((player.coordX / Block.side_size) - Main.REACH); i < (player.coordX / Block.side_size) + Main.REACH; i++) {
            for (int j = (int) ((player.coordZ / Block.side_size) - Main.REACH); j < (player.coordZ / Block.side_size) + Main.REACH; j++) {
                for (int k = (int) ((player.coordY / Block.side_size) - Main.REACH); k < (player.coordY / Block.side_size) + Main.REACH; k++) {
                    out = Main.WORLD_MAP.blockMap[i][j][k].calculateBoundingBox(out);
                    if (Intersector.intersectRayBounds(ray, out, tmp)) {
                        if (Main.WORLD_MAP.blockMap[i][j][k].type != "air") {
                            Block initialBlock = Main.WORLD_MAP.blockMap[i][j][k];
                            Main.WORLD_MAP.blockMap[i][j + 1][k] = new Block(initialBlock.x,
                                    initialBlock.y + Block.side_size, initialBlock.z, (int) Block.side_size,
                                    10, hotbar.squares[Main.selectedSquareIndex].type, this.model);
                        }
                        return;
                    }
                }
            }
        }*/
        touchedBlock = ray.getEndPoint(camera.direction.cpy(), Main.REACH * Block.side_size);
        touchedBlock.x /= Block.side_size;
        touchedBlock.y /= Block.side_size;
        touchedBlock.z /= Block.side_size;
        try {
            indexI = (int) Math.floor(touchedBlock.x);
            indexY = (int) Math.floor(touchedBlock.y);
            indexZ = (int) Math.floor(touchedBlock.z);
            if (Main.WORLD_MAP.blockMap[indexI][indexY][indexZ].type != "air") {
                if (Main.WORLD_MAP.blockMap[indexI][indexY + 1][indexZ].type == "air") {
                    Block initialBlock = Main.WORLD_MAP.blockMap[indexI][indexY][indexZ];
                    Main.WORLD_MAP.blockMap[indexI][indexY + 1][indexZ] = new Block(initialBlock.x,
                            initialBlock.y + Block.side_size, initialBlock.z, (int) Block.side_size,
                            10, hotbar.squares[Main.selectedSquareIndex].type, this.model);
                }
                //Main.WORLD_MAP.blockMap[indexI][indexY + 1][indexZ].setType(hotbar.squares[Main.selectedSquareIndex].type);
                //Main.WORLD_MAP.chunkMap[(int) (touchedBlock.x / Chunk.sizeX)][(int) (touchedBlock.z / Chunk.sizeX)].setBlockType(indexI, indexY, indexZ, hotbar.squares[Main.selectedSquareIndex].type);
            }
        } catch (ArrayIndexOutOfBoundsException ignored) {}
    }

    public void getCameraRay(float x, float y) {
        Ray ray = camera.getPickRay(x, y);
        touchedBlock = ray.getEndPoint(camera.direction.cpy(), Main.REACH * Block.side_size);
        touchedBlock.x /= Block.side_size;
        touchedBlock.y /= Block.side_size;
        touchedBlock.z /= Block.side_size;
        try {
            Main.WORLD_MAP.blockMap[(int) Math.floor(touchedBlock.x)][(int) Math.floor(touchedBlock.y)][(int) Math.floor(touchedBlock.z)].setType("air");
            Main.WORLD_MAP.blockMap[indexI][indexY][indexZ].setType("air");
        } catch (ArrayIndexOutOfBoundsException ignored) {}
    }
}
