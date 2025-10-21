package io.github.some_example_name;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

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

    // UI
    Stage stage;
    Skin skin;
    Label label;

    // Timer
    float timer = 10f;

    @Override
    public void create() {
        // Prepare your application here.
        backgroundTexture = new Texture("background.png"); //Background is a placeholder
        playerTexture = new Texture("bucket.png"); //bucket is a placeholder
        map = new TmxMapLoader().load("ENG_START_MAP.tmx");
        nonWalkable = (TiledMapTileLayer) map.getLayers().get("non-walkable objects");

        //dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.mp3"));
        //music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        spriteBatch = new SpriteBatch();
        font = new BitmapFont();
        //font.getData().setScale(0.05f);
        viewport = new FitViewport(1920, 1080);
        player = new Player(playerTexture, 15, 90, nonWalkable);

        stage = new Stage(new ScreenViewport());
        font = new BitmapFont();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        Label.LabelStyle style = new Label.LabelStyle(font, Color.RED);
        label = new Label(Float.toString(timer) , style);
        label.setPosition(950,1000);
        stage.addActor(label);

        map = new TmxMapLoader().load("ENG_START_MAP.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);

    }

    @Override
    public void resize(int width, int height) {
        // If the window is minimized on a desktop (LWJGL3) platform, width and height are 0, which causes problems.
        // In that case, we don't resize anything, and wait for the window to be a normal size before updating.
        if(width <= 0 || height <= 0) return;

        // Resize your application here. The parameters represent the new window size.
        viewport.update(width, height, true); // true centers the camera
    }

    @Override
    public void render() {
        // organize code into three methods
        input();
        logic();
        draw();
        updateTimer();
    }



    private void input() {
        player.update();
    }

    private void logic() {
        // Store the worldWidth and worldHeight as local variables for brevity
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        player.clamp(worldWidth, worldHeight);
    }

    private void draw() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.apply();
        // Draw mao
        mapRenderer.setView((OrthographicCamera) viewport.getCamera());
        mapRenderer.render();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        //spriteBatch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);
        //font.draw(spriteBatch, "Hello", 1, 1);
        player.draw(spriteBatch);

        spriteBatch.end();
        // Draw map
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();


    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    public void updateTimer() {
        if (timer >= 0) {
            timer -= Gdx.graphics.getDeltaTime();
            int intTimer = (int) timer; // Removes decimals when displaying the timer
            label.setText(Integer.toString(intTimer));
        }
    }

    @Override
    public void dispose() {
        // Destroy application's resources here.
        stage.dispose();
        skin.dispose();
    }
}
