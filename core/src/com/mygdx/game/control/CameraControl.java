package com.mygdx.game.control;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.mygdx.game.Main;
import com.mygdx.game.model.Block;
import com.mygdx.game.model.Player;
import com.mygdx.game.view.GameScreen;

public class CameraControl extends CameraInputController {
    Camera camera;
    Player player;
    Controls controls;
    Hotbar hotbar;
    Vector3 touchedBlock = new Vector3(0, 0, 0);

    public CameraControl(GameScreen screen) {
        super(screen.camera);
        this.camera = screen.camera;
        this.player = screen.player;
        this.controls = screen.controls;
        this.hotbar = screen.hotbar;
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
            controls.getCameraRay(screenX, screenY);
        } else if (tmpSquare != null) {
            hotbar.selectHotbarSquare(tmpSquare);
            hotbar.setSelectedBlock(tmpSquare);
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
}
