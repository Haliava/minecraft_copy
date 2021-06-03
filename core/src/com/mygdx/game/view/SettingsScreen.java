package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.mygdx.game.Main;

public class SettingsScreen implements Screen {
    Vector2 touchCoords = new Vector2();
    Main game;
    Rectangle arrowRect, backgroundRect;
    SpriteBatch batch;
    Texture arrow, background, button;
    Stage stage;
    TextField seedField, renderDistanceField, fovField;
    TextField.TextFieldStyle style;
    BitmapFont font;
    FreeTypeFontGenerator generator;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    public SettingsScreen(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        generator = new FreeTypeFontGenerator(Gdx.files.internal("font.TTF"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 96;
        font = generator.generateFont(parameter);
        font.setColor(Color.BLACK);
        generator.dispose();

        style = new TextField.TextFieldStyle(font, Color.BLACK, null, null, new Drawable() {
            @Override
            public void draw(Batch batch, float x, float y, float width, float height) {
                batch.draw(button, x, y, Main.WIDTH / 5, Main.HEIGHT / 8);
            }

            @Override
            public float getLeftWidth() { return 0; }

            @Override
            public void setLeftWidth(float leftWidth) { }

            @Override
            public float getRightWidth() { return 0; }

            @Override
            public void setRightWidth(float rightWidth) { }

            @Override
            public float getTopHeight() { return 0; }

            @Override
            public void setTopHeight(float topHeight) { }

            @Override
            public float getBottomHeight() { return 0; }

            @Override
            public void setBottomHeight(float bottomHeight) { }

            @Override
            public float getMinWidth() { return 0; }

            @Override
            public void setMinWidth(float minWidth) { }

            @Override
            public float getMinHeight() { return 0; }

            @Override
            public void setMinHeight(float minHeight) { }
        });

        arrow = new Texture("ar.png");
        arrowRect = new Rectangle(100, 100, 200, 200);

        background = new Texture("bg.png");
        backgroundRect = new Rectangle(0, 0, Main.WIDTH, Main.HEIGHT);

        button = new Texture("but.png");

        batch = new SpriteBatch();

        stage = new Stage();
        seedField = new TextField("", Main.skin);
        seedField.setPosition(Main.WIDTH / 2 - Main.WIDTH / 10, Main.HEIGHT / 2);
        seedField.setSize(Main.WIDTH / 5, Main.HEIGHT / 8);
        seedField.setStyle(style);
        seedField.setText(String.valueOf(Main.SEED));
        stage.addActor(seedField);

        renderDistanceField = new TextField("", Main.skin);
        renderDistanceField.setPosition(Main.WIDTH / 2 + Main.WIDTH / 10, Main.HEIGHT / 2 - Main.HEIGHT / 6);
        renderDistanceField.setSize(Main.WIDTH / 10, Main.HEIGHT / 8);
        renderDistanceField.setStyle(style);
        renderDistanceField.setText(String.valueOf(Main.RENDER_DISTANCE));
        stage.addActor(renderDistanceField);

        fovField = new TextField("", Main.skin);
        fovField.setPosition(Main.WIDTH / 2 - Main.WIDTH / 10, Main.HEIGHT / 2 + Main.HEIGHT / 5);
        fovField.setSize(Main.WIDTH / 5, Main.HEIGHT / 8);
        fovField.setStyle(style);
        fovField.setText(String.valueOf(Main.FOV));
        stage.addActor(fovField);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(135 / 255f, 206 / 255f, 235 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        batch.begin();
        batch.draw(background, backgroundRect.x, backgroundRect.y, backgroundRect.width, backgroundRect.height);
        font.draw(batch, "FOV", Main.WIDTH / 2 - Main.WIDTH / 4, Main.HEIGHT - Main.HEIGHT / 5);
        font.draw(batch, "SEED", Main.WIDTH / 2 - Main.WIDTH / 4, Main.HEIGHT / 2 + Main.HEIGHT / 10);
        font.draw(batch, "RENDER DISTANCE", Main.WIDTH / 10 + 50, Main.HEIGHT / 2 - Main.HEIGHT / 14);
        batch.draw(arrow, arrowRect.x, arrowRect.y, arrowRect.width, arrowRect.height);
        stage.draw();
        stage.act();
        batch.end();

        if (Gdx.input.isTouched()) {
            touchCoords.set(Gdx.input.getX(), Main.HEIGHT - Gdx.input.getY());
            if (arrowRect.contains(touchCoords)) {
                String seed = seedField.getText();
                String dist = renderDistanceField.getText();
                String fov = fovField.getText();
                if (seed != "")
                    if (seed.length() <= 9) Main.SEED = Integer.parseInt(seed);
                if (dist != "")
                    if (dist.length() <= 9) Main.RENDER_DISTANCE = Integer.parseInt(dist);
                if (fov != "")
                    if (fov.length() <= 3) Main.FOV = Integer.parseInt(fov);
                this.dispose();
                MenuScreen ms = new MenuScreen(game);
                game.activeScreen = ms;
                game.setScreen(ms);
            }
        }
    }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() { stage.dispose(); }
}
