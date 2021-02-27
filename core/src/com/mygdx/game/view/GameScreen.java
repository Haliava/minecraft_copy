package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Predicate;
import com.mygdx.game.Main;
import com.mygdx.game.control.CameraControl;
import com.mygdx.game.control.Controls;
import com.mygdx.game.model.Block;
import com.mygdx.game.model.Map;
import com.mygdx.game.model.Noise;
import com.mygdx.game.model.NoisePerlin;
import com.mygdx.game.model.OpenSimplexNoise;
import com.mygdx.game.model.Player;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

import javax.xml.transform.Result;

public class GameScreen implements Screen {
    int map_width;
    int map_length;
    Map map;
    PerspectiveCamera camera;
    ModelBatch modelBatch;
    SpriteBatch spriteBatch;
    ModelBuilder builder;
    Model cube;
    Model playerModel;
    Player player;
    Environment environment;
    CameraInputController cameraInputController;
    CameraControl cameraControl;
    Controls controls;
    Texture outerCircle, innerCircle;
    Vector3 temp = new Vector3();

    @Override
    public void show() {
        map_width = 10;
        map_length = 10;
        camera = new PerspectiveCamera(75, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(0f, 16f, 0f);
        camera.lookAt(-18f, 5f, 12f);
        camera.near = .1f;
        camera.far = 150f;

        modelBatch = new ModelBatch();
        spriteBatch = new SpriteBatch();
        builder = new ModelBuilder();
        cube = Block.createModel(Block.side_size, Block.side_size, Block.side_size, builder);
        map = new Map(map_width, map_length);

        OpenSimplexNoise noise = new OpenSimplexNoise();
        for (int i = -map_length; i < map_length; i++)
            for (int j = -map_width; j < map_width; j++) {
                float noise_result = (float) noise.eval(0.04 * i, 0.04 * j) * 24;
                map.add_block(new Block(Block.side_size * i, noise_result, Block.side_size * j, 16, 10, cube));
                //models.add(new ModelInstance(cube, 4f * i, (float) Noise.noise(0.1 * i, 0.1 * j, 0) * 16,4f * j));     НЕОФИЦИАЛЬНАЯ РЕАЛИЗАЦИЯ ШУМА ПЕРЛИНА
                //models.add(new ModelInstance(cube, 4f * i, (float) NoisePerlin.noise(0.08 * i, 0.08 * j) * 10,4f * j)); ОФИЦИАЛЬНАЯ РЕАЛИЗАЦИЯ ШУМА ПЕРЛИНА
            }
        playerModel = Player.createModel(4f, 8f, 12f, builder);
        player = new Player(0, 12, 0, new int[]{(int) Block.side_size, (int) Block.side_size * 2}, 10f, playerModel);

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.8f, 0.8f, 0.8f, 1));
        environment.add(new DirectionalLight().set(0.7f, 0.7f, 0.7f, -1f, -0.8f, -0.2f));

        outerCircle = new Texture("outer.png");
        innerCircle = new Texture("inner.png");
        controls = new Controls(outerCircle, innerCircle, new Vector2(300, 300), Main.HEIGHT / 3);

        cameraControl = new CameraControl(camera, null, controls);
        cameraInputController = new CameraInputController(camera);
        //Gdx.input.setInputProcessor(cameraInputController);
        Gdx.input.setInputProcessor(cameraControl);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(135 / 255f, 206 / 255f, 235 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        camera.position.set((float) player.x, (float) player.y, (float) player.z);

        camera.update();
        modelBatch.begin(camera);
        modelBatch.render(map.blockMap, environment);
        modelBatch.render(player, environment);
        player.update(controls);
        modelBatch.end();
        spriteBatch.begin();
        controls.draw(spriteBatch);
        spriteBatch.end();
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
