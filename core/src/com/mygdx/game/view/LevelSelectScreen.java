package com.mygdx.game.view;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.Main;

public class LevelSelectScreen implements Screen {
    Table outerTable, innerTable, list;
    ScrollPane scrollPane;
    Stage stage;

    @Override
    public void show() {
        stage = new Stage();

        innerTable = new Table();
        outerTable = new Table();
        scrollPane = new ScrollPane(innerTable);

        outerTable.add(scrollPane).height(Main.HEIGHT * 2);
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
