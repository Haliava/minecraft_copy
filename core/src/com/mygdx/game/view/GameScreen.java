package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Main;
import com.mygdx.game.control.CameraControl;
import com.mygdx.game.control.Controls;
import com.mygdx.game.control.Hotbar;
import com.mygdx.game.control.HotbarSquare;
import com.mygdx.game.model.Block;
import com.mygdx.game.model.Chunk;
import com.mygdx.game.model.Player;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.utils.BlocksMaterial;
import com.mygdx.game.utils.Material2D;

import java.util.ArrayList;

public class GameScreen implements Screen {
    public Hotbar hotbar;
    public PerspectiveCamera camera;
    public Player player;
    public Controls controls;
    ArrayList<int[]> toRender = new ArrayList<>();
    Model debugCube;
    ModelInstance debugInst;
    ModelBatch modelBatch;
    SpriteBatch spriteBatch;
    ModelBuilder builder;
    public Model cube;
    Model playerModel;
    Environment environment;
    CameraInputController cameraInputController;
    CameraControl cameraControl;
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
        debugCube = builder.createBox(Block.side_size, Block.side_size, Block.side_size, BlocksMaterial.listOfMaterials[3],
                (VertexAttributes.Usage.Position|VertexAttributes.Usage.Normal|VertexAttributes.Usage.TextureCoordinates));
        debugInst = new ModelInstance(debugCube, 10 * Block.side_size, (Main.MIN_HEIGHT + 7) * Block.side_size, 10 * Block.side_size);

        Main.WORLD_MAP.initialiseBlockMap(cube);
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

        hotbar = new Hotbar();
        for (int i = 0; i < 9; i++) {
            TextureRegion tmpRegion = Main.hotbar_atlas.findRegion("square" + (i + 1));
            hotbar.setSquare(i, new HotbarSquare(tmpRegion));
        }

        cameraControl = new CameraControl(this);
        cameraInputController = new CameraInputController(camera);
        //Gdx.input.setInputProcessor(cameraInputController);
        Gdx.input.setInputProcessor(cameraControl);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(135 / 255f, 206 / 255f, 235 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        camera.position.set(player.x, player.y - Block.side_size, player.z);
        debugInst.transform.translate(player.velocityX, 0, player.velocityZ);
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
        modelBatch.render(debugInst, environment);
        player.update(controls, delta);
        cameraControl.CheckForLongClick();
        modelBatch.end();
        spriteBatch.begin();
        for (int i = 0; i < 9; i++) {
            float x = (float) (Main.WIDTH / 3.3 + Material2D.TEXTURE_2D_SIZE * i);
            if (x == Main.selectedSquareX) Main.selectedSquareIndex = i;
            spriteBatch.draw(Material2D.hotbar_squares[i], x, 100);
            hotbar.squares[i].drawInsides(spriteBatch);
        }
        spriteBatch.draw(Main.selectedSquareTexture, Main.selectedSquareX, 100);
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
