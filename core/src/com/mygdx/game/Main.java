package com.mygdx.game;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.view.GameScreen;

class SimpleGame extends Game {
	Screen gameScreen;
	Vector3 touchPoint;
	Rectangle leftBounds, rightBounds;

	@Override
	public void create() {
		gameScreen = new GameScreen();
		setScreen(gameScreen);
	}

	@Override
	public void render() {
		leftBounds = new Rectangle(0, 0, 80, 80);
		rightBounds = new Rectangle(80, 0, 80, 80);
		touchPoint = new Vector3();

		gameScreen.render(0.05f);
	}

	@Override
	public void dispose() {
	}
}
