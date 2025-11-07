package io.github.some_example_name;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;


public class EndGameScreen implements Screen{
    private Stage stage;
    private Skin skin;
    private Label messageLabel;
    private Label scoreLabel;
    private TextButton menuButton;
    private Main game;
    private int score;
    private Texture backgroundTexture;



    public EndGameScreen(Main game, boolean escaped, int score) {
        this.game = game;
        this.score = score;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("uiskin.json"));
        BitmapFont font = new BitmapFont();
        //Find a background texture to use and add it to assests
        backgroundTexture = new Texture (Gdx.files.internal("background.png")); 
        Image background = new Image(backgroundTexture);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        background.setPosition(0, 0);
        stage.addActor(background);


        //label message which depends on the boolean escaped 
        Label.LabelStyle style = new Label.LabelStyle(font, Color.RED);
        String message = escaped ? "Congratulations! You Escaped!" : "Unlucky, Better luck next time!";
        messageLabel = new Label(message, style);
        messageLabel.setFontScale(2f);
        messageLabel.setPosition(
            Gdx.graphics.getWidth() / 2f - messageLabel.getWidth() / 2f, 
            Gdx.graphics.getHeight() - 200
        );
        stage.addActor(messageLabel);

        //score label
        scoreLabel = new Label(escaped ? "Score: " + score : " ", style);
        scoreLabel.setFontScale(1.5f);
        scoreLabel.setPosition(
            Gdx.graphics.getWidth() / 2f - scoreLabel.getWidth() / 2f, 
            Gdx.graphics.getHeight() - 300
            );
        stage.addActor(scoreLabel);
        
        //button for main menu
        menuButton = new TextButton("Back to main menu", skin);
        menuButton.setSize(300, 80);
        menuButton.setPosition(
            Gdx.graphics.getWidth() / 2f - menuButton.getWidth() / 2f, 
            Gdx.graphics.getHeight() - 500
        );
        menuButton.addListener(new ClickListener(){
            @Override
            //ignore the argument - used for specific click / place clicked
            public void clicked(InputEvent event, float x, float y) {
            	dispose();
                game.setScreen(new MainMenu(game));
            }
        });
        stage.addActor(menuButton);

    }

    @Override
    public void show() {
    	Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    //Resizes screen if window is resized
    public void resize(int width, int height){
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}
    @Override
    //Used to clear old stage + skin
    public void dispose() {
        stage.dispose();
        skin.dispose();
        backgroundTexture.dispose();
    }

    
}
