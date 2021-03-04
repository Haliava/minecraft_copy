package com.mygdx.game.control;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.mygdx.game.Main;
import com.mygdx.game.model.Player;

public class CameraControl extends CameraInputController implements InputProcessor {
    Camera camera;
    Player player;
    Controls controls;

    public CameraControl(Camera camera, Player player, Controls controls) {
        super(camera);
        this.camera = camera;
        this.player = player;
        this.controls = controls;
    }

    public void multitouch(int touchX, int touchY, boolean isTouched, int pointer) {
        for (int i = 0; i < 2; i++) {
            controls.update(touchX, touchY, isTouched, pointer);
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (!(controls.isInsideControls(screenX, screenY)))
            super.touchDown(screenX, screenY, pointer, button);
        multitouch(screenX, Main.HEIGHT - screenY, true, pointer);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (!(controls.isInsideControls(screenX, screenY)))
            super.touchUp(screenX, screenY, pointer, button);
        multitouch(screenX, Main.HEIGHT - screenY, false, pointer);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (!(controls.isInsideControls(screenX, screenY)))
            super.touchDragged(screenX, screenY, pointer);
        multitouch(screenX, Main.HEIGHT - screenY, true, pointer);
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
