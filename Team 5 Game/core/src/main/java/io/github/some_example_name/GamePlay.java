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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/* This class handles the main game screen.
 * This class creates the map, player, dean
 * and any objects as well as a timer and pause menu
 * that can be activated by player input.
 */
public class GamePlay implements Screen {
	//Textures
    Texture playerTexture;
    Texture speedBoostTexture;
    Texture doorTexture;
    Texture keyTexture;
    Texture deanTexture;
    Texture deanAreaDebug;

    SpriteBatch spriteBatch;
    BitmapFont font;
    Player player;
    SpeedBoost speedBoost;

    //map
    FitViewport viewport;
    TiledMap map;
    OrthogonalTiledMapRenderer mapRenderer;
    Rectangle finishZone;
    //map collision
    Array<TiledMapTileLayer> nonWalkableLayers;
    TiledMapTileLayer walls;
    TiledMapTileLayer corners;

    // UI
    boolean isPaused;
    Stage stage;
    Skin skin;
    Label label;
    Label pausedLabel;
    // Label styles (red, yellow and green)
    Label.LabelStyle redStyle;
    Label.LabelStyle yellowStyle;
    Label.LabelStyle greenStyle;

    // Timer
    double timer = 300.0;

    // Doors
    private List<Door> doors = new ArrayList<>();
    private Tripwire tripWire;
    private Key key;
    private boolean hasKey = false;

    // Dean
    private Dean dean;

    private final Main main;

    // Points
    Points points = new Points();

    //constructor
    public GamePlay(final Main game) {
        this.main = game;
    }

    @Override
    public void show() {
        System.out.println("GamePlay screen loading...");

        // Initialize viewport and camera FIRST
        viewport = new FitViewport(1600, 1120);
        OrthographicCamera camera = (OrthographicCamera) viewport.getCamera();
        camera.position.set(960, 540, 0);
        camera.update();

        spriteBatch = new SpriteBatch();

        
        
        
        // Load textures
        playerTexture = new Texture(Gdx.files.internal("player1.png"));
        speedBoostTexture = new Texture(Gdx.files.internal("speed_boost_sprite.png"));
        doorTexture = new Texture(Gdx.files.internal("door1.png"));
        keyTexture = new Texture(Gdx.files.internal("keycard1.png"));
        deanTexture = new Texture(Gdx.files.internal("dean.png"));

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
        
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        
        
        
        // Initialize game objects
        //player
        player = new Player(playerTexture, 775, 100, nonWalkableLayers, walls, corners, 40, 40);
        
        //speedboost
        speedBoost = new SpeedBoost("SpeedBoost", speedBoostTexture, 680, 490);

        //dean
        dean = new Dean(deanTexture, 550f, 480f, nonWalkableLayers, walls, corners, 425f, 425f, 180f, 145f, 50, 50);

        //doors
        Door door = new Door(485, 580, 52, 52, doorTexture);
        door.unlock();
        doors.add(door);
        
        //key
        Rectangle keyZone = new Rectangle(1472, 480, 35, 35);
        key = new Key("Keycard", keyZone, keyTexture);
        
        //tripwire
        Rectangle tripWireZone = new Rectangle(378, 500, 32, 32);
        tripWire = new Tripwire("tripwire", tripWireZone, door);
        
        finishZone = new Rectangle(0, 864, 32, 128);
        
        
        // Set up UI (only game UI, no menu)
        stage = new Stage(new ScreenViewport());

        skin = new Skin(Gdx.files.internal("uiskin.json"));
        font = new BitmapFont();
        font.getData().setScale(2.5f);

        redStyle = new Label.LabelStyle(font, Color.RED);
        yellowStyle = new Label.LabelStyle(font, Color.YELLOW);
        greenStyle = new Label.LabelStyle(font, Color.GREEN);
        label = new Label(String.format("%.1f", timer), greenStyle);
        pausedLabel = new Label("PAUSED", greenStyle);

        label.setPosition(900, 1000); // At the top of the screen
        pausedLabel.setPosition(900, 500); // Displayed at the centre of the screen

        stage.addActor(pausedLabel);
        stage.addActor(label);
        pausedLabel.setVisible(false);

        mapRenderer = new OrthogonalTiledMapRenderer(map);

        

        
        
        
        
        // Set up UI (only game UI, no menu)
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        font = new BitmapFont();
        font.getData().setScale(2.5f);
        Label.LabelStyle style = new Label.LabelStyle(font, Color.RED);
        label = new Label(String.format("%.1f", timer), style);
        pausedLabel = new Label("PAUSED", style);
        label.setPosition(900, 1000); // At the top of the screen
        pausedLabel.setPosition(900, 500); // Displayed at the centre of the screen
        stage.addActor(pausedLabel);
        stage.addActor(label);
        pausedLabel.setVisible(false);

        

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
        if (!speedBoost.isTriggered() && speedBoost.checkCollision(player)) {
            speedBoost.trigger();
            float newSpeed = player.getPlayerSpeed();
            newSpeed *= 2;
            player.setPlayerSpeed(newSpeed);
        }
    }

    private void input() {
        player.update(doors, tripWire);
    }

    private void logic() {
    	//clamp player movement to world
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        player.clamp(worldWidth, worldHeight);

        if (finishZone.overlaps(player.getCollision())) {
            gameOver(true);
        }
        
        // Key collection
        if (!key.isTriggered() && key.collides(player.getCollision()) && tripWire.isTriggered()) {
            key.trigger();
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
            points.deanCaughtYou();

            gameOver(false);
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

        //only draw when the player has not collected yet
        if (!speedBoost.isTriggered()) {
            speedBoost.draw(spriteBatch);
        }

        //draw list of doors
        for (Door door : doors) {
            door.draw(spriteBatch);
        }

        //draw key
        if (tripWire.isTriggered()) {
            key.draw(spriteBatch);
        }

        //draw dean
        dean.draw(spriteBatch);
        
        //debug
        //spriteBatch.draw(deanAreaDebug, 425, 425, 180, 145);

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

    public void gameOver(boolean hasWon) {
        Event.resetEventsCounter();
        System.out.println("Game Over!");
        if (hasWon) {
            points.calcPoints(timer);
            main.winGame(points.getScore());
        }
        else {
            main.endGame();
        }
    }

    private void updateTimer(float delta) {
        timer -= delta;
        if (timer <= 150 && timer >= 60) {
            label.setStyle(yellowStyle);
        }
        if (timer <= 60) {
            label.setStyle(redStyle);
        }
        label.setText(String.format("%.1f", timer));
        if (timer <= 0) {
            gameOver(false);
        }
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
