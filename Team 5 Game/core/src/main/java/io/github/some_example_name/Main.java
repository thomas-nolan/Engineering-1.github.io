package io.github.some_example_name;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Input;
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
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.List;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    Texture backgroundTexture;
    Texture playerTexture;
    Texture speedBoostTexture;

    SpriteBatch spriteBatch;
    BitmapFont font;
    Player player;
    SpeedBoost speedBoost;
    FitViewport viewport;
    TiledMap map;
    OrthogonalTiledMapRenderer mapRenderer;
    Array<TiledMapTileLayer> nonWalkableLayers;
    TiledMapTileLayer walls;
    TiledMapTileLayer corners;

    // UI
    boolean isPaused;
    Stage stage;
    Skin skin;
    Label label;
    Label titleLabel;
    Label pausedLabel;
    TextButton playButton;
    TextButton exitButton;
    boolean mainMenu = true;

    // Timer
    double timer = 10.0;

    // Doors
    private Texture doorTexture;
    private List<Door> doors = new ArrayList<>();

    private Texture keyTexture;
    private Key key;
    private boolean hasKey = false;

    // Dean
    private Texture deanTexture;
    private Dean dean;

    @Override
    public void create() {

    	// Prepare your application here.
        backgroundTexture = new Texture("background.png"); //Background is a placeholder
        playerTexture = new Texture("bucket.png"); //bucket is a placeholder
        speedBoostTexture = new Texture("speed_boost_sprite.png");
        map = new TmxMapLoader().load("./maps/ENG.tmx");

        nonWalkableLayers = new Array<>();
        nonWalkableLayers.add((TiledMapTileLayer) map.getLayers().get("Non-walkable Objects"));
        nonWalkableLayers.add((TiledMapTileLayer) map.getLayers().get("Non-walkable Objects 2"));
        nonWalkableLayers.add((TiledMapTileLayer) map.getLayers().get("Fence"));
        nonWalkableLayers.add((TiledMapTileLayer) map.getLayers().get("Trees"));

        walls = (TiledMapTileLayer) map.getLayers().get("Edges");
        corners = (TiledMapTileLayer) map.getLayers().get("Corners");

        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(1920, 1080);

        player = new Player(playerTexture, 200, 160, nonWalkableLayers, walls, corners, 30, 30);
        speedBoost = new SpeedBoost(speedBoostTexture, 300, 100);

        // Dean
        deanTexture = new Texture("door.jpg");
        dean = new Dean(deanTexture, 550f, 480f, nonWalkableLayers, walls, corners, 400f, 410f, 200f, 165f, 50, 50);

        stage = new Stage(new ScreenViewport());
        font = new BitmapFont();
        font.getData().setScale(2.5f);
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        Label.LabelStyle style = new Label.LabelStyle(font, Color.RED);
        label = new Label(Double.toString(timer) , style);
        titleLabel = new Label("Team 5 Game", style);
        pausedLabel = new Label("PAUSED", style);
        titleLabel.setPosition(900, 1000);
        label.setPosition(900,1000);
        pausedLabel.setPosition(950, 500);
        stage.addActor(pausedLabel);
        stage.addActor(titleLabel);
        stage.addActor(label);
        label.setVisible(false);
        pausedLabel.setVisible(false);

        mapRenderer = new OrthogonalTiledMapRenderer(map);

        // doors
        doorTexture = new Texture("door.jpg");
        doors.add(new Door(485, 580, 52, 52, doorTexture));

        keyTexture = new Texture("keycard1.png");
        key = new Key(550, 480, 30, 30, keyTexture);

        playButton = new TextButton("Play Game", skin);
        playButton.setPosition(500,500);
        playButton.setSize(200,50);
        playButton.addListener(new ClickListener() {
            @Override
                public void clicked(InputEvent event, float x, float y) {
                mainMenu = false;
                playButton.remove();
                exitButton.remove();
                label.setVisible(true);
                titleLabel.setVisible(false);
            }
        });

        exitButton = new TextButton("Exit Game", skin);
        exitButton.setPosition(1000, 500);
        exitButton.setSize(200, 50);
        exitButton.addListener(new ClickListener() {
            @Override
                public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        stage.addActor(exitButton);
        stage.addActor(playButton);
    }

    @Override
    public void resize(int width, int height) {
        if (width <= 0 || height <= 0) return;
        viewport.update(width, height, true);
    }

    /* The render function  */
    @Override
    public void render() {
    	if (getScreen() != null) {
            super.render();
        }
    	else {
	        Gdx.gl.glClearColor(0, 0, 0, 1);
	        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

	        if (mainMenu) {
	            stage.act(Gdx.graphics.getDeltaTime());
	            stage.draw();
	        }
	        else {
	            togglePause();
	            if (!isPaused) {
	                input();
	                logic();
	                updateTimer();
	                speedBoost();
	                dean.update();
	            }
	            draw();
	        }
    	}
    }

    public void speedBoost() {
        boolean isActive = speedBoost.getActive();
        if (speedBoost.checkCollision(player) && isActive) {
            float newSpeed = player.getPlayerSpeed();
            newSpeed *= 2;
            player.setPlayerSpeed(newSpeed);
            speedBoost.deactivate();
        }
    }

    private void input() {
        player.update(doors);
    }

    private void logic() {
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        player.clamp(worldWidth, worldHeight);

        // door
        if (!key.isCollected() && key.collides(player.getCollision())) {
            key.collect();
            hasKey = true;
        }

        // unlock door if player has key
        if (hasKey) {
            for (Door door : doors) {
                door.unlock();
            }
        }

        // Dean
        if (dean.checkCollision(player.getCollision())){
        	gameOver();
        }
    }

    private void draw() {
        viewport.apply();
        // Draw map
        OrthographicCamera camera = (OrthographicCamera) viewport.getCamera();
        mapRenderer.setView(camera);
        mapRenderer.render();

        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();

        //spriteBatch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);
        //font.draw(spriteBatch, "Hello", 1, 1);
        player.draw(spriteBatch);

        if ((speedBoost.getActive())) {
            speedBoost.draw(spriteBatch);
        }

        for (Door door : doors) {
            door.draw(spriteBatch);
        }

        key.draw(spriteBatch);

        dean.draw(spriteBatch);


        spriteBatch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void pause() {}
    @Override
    public void resume() {}

    // Called when timer runs out or caught.
    public void gameOver() {
    	// need to calculate points
    	this.setScreen(new EndGameScreen(this, false, 0));
    }

    private void updateTimer() {
        timer -= Gdx.graphics.getDeltaTime();
        label.setText(Double.toString(timer));
    }

    public void togglePause() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            isPaused = !isPaused;
            if (isPaused) {
                pausedLabel.setVisible(true);
            }
            else {
                pausedLabel.setVisible(false);
            }
        }
    }


    /* This function disposes of application resources freeing up memory*/
    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        spriteBatch.dispose();
        playerTexture.dispose();
        backgroundTexture.dispose();
        doorTexture.dispose();
        speedBoost.dispose();
        keyTexture.dispose();
        deanTexture.dispose();

    }
}
