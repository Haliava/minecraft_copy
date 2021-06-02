package com.mygdx.game.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
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
import java.util.Arrays;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author Haliaven
 * @version 0.0.7
 * @since 2021-02-17
 */
public class GameScreen implements Screen {
    public Hotbar hotbar;
    public PerspectiveCamera camera;
    public Player player;
    public Controls controls;
    public Model cube;
    Texture arrow;
    Rectangle arrowRect;
    Main game;
    ArrayList<int[]> toRender = new ArrayList<>();
    ModelBuilder builder;
    Chunk currentChunk;
    Model playerModel;
    Environment environment;
    CameraControl cameraControl;
    Texture outerCircle, innerCircle, jumpCircle;
    Slider slider;
    Vector2 touchedCoords = new Vector2();

    public GameScreen(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        camera = new PerspectiveCamera(75, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(0f, 12f, 0f);
        camera.near = .1f;
        camera.far = 500f;

        builder = new ModelBuilder();
        cube = Block.createModel(builder);

        /** Добавление и инициплизирование объектов класса {@link Chunk} в {@link com.mygdx.game.model.Map} мира **/
        for (int i = 0; i < Main.WORLD_MAP.sizeX; i++) {
            for (int j = 0; j < Main.WORLD_MAP.sizeY; j++) {
                currentChunk = new Chunk(Chunk.sizeX * i, Chunk.sizeX * j, cube, true);
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

        arrow = new Texture("ar.png");
        arrowRect = new Rectangle(200, Main.HEIGHT - 300, 200, 200);

        hotbar = new Hotbar();
        for (int i = 0; i < 9; i++) {
            TextureRegion tmpRegion = Main.hotbar_atlas.findRegion("square" + (i + 1));
            HotbarSquare tmpSquare = new HotbarSquare(tmpRegion);
            tmpSquare.setType(i == 0 ? i + 1: i);
            hotbar.setSquare(i, tmpSquare);
        }

        slider = new Slider(1, 100, 1, false, new Slider.SliderStyle());
        slider.setBounds(0, 0, 1000, 500);
        slider.setPosition(0, 0);
        slider.setValue(1);

        cameraControl = new CameraControl(this);
        Gdx.input.setInputProcessor(cameraControl);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(135 / 255f, 206 / 255f, 235 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        camera.position.set(player.x, player.y - Block.side_size, player.z);
        camera.update();

        player.getChunkCoords();
        createNearestChunks(player.currentChunkCoordX, player.currentChunkCoordY, Main.RENDER_DISTANCE);

        /** Отрисовка {@link Chunk} в радиуре прорисовки {@link Main.RENDER_DISTANCE} **/
        game.modelBatch.begin(camera);
        for (int[] x: toRender) {
            try {
                Main.WORLD_MAP.get_chunk(x[0], x[1]).drawBlocks(game.modelBatch, environment);
            } catch (ArrayIndexOutOfBoundsException ignored) { }
        }
        game.modelBatch.render(player, environment);
        player.update(controls, delta);
        cameraControl.CheckForLongClick();
        game.modelBatch.end();
        game.spriteBatch.begin();
        game.spriteBatch.draw(arrow, arrowRect.x, arrowRect.y, arrowRect.width, arrowRect.height);
        slider.draw(game.spriteBatch, (float) 0.8);
        slider.updateVisualValue();

        /** Отрисовка {@link HotbarSquare}  **/
        for (int i = 0; i < 9; i++) {
            float x = (float) (Main.WIDTH / 3.3 + Material2D.TEXTURE_2D_SIZE * i);
            if (x == Main.selectedSquareX) Main.selectedSquareIndex = i;
            game.spriteBatch.draw(Material2D.hotbar_squares[i], x, 100);
            hotbar.squares[i].drawInsides(game.spriteBatch);
        }
        game.spriteBatch.draw(Main.selectedSquareTexture, Main.selectedSquareX, 100);
        controls.draw(game.spriteBatch);
        game.spriteBatch.end();

        if (Gdx.input.isTouched()) {
            touchedCoords.set(Gdx.input.getX(), Main.HEIGHT - Gdx.input.getY());
            if (arrowRect.contains(touchedCoords)) {
                game.spriteBatch.dispose();
                game.modelBatch.dispose();
                this.dispose();
                MenuScreen ms = new MenuScreen(game);
                game.activeScreen = ms;
                game.setScreen(ms);
            }
        }
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
        game.modelBatch.dispose();
        game.spriteBatch.dispose();
    }
}
