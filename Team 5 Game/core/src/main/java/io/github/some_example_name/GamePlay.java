package io.github.some_example_name;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GamePlay implements Screen {
    Texture playerTexture;
    Texture speedBoostTexture;
    Texture doorTexture;
    Texture keyTexture;
    Texture deanTexture;

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
    Label pausedLabel;

    // Timer
    double timer = 10.0;

    // Doors
    private List<Door> doors = new ArrayList<>();
    private Key key;
    private boolean hasKey = false;

    // Dean
    private Dean dean;
    private final Main main;

    public GamePlay(final Main game) {
        this.main = game;
    }

    @Override
    public void show() {
        System.out.println("GamePlay screen loading...");

        // Initialize viewport and camera FIRST
        viewport = new FitViewport(1920, 1080);
        OrthographicCamera camera = (OrthographicCamera) viewport.getCamera();
        camera.position.set(960, 540, 0);
        camera.update();

        spriteBatch = new SpriteBatch();

        // Load textures
        playerTexture = new Texture(Gdx.files.internal("player1.png"));
        speedBoostTexture = new Texture(Gdx.files.internal("speed_boost_sprite.png"));
        doorTexture = new Texture(Gdx.files.internal("door.jpg"));
        keyTexture = new Texture(Gdx.files.internal("keycard1.png"));
        deanTexture = new Texture(Gdx.files.internal("door.jpg"));

        System.out.println("Textures loaded successfully");

        // Load map
        map = new TmxMapLoader().load(Gdx.files.internal("maps/ENG.tmx").file().getPath());
        System.out.println("Map loaded successfully");

        // Set up map layers
        nonWalkableLayers = new Array<>();
        nonWalkableLayers.add((TiledMapTileLayer) map.getLayers().get("Non-walkable Objects"));
        nonWalkableLayers.add((TiledMapTileLayer) map.getLayers().get("Non-walkable Objects 2"));
        nonWalkableLayers.add((TiledMapTileLayer) map.getLayers().get("Fence"));
        nonWalkableLayers.add((TiledMapTileLayer) map.getLayers().get("Trees"));

        walls = (TiledMapTileLayer) map.getLayers().get("Edges");
        corners = (TiledMapTileLayer) map.getLayers().get("Corners");

        // Initialize game objects
        player = new Player(playerTexture, 200, 160, nonWalkableLayers, walls, corners, 30, 30);
        speedBoost = new SpeedBoost(speedBoostTexture, 300, 100);
        dean = new Dean(deanTexture, 550f, 480f, nonWalkableLayers, walls, corners, 400f, 410f, 200f, 165f, 50, 50);

        // Set up UI (only game UI, no menu)
        stage = new Stage(new ScreenViewport());

        skin = new Skin(Gdx.files.internal("uiskin.json"));
        font = new BitmapFont();
        font.getData().setScale(2.5f);

        Label.LabelStyle style = new Label.LabelStyle(font, Color.RED);
        label = new Label(String.format("%.1f", timer), style);
        pausedLabel = new Label("PAUSED", style);

        label.setPosition(900, 1000);
        pausedLabel.setPosition(950, 500);

        stage.addActor(pausedLabel);
        stage.addActor(label);
        pausedLabel.setVisible(false);

        mapRenderer = new OrthogonalTiledMapRenderer(map);

        // Set up doors and key
        doors.add(new Door(485, 580, 52, 52, doorTexture));
        key = new Key(550, 480, 30, 30, keyTexture);

        System.out.println("GamePlay screen loaded successfully");
    }

    @Override
    public void resize(int width, int height) {
        if (width <= 0 || height <= 0) return;
        viewport.update(width, height, true);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render(float delta) {
        // Clear screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // No menu check - just show the game directly
        togglePause();
        if (!isPaused) {
            input();
            logic();
            updateTimer(delta);
            speedBoost();
            dean.update();
        }
        draw();
    }

    public void speedBoost() {
        if (speedBoost.getActive() && speedBoost.checkCollision(player)) {
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

        // Key collection
        if (!key.isCollected() && key.collides(player.getCollision())) {
            key.collect();
            hasKey = true;
        }

        // Unlock doors
        if (hasKey) {
            for (Door door : doors) {
                door.unlock();
            }
        }

        // Dean collision
        if (dean.checkCollision(player.getCollision())) {
            gameOver();
        }
    }

    private void draw() {
        viewport.apply();

        // Draw map
        OrthographicCamera camera = (OrthographicCamera) viewport.getCamera();
        mapRenderer.setView(camera);
        mapRenderer.render();

        // Draw game objects
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();

        player.draw(spriteBatch);

        if (speedBoost.getActive()) {
            speedBoost.draw(spriteBatch);
        }

        for (Door door : doors) {
            door.draw(spriteBatch);
        }

        key.draw(spriteBatch);
        dean.draw(spriteBatch);

        spriteBatch.end();

        // Draw UI
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    public void gameOver() {
        System.out.println("Game Over!");
        main.endGame();
    }

    private void updateTimer(float delta) {
        timer -= delta;
        label.setText(String.format("%.1f", timer));
    }

    public void togglePause() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            isPaused = !isPaused;
            pausedLabel.setVisible(isPaused);
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        spriteBatch.dispose();
        playerTexture.dispose();
        doorTexture.dispose();
        speedBoost.dispose();
        keyTexture.dispose();
        deanTexture.dispose();
        map.dispose();
        mapRenderer.dispose();
    }
}
