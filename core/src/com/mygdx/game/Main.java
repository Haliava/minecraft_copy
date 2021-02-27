package com.mygdx.game;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.view.GameScreen;

public class Main extends Game {
	Screen gameScreen;
	public static int WIDTH = 2200;
	public static int HEIGHT = 1080;

	@Override
	public void create() {
		gameScreen = new GameScreen();
		setScreen(gameScreen);
	}

	@Override
	public void render() {
		gameScreen.render(0.05f);
	}

	@Override
	public void dispose() {
	}
}
