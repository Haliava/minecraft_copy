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

import java.util.Random;

public class CameraControl extends CameraInputController {
    Camera camera;
    Player player;
    Controls controls;
    Hotbar hotbar;
    Vector3 touchedBlock = new Vector3(0, 0, 0);
    float deltaShortT = 0f;
    Model model;
    int indexI, indexY, indexZ;
    int[] rayCoords = new int[] {indexI, indexY, indexZ};
    int tmpX, tmpY, tmpPointer, tmpButton;
    int tmpI, tmpYC, tmpZ;
    boolean longPress = false;

    public CameraControl(GameScreen screen) {
        super(screen.camera);
        this.camera = screen.camera;
        this.player = screen.player;
        this.controls = screen.controls;
        this.hotbar = screen.hotbar;
        this.model = screen.cube;
    }

    public void CheckForLongClick() {
        if (this.isLongPressed(Main.LONG_PRESS_TIME)) {
            longPress = true;
            this.touchDown(tmpX, tmpY, tmpPointer, tmpButton);
        }
    }

    public void multitouch(int touchX, int touchY, boolean isTouched, int pointer) {
        for (int i = 0; i < 3; i++) {
            controls.update(touchX, touchY, isTouched, pointer);
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        tmpX = screenX;
        tmpY = screenY;
        tmpPointer = pointer;
        tmpButton = button;
        HotbarSquare tmpSquare = hotbar.isInsideOfASquare(screenX, screenY);
        System.out.println((controls.isInsideJumpControls(screenX, (Main.HEIGHT - screenY))));
        System.out.println(controls.jumpBounds.x + ", " + controls.jumpBounds.y + ", " + screenX + ", " + (Main.HEIGHT - screenY));
        if (tmpSquare == null && !(controls.isInsideControls(screenX, screenY, controls.circleBounds)) &&
                !(controls.isInsideJumpControls(screenX, Main.HEIGHT - screenY))) {
            super.touchDown(screenX, screenY, pointer, button);
            if (longPress) {
                getCameraRay(screenX, screenY);
            } else deltaShortT += Main.TIME_SCALE;
        } else if (tmpSquare != null) {
            hotbar.selectHotbarSquare(tmpSquare);
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (!(controls.isInsideControls(screenX, screenY, controls.circleBounds)) &&
                !controls.isInsideJumpControls(screenX, Main.HEIGHT - screenY))
            if (deltaShortT < Main.LONG_PRESS_TIME) {
                deltaShortT = 0;
                getCameraRay(screenX, screenY, hotbar);
            }
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
        rayCoords = getBlockCoordsFromRay(ray);
        if (rayCoords == null) return;
        indexI = rayCoords[0]; indexY = rayCoords[1]; indexZ = rayCoords[2];
        if (Main.WORLD_MAP.blockMap[indexI][indexY + 1][indexZ].type == "air") {
            Block initialBlock = Main.WORLD_MAP.blockMap[indexI][indexY][indexZ];
            Main.WORLD_MAP.blockMap[indexI][indexY + 1][indexZ] = new Block(initialBlock.x,
                    initialBlock.y + Block.side_size, initialBlock.z, (int) Block.side_size,
                    10, hotbar.squares[Main.selectedSquareIndex].type, this.model);
        } else if (indexZ + 1 < Chunk.sizeX && Main.WORLD_MAP.blockMap[indexI][indexY][indexZ + 1].type == "air") {
            Block initialBlock = Main.WORLD_MAP.blockMap[indexI][indexY][indexZ];
            Main.WORLD_MAP.blockMap[indexI][indexY][indexZ + 1] = new Block(initialBlock.x,
                    initialBlock.y, initialBlock.z + Block.side_size, (int) Block.side_size,
                    10, hotbar.squares[Main.selectedSquareIndex].type, this.model);
        }
    }

    public void getCameraRay(float x, float y) {
        Ray ray = camera.getPickRay(x, y);
        rayCoords = getBlockCoordsFromRay(ray);
        if (rayCoords == null) return;
        indexI = rayCoords[0]; indexY = rayCoords[1]; indexZ = rayCoords[2];
        try {
            Main.WORLD_MAP.blockMap[indexI][indexY][indexZ].setType("air");
        } catch (ArrayIndexOutOfBoundsException ignored) {
        } finally { longPress = false; }
    }

    public int[] getBlockCoordsFromRay(Ray ray) {
        int resI = -1; int resY = -1; int resZ = -1;
        for (int i = Main.REACH * 2; i > 1; i--) {
            touchedBlock = ray.getEndPoint(camera.direction.cpy(), i * Block.side_size);
            touchedBlock.x /= Block.side_size;
            touchedBlock.y /= Block.side_size;
            touchedBlock.z /= Block.side_size;
            tmpI = (int) Math.floor(touchedBlock.x);
            tmpYC = (int) Math.floor(touchedBlock.y);
            tmpZ = (int) Math.floor(touchedBlock.z);
            try {
                if (Main.WORLD_MAP.blockMap[tmpI][tmpYC][tmpZ].type != "air") {
                    resI = tmpI;
                    resY = tmpYC;
                    resZ = tmpZ;
                }
            } catch (ArrayIndexOutOfBoundsException ignored) { }
        }
        if (resI > -1 && resY > -1 && resZ > -1) return new int[] {resI, resY, resZ};
        else return null;
    }
}
