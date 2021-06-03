package com.mygdx.game;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.model.Block;
import com.mygdx.game.model.Map;
import com.mygdx.game.view.GameScreen;
import com.mygdx.game.view.MenuScreen;

public class Main extends Game {
	public static int FOV = 100;
	public static Skin skin;
	public Screen activeScreen;
	public static int SEED = 109128301;
	public static int REACH = 2;
	public static int MAP_WIDTH = 2;
	public static int MAP_LENGTH = 2;
	public static int RENDER_DISTANCE = 10;
	public static int GRAVITY = 15;
	public static int MAX_HEIGHT = 32;
	public static int MIN_HEIGHT = 0;
	public static int WIDTH = 2200;
	public static int HEIGHT = 1080;
	public static float MAX_VELOCITY = Block.side_size / 5;
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
		skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

		atlas = new TextureAtlas(Gdx.files.internal("texture_atlas/atlas.atlas"));
		hotbar_atlas = new TextureAtlas(Gdx.files.internal("hotbar/hotbar.atlas"));
		selectedSquareTexture = new Texture("hotbar/selected_square.jpg");

		if (activeScreen == null) {
			activeScreen = new MenuScreen(this);
			setScreen(activeScreen);
		}
	}

	@Override
	public void render() {
		activeScreen.render(TIME_SCALE);
	}

	@Override
	public void dispose() {
	}
}
