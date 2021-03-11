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
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.DebugDrawer;
import com.badlogic.gdx.physics.bullet.collision.*;
import com.badlogic.gdx.physics.bullet.collision.btDispatcher;
import com.badlogic.gdx.physics.bullet.linearmath.btIDebugDraw;
import com.badlogic.gdx.utils.Predicate;
import com.mygdx.game.Main;
import com.mygdx.game.control.CameraControl;
import com.mygdx.game.control.Controls;
import com.mygdx.game.model.Block;
import com.mygdx.game.model.Chunk;
import com.mygdx.game.model.Map;
import com.mygdx.game.model.Noise;
import com.mygdx.game.model.NoisePerlin;
import com.mygdx.game.model.OpenSimplexNoise;
import com.mygdx.game.model.Player;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.xml.transform.Result;

public class GameScreen implements Screen {
    int map_width;
    int map_length;
    ArrayList<int[]> toRender = new ArrayList<>();
    HashMap<int[], Chunk> chunkCoords;
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
    DebugDrawer debugDrawer;

    @Override
    public void show() {
        map_width = 16;
        map_length = 16;
        camera = new PerspectiveCamera(75, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(0f, 16f, 0f);
        camera.lookAt(-18f, 5f, 12f);
        camera.near = .1f;
        camera.far = 500f;

        modelBatch = new ModelBatch();
        spriteBatch = new SpriteBatch();
        builder = new ModelBuilder();
        cube = Block.createModel(Block.side_size, Block.side_size, Block.side_size, builder);
        map = new Map(map_width, map_length);
        chunkCoords = new HashMap<>();

        for (int i = -2; i < 4; i++) {
            for (int j = -2; j < 4; j++) {
                Chunk currentChunk = new Chunk().createChunk(
                        Chunk.sizeX * i, Chunk.sizeX * j, 0, cube
                );
                chunkCoords.put(new int[] {currentChunk.chunkX, currentChunk.chunkY}, currentChunk);
                map.add_chunk(currentChunk);
            }
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

        //debugDrawer = new DebugDrawer();
        //debugDrawer.setDebugMode(btIDebugDraw.DebugDrawModes.DBG_MAX_DEBUG_DRAW_MODE);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(135 / 255f, 206 / 255f, 235 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        camera.position.set(player.x, player.y, player.z);
        int currentChunkCoordX = (int) Math.ceil(player.x / Chunk.sizeX);
        int currentChunkCoordY = (int) Math.ceil(player.z / Chunk.sizeX);
        createNearestChunks(currentChunkCoordX, currentChunkCoordY);

        camera.update();
        modelBatch.begin(camera);
        /*for (Chunk chunk: map.chunkMap) {
            chunk.drawBlocks(modelBatch, environment);
        }*/
        for (int[] y: chunkCoords.keySet()) {
            System.out.println(Arrays.toString(y) + "123123123123123");
        }
        for (int[] x: toRender) {
            System.out.println(Arrays.toString(x));
            chunkCoords.get(x).drawBlocks(modelBatch, environment);
        }
        modelBatch.render(player, environment);
        player.update(controls);
        modelBatch.end();
        spriteBatch.begin();
        controls.draw(spriteBatch);
        spriteBatch.end();
    }

    public void createNearestChunks(int coordX, int coordY) {
        toRender.clear();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                toRender.add(new int[] {coordX + i, coordY + j});
            }
        }
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
        spriteBatch.dispose();
    }
}
