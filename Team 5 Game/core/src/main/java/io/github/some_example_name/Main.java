package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.DOMError;

public class Main extends Game {
	public SpriteBatch batch;
    @Override
    public void create() {

    	batch = new SpriteBatch();
        //Calling the new class here
        setScreen(new MainMenu(this)); // Start with the menu

    }

    @Override
    public void render() {
    	super.render();
    }

  //This function disposes of application resources freeing up memory
    @Override
    public void dispose() {
        batch.dispose();
        if (getScreen() != null) {
        	getScreen().dispose();
        }
    }

    // Method to start the game
    public void startGame() {setScreen(new GamePlay(this)); }

    public void endGame() {setScreen(new EndGameScreen(this, false, 0));}

    public void winGame(int points) {setScreen(new EndGameScreen(this, true, points));}

}
