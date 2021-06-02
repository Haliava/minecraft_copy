package com.mygdx.game.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Main;

import java.awt.Font;

import sun.font.TrueTypeFont;

public class MenuScreen implements Screen {
    Main game;
    Rectangle playButtonRect, settingsButtonRect, exitButtonRect, backgroundRect;
    SpriteBatch batch;
    BitmapFont font;
    FreeTypeFontGenerator generator;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    Texture playButton, settingsButton, exitButton, background;
    Vector2 touchCoords = new Vector2();

    public MenuScreen(Main game) {
        this.game = game;
    }

    @Override
    public void show() {
        playButton = new Texture("but.png");
        playButtonRect = new Rectangle(500, Main.HEIGHT - 275, Main.WIDTH / 2, Main.HEIGHT / 6);

        settingsButton = new Texture("but.png");
        settingsButtonRect = new Rectangle(500, Main.HEIGHT / 3 * 2 - 275, Main.WIDTH / 2, Main.HEIGHT / 6);

        exitButton = new Texture("but.png");
        exitButtonRect = new Rectangle(500, Main.HEIGHT / 3 - 275, Main.WIDTH / 2, Main.HEIGHT / 6);

        background = new Texture("bg.png");
        backgroundRect = new Rectangle(0, 0, Main.WIDTH, Main.HEIGHT);

        generator = new FreeTypeFontGenerator(Gdx.files.internal("font.TTF"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 96;
        font = generator.generateFont(parameter);
        font.setColor(Color.BLACK);
        generator.dispose();

        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(135 / 255f, 206 / 255f, 235 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        batch.begin();
        batch.draw(background, backgroundRect.x, backgroundRect.y, backgroundRect.width, backgroundRect.height);

        batch.draw(playButton, playButtonRect.x, playButtonRect.y, playButtonRect.width, playButtonRect.height);
        font.draw(batch, "START", 700, Main.HEIGHT - 150);

        batch.draw(settingsButton, settingsButtonRect.x, settingsButtonRect.y, settingsButtonRect.width, settingsButtonRect.height);
        font.draw(batch, "SETTINGS", 700, Main.HEIGHT / 3 * 2 - 150);

        batch.draw(exitButton, exitButtonRect.x, exitButtonRect.y, exitButtonRect.width, exitButtonRect.height);
        font.draw(batch, "EXIT", 700, Main.HEIGHT / 3 - 150);
        batch.end();

        if (Gdx.input.isTouched()) {
            touchCoords.set(Gdx.input.getX(), Main.HEIGHT - Gdx.input.getY());
            if (playButtonRect.contains(touchCoords)) {
                this.dispose();
                GameScreen gs = new GameScreen(game);
                game.activeScreen = gs;
                game.setScreen(gs);
            } else if (settingsButtonRect.contains(touchCoords)) {
                this.dispose();
                SettingsScreen ss = new SettingsScreen(game);
                game.activeScreen = ss;
                game.setScreen(ss);
            } else if (exitButtonRect.contains(touchCoords)) {
                Gdx.app.exit();
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
