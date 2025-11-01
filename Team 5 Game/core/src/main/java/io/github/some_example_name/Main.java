package io.github.some_example_name;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input;
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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main implements ApplicationListener {
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
    TiledMapTileLayer nonWalkable;
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
    float timer = 10f;

    @Override
    public void create() {
        // Prepare your application here.
        backgroundTexture = new Texture("background.png"); //Background is a placeholder
        playerTexture = new Texture("bucket.png"); //bucket is a placeholder
        speedBoostTexture = new Texture("speed_boost_sprite.png");
        map = new TmxMapLoader().load("ENG_START_MAP.tmx");
        nonWalkable = (TiledMapTileLayer) map.getLayers().get("non-walkable objects");
        walls = (TiledMapTileLayer) map.getLayers().get("Bording");
        corners = (TiledMapTileLayer) map.getLayers().get("Corners");

        spriteBatch = new SpriteBatch();
        font = new BitmapFont();
        viewport = new FitViewport(1920, 1080);
        player = new Player(playerTexture, 200, 160, nonWalkable, walls, corners);
        speedBoost = new SpeedBoost(speedBoostTexture, 300, 100);

        stage = new Stage(new ScreenViewport());
        font = new BitmapFont();
        font.getData().setScale(2.5f);
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        Label.LabelStyle style = new Label.LabelStyle(font, Color.RED);
        label = new Label(Float.toString(timer) , style);
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


        map = new TmxMapLoader().load("ENG_START_MAP.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);

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
        // If the window is minimized on a desktop (LWJGL3) platform, width and height are 0, which causes problems.
        // In that case, we don't resize anything, and wait for the window to be a normal size before updating.
        if(width <= 0 || height <= 0) return;

        // Resize your application here. The parameters represent the new window size.
        viewport.update(width, height, true); // true centers the camera
    }

    /* The render function  */
    @Override
    public void render() {
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
            }
            draw();
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
        player.update();
    }

    private void logic() {
        // Store the worldWidth and worldHeight as local variables for brevity
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        player.clamp(worldWidth, worldHeight);
    }

    private void draw() {

        viewport.apply();
        // Draw map
        mapRenderer.setView((OrthographicCamera) viewport.getCamera());
        mapRenderer.render();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        player.draw(spriteBatch);

        if ((speedBoost.getActive())) {
            speedBoost.draw(spriteBatch);
        }


        spriteBatch.end();
        // Draw map
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    // Called when timer runs out or caught.
    public void gameOver() {
        
    }

    public void updateTimer() {
        if (timer >= 0) {
            timer -= Gdx.graphics.getDeltaTime();
            int intTimer = (int) timer; // Removes decimals when displaying the timer
            label.setText(Integer.toString(intTimer));
        }
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
        // Destroy application's resources here.
        stage.dispose();
        skin.dispose();
        speedBoost.dispose();
    }
}
