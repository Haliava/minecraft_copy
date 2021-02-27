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
    public boolean keyDown(int keycode) {
        super.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        super.keyUp(keycode);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        multitouch(screenX, Main.HEIGHT - screenY, true, pointer);
        super.touchDown(screenX, screenY, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        multitouch(screenX, Main.HEIGHT - screenY, false, pointer);
        if (screenX > controls.circleBounds.x + controls.circleR && screenY > controls.circleBounds.y + controls.circleR)
            super.touchUp(screenX, screenY, pointer, button);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        multitouch(screenX, Main.HEIGHT - screenY, true, pointer);
        if (screenX > controls.circleBounds.x * 2 && screenY > controls.circleBounds.y)
            super.touchDragged(screenX, screenY, pointer);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        if (screenX > controls.circleBounds.x && screenY > controls.circleBounds.y)
            super.mouseMoved(screenX, screenY);
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
