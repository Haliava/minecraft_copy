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
import com.badlogic.gdx.graphics.g3d.RenderableProvider;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Predicate;
import com.mygdx.game.model.Block;
import com.mygdx.game.model.Map;
import com.mygdx.game.model.Noise;
import com.mygdx.game.model.NoisePerlin;
import com.mygdx.game.model.OpenSimplexNoise;

import java.util.ArrayList;

public class GameScreen implements Screen {
    int map_width;
    int map_length;
    Map map;
    public PerspectiveCamera camera;
    private ModelBatch modelBatch;
    private ArrayList<ModelInstance> models;
    private Model cube;
    private Environment environment;
    private CameraInputController cameraInputController;

    @Override
    public void show() {
        map_width = 30;
        map_length = 30;
        camera = new PerspectiveCamera(75, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(0f, 16f, 0f);
        camera.lookAt(12f, 12f, 12f);
        camera.near = .1f;
        camera.far = 300f;

        models = new ArrayList<>();
        modelBatch = new ModelBatch();
        cube = Block.createModel(Block.side_size, Block.side_size, Block.side_size);
        map = new Map(map_width, map_length);

        OpenSimplexNoise noise = new OpenSimplexNoise();
        for (int i = -map_length; i < map_length; i++)
            for (int j = -map_width; j < map_width; j++) {
                float noise_result = (float) noise.eval(0.04 * i, 0.04 * j) * 24;
                ModelInstance instance = new ModelInstance(cube, 4f * i, noise_result, 4f * j);
                models.add(instance);
                map.add_block(new Block(Block.side_size * i, noise_result, Block.side_size * j, 16, 10, cube));
                //models.add(new ModelInstance(cube, 4f * i, (float) Noise.noise(0.1 * i, 0.1 * j, 0) * 16,4f * j));     НЕОФИЦИАЛЬНАЯ РЕАЛИЗАЦИЯ ШУМА ПЕРЛИНА
                //models.add(new ModelInstance(cube, 4f * i, (float) NoisePerlin.noise(0.08 * i, 0.08 * j) * 10,4f * j)); ОФИЦИАЛЬНАЯ РЕАЛИЗАЦИЯ ШУМА ПЕРЛИНА
            }

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.8f, 0.8f, 0.8f, 1));
        environment.add(new DirectionalLight().set(0.7f, 0.7f, 0.7f, -1f, -0.8f, -0.2f));

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
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        camera.update();
        modelBatch.begin(camera);
        modelBatch.render(map.blockMap, environment);
        modelBatch.render(models, environment);
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
