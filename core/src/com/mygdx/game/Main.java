package com.mygdx.game;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.model.Block;
import com.mygdx.game.model.Map;
import com.mygdx.game.view.GameScreen;

public class Main extends Game {
	Screen gameScreen;
	public static int REACH = 3;
	public static int MAP_WIDTH = 1;
	public static int MAP_LENGTH = 1;
	public static int RENDER_DISTANCE = 1;
	public static int GRAVITY = 8; //15
	public static int MAX_HEIGHT = 32;
	public static int MIN_HEIGHT = 0;
	public static int WIDTH = 2200;
	public static int HEIGHT = 1080;
	public static float MAX_VELOCITY = Block.side_size / 15;
	public static float LONG_PRESS_TIME = 0.5f;
	public static float TIME_SCALE = 0.05f;
	public static TextureAtlas atlas;
	public static TextureAtlas hotbar_atlas;
	public static Map WORLD_MAP = new Map(MAP_WIDTH, MAP_LENGTH);
	public static Texture selectedSquareTexture;
	public static int selectedSquareX = (int) (Main.WIDTH / 3.3);
	public static int selectedSquareIndex = 0;

	@Override
	public void create() {
		atlas = new TextureAtlas(Gdx.files.internal("texture_atlas/atlas.atlas"));
		hotbar_atlas = new TextureAtlas(Gdx.files.internal("hotbar/hotbar.atlas"));
		selectedSquareTexture = new Texture("hotbar/selected_square.jpg");
		gameScreen = new GameScreen();
		setScreen(gameScreen);
	}

	@Override
	public void render() {
		gameScreen.render(TIME_SCALE);
	}

	@Override
	public void dispose() {
	}
}
