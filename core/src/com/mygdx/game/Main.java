package com.mygdx.game;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.mygdx.game.view.GameScreen;

class SimpleGame extends Game {
	private Screen gameScreen;

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
