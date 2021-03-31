package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Main;
import com.mygdx.game.control.CameraControl;
import com.mygdx.game.control.Controls;
import com.mygdx.game.model.Block;
import com.mygdx.game.model.Chunk;
import com.mygdx.game.model.Map;
import com.mygdx.game.model.Player;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class GameScreen implements Screen {
    ArrayList<int[]> toRender = new ArrayList<>();
    Vector3 temp = new Vector3();
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
    Texture outerCircle, innerCircle, jumpCircle;

    @Override
    public void show() {
        camera = new PerspectiveCamera(75, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(0f, 12f, 0f);
        camera.near = .1f;
        camera.far = 500f;

        modelBatch = new ModelBatch();
        spriteBatch = new SpriteBatch();
        builder = new ModelBuilder();
        cube = Block.createModel(builder);

        Main.WORLD_MAP.initialiseBlockMap();
        for (int i = 0; i < Main.MAP_WIDTH; i++) {
            for (int j = 0; j < Main.MAP_WIDTH; j++) {
                Chunk currentChunk = new Chunk(Chunk.sizeX * i, Chunk.sizeX * j, Main.WORLD_MAP.blockMap, cube);
                Main.WORLD_MAP.add_chunk(currentChunk);
            }
        }

        playerModel = Player.createModel(0f, 0f, 0f, builder);
        player = new Player(10 * Block.side_size, (Main.MIN_HEIGHT + 15) * Block.side_size, 10 * Block.side_size,
                new int[]{(int) Block.side_size, (int) Block.side_size * 2}, 10f, playerModel);

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.8f, 0.8f, 0.8f, 1));
        environment.add(new DirectionalLight().set(0.7f, 0.7f, 0.7f, -1f, -0.8f, -0.2f));

        outerCircle = new Texture("outer.png");
        innerCircle = new Texture("inner.png");
        jumpCircle = new Texture("inner.png");
        controls = new Controls(outerCircle, innerCircle, jumpCircle, new Vector2(300, 300), Main.HEIGHT / 3, camera);

        cameraControl = new CameraControl(camera, player, controls);
        cameraInputController = new CameraInputController(camera);
        //Gdx.input.setInputProcessor(cameraInputController);
        Gdx.input.setInputProcessor(cameraControl);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(135 / 255f, 206 / 255f, 235 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        camera.position.set(player.x, player.y, player.z);
        camera.update();

        player.getChunkCoords();
        createNearestChunks(player.currentChunkCoordX, player.currentChunkCoordY, Main.RENDER_DISTANCE);

        modelBatch.begin(camera);
        for (int[] x: toRender) {
            try {
                Main.WORLD_MAP.get_chunk(x[0], x[1]).drawBlocks(modelBatch, environment);
            } catch (ArrayIndexOutOfBoundsException ignored) { }
        }
        modelBatch.render(player, environment);
        player.update(controls, delta);
        modelBatch.end();
        spriteBatch.begin();
        controls.draw(spriteBatch);
        spriteBatch.end();
    }

    public void createNearestChunks(int coordX, int coordY, int renderDistance) {
        toRender.clear();
        for (int i = -(renderDistance / 2); i <= (renderDistance / 2); i++) {
            for (int j = -(renderDistance / 2); j <= (renderDistance / 2); j++) {
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
