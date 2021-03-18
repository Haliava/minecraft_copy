package com.mygdx.game;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.model.Block;
import com.mygdx.game.view.GameScreen;

public class Main extends Game {
	Screen gameScreen;
	public static int RENDER_DISTANCE = 2;
	public static int GRAVITY = 5;
	public static int MAX_HEIGHT = 10;
	public static int WIDTH = 2200;
	public static int HEIGHT = 1080;
	public static float MAX_VELOCITY = Block.side_size / 5;
	public static float ROTATION_ANGLE = 10f;
	public static int ID = 1;
	public static String[] BLOCK_TYPES = new String[] {
			"air", "grass", "dirt", "stone", "cobblestone"
	};
	public static TextureAtlas atlas;

	@Override
	public void create() {
		atlas = new TextureAtlas(Gdx.files.internal("texture_atlas/atlas.atlas"));
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
