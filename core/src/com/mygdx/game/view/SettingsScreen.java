package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.mygdx.game.Main;

public class SettingsScreen implements Screen {
    Vector2 touchCoords = new Vector2();
    Main game;
    Rectangle arrowRect, backgroundRect;
    SpriteBatch batch;
    Texture arrow, background;
    Stage stage;
    TextField textField;
    BitmapFont font;
    FreeTypeFontGenerator generator;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    public SettingsScreen(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        arrow = new Texture("ar.png");
        arrowRect = new Rectangle(100, 100, 200, 200);

        background = new Texture("bg.png");
        backgroundRect = new Rectangle(0, 0, Main.WIDTH, Main.HEIGHT);

        generator = new FreeTypeFontGenerator(Gdx.files.internal("font.TTF"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 96;
        font = generator.generateFont(parameter);
        font.setColor(Color.BLACK);
        generator.dispose();

        batch = new SpriteBatch();

        stage = new Stage();
        textField = new TextField("", Main.skin);
        textField.setPosition(Main.WIDTH / 2 - Main.WIDTH / 10, Main.HEIGHT / 2 - Main.HEIGHT / 16);
        textField.setSize(Main.WIDTH / 5, Main.HEIGHT / 8);
        stage.addActor(textField);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(135 / 255f, 206 / 255f, 235 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        batch.begin();
        batch.draw(background, backgroundRect.x, backgroundRect.y, backgroundRect.width, backgroundRect.height);
        font.draw(batch, "SEED", Main.WIDTH / 2 - Main.WIDTH / 4, Main.HEIGHT / 2 + Main.HEIGHT / 24);
        batch.draw(arrow, arrowRect.x, arrowRect.y, arrowRect.width, arrowRect.height);
        stage.draw();
        stage.act();
        batch.end();

        if (Gdx.input.isTouched()) {
            touchCoords.set(Gdx.input.getX(), Main.HEIGHT - Gdx.input.getY());
            if (arrowRect.contains(touchCoords)) {
                this.dispose();
                String text = textField.getText();
                if (text != "")
                    if (text.length() <= 9) Main.SEED = Integer.parseInt(text);
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
    public void dispose() { }
}
