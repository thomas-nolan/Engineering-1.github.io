package io.github.some_example_name;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.List;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main implements ApplicationListener {
    Texture backgroundTexture;
    Texture playerTexture;

    SpriteBatch spriteBatch;
    BitmapFont font;
    Player player;
    FitViewport viewport;
    TiledMap map;
    OrthogonalTiledMapRenderer mapRenderer;
    TiledMapTileLayer nonWalkable;
    TiledMapTileLayer walls;
    TiledMapTileLayer corners;

    // UI
    Stage stage;
    Skin skin;
    Label label;

    // Timer
    double timer = 10.0;

    // Doors
    private Texture doorTexture;
    private List<Door> doors = new ArrayList<>();

    @Override
    public void create() {
        backgroundTexture = new Texture("background.png");
        playerTexture = new Texture("bucket.png");
        map = new TmxMapLoader().load("ENG_START_MAP.tmx");

        nonWalkable = (TiledMapTileLayer) map.getLayers().get("non-walkable objects");
        walls = (TiledMapTileLayer) map.getLayers().get("Bording");
        corners = (TiledMapTileLayer) map.getLayers().get("Corners");

        spriteBatch = new SpriteBatch();
        font = new BitmapFont();
        viewport = new FitViewport(1920, 1080);

        player = new Player(playerTexture, 200, 160, nonWalkable, walls, corners);

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        Label.LabelStyle style = new Label.LabelStyle(font, Color.RED);
        label = new Label(Double.toString(timer), style);
        label.setPosition(950, 1000);
        stage.addActor(label);

        mapRenderer = new OrthogonalTiledMapRenderer(map);

        // doors
        doorTexture = new Texture("door.jpg");
        doors.add(new Door(205, 70, 20, 20, doorTexture));
    }

    @Override
    public void resize(int width, int height) {
        if (width <= 0 || height <= 0) return;
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        input();
        logic();
        draw();
        updateTimer();
    }

    private void input() {
        player.update(doors);
    }

    private void logic() {
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        player.clamp(worldWidth, worldHeight);

        // door
        for (Door door : doors) {
            if (door.collides(player.getCollision())) {
                // unlock door
            	// finish when key item is implemented
            }
        }
    }

    private void draw() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.apply();
        OrthographicCamera camera = (OrthographicCamera) viewport.getCamera();
        mapRenderer.setView(camera);
        mapRenderer.render();

        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();

        //spriteBatch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);
        //font.draw(spriteBatch, "Hello", 1, 1);
        player.draw(spriteBatch);

        for (Door door : doors) {
            door.draw(spriteBatch);
        }

        spriteBatch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void pause() {}
    @Override
    public void resume() {}

    private void updateTimer() {
        timer -= Gdx.graphics.getDeltaTime();
        label.setText(Double.toString(timer));
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        spriteBatch.dispose();
        playerTexture.dispose();
        backgroundTexture.dispose();
        doorTexture.dispose();
    }
}
