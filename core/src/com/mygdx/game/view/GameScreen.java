package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.mygdx.game.model.Block;
import com.mygdx.game.model.Map;
import com.mygdx.game.model.Noise;

import java.util.ArrayList;

public class GameScreen implements Screen {
    int map_width;
    int map_length;
    Map map;
    private PerspectiveCamera camera;
    private ModelBatch modelBatch;
    private ArrayList<ModelInstance> models;
    private Model cube;
    private Environment environment;
    private CameraInputController cameraInputController;

    @Override
    public void show() {
        map_width = 50;
        map_length = 50;
        camera = new PerspectiveCamera(75, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(0f, 0f, 5f);
        camera.lookAt(0f, 0f, 0f);
        camera.near = .1f;
        camera.far = 300f;

        models = new ArrayList<>();
        modelBatch = new ModelBatch();
        cube = Block.createModel(2f, 2f, 2f);
        map = new Map(map_width, map_length);

        for (int i = 0; i < map_length; i++)
            for (int j = 0; j < map_width; j++) {
                map.add_block(new Block(2f * i,
                        (float) Noise.noise(i * 1.5f, j * 1.8f, (i + j) * 1.5f) * 2,
                        2f * j, 16, 20, cube));
                models.add(new ModelInstance(cube, 2f * i, (float) Noise.noise(i * 1.5f, j * 1.8f, (i + j) * 1.5f) * 2, 2f * j));
            }

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.8f, 0.8f, 0.8f, 1));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        cameraInputController = new CameraInputController(camera);
        cameraInputController.forwardKey = Input.Keys.UP;
        cameraInputController.backwardKey = Input.Keys.DOWN;
        cameraInputController.rotateRightKey = Input.Keys.RIGHT;
        cameraInputController.rotateLeftKey = Input.Keys.LEFT;
        Gdx.input.setInputProcessor(cameraInputController);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);

        camera.update();
        modelBatch.begin(camera);
        modelBatch.render(map.blockMap, environment);
        //modelBatch.render(models, environment);
        modelBatch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        modelBatch.dispose();
    }
}
