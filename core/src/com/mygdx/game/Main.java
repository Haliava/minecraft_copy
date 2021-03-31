package com.mygdx.game;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
	public static int MAP_WIDTH = 1;
	public static int MAP_LENGTH = 1;
	public static int RENDER_DISTANCE = 1;
	public static int GRAVITY = 9; //15
	public static int MAX_HEIGHT = 20;
	public static int MIN_HEIGHT = 0;
	public static int WIDTH = 2200;
	public static int HEIGHT = 1080;
	public static float MAX_VELOCITY = Block.side_size / 7;
	public static float ROTATION_ANGLE = 10f;
	public static String ID = "00000";
	public static String[] BLOCK_TYPES = new String[] {
			"air", "grass", "dirt", "stone", "cobblestone", "bedrock"
	};
	public static TextureAtlas atlas;
	public static Map WORLD_MAP = new Map(MAP_WIDTH, MAP_LENGTH);

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
