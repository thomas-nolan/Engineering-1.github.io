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

/* Class for the end game screen.
 * Once the game is completed, the end game screen displays
 * a message that varies based on if the player won or not
 * The final point score is also displayed in this menu
 * The player can return to the main menu from this screen with a button
 */
public class EndGameScreen implements Screen{
    private Stage stage;
    private Skin skin;
    private Label messageLabel;
    private Label scoreLabel;
    private TextButton menuButton;
    private Main game;
    private int score;
    private Texture backgroundTexture;


    /**
     * Constructor for the EndGameScreen.
     *
     * @param game    the main game instance which is used to switch screens
     * @param escaped true if the player has escaped, false if they failed
     * @param score   the player's final score, calculated at the end
     */
    public EndGameScreen(Main game, boolean escaped, int score) {
        this.game = game;
        this.score = score;

        // Create a new stage and set it as the input processor
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Loads default UI skin for buttons and labels
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        BitmapFont font = new BitmapFont();
        // Different screens shown for failure and success
        if (escaped) {
            backgroundTexture = new Texture(Gdx.files.internal("endGameWin1.bmp"));
        } else {
            backgroundTexture = new Texture(Gdx.files.internal("endGameFail1.bmp"));
        }

        // Create and add the background image to the stage
        Image background = new Image(backgroundTexture);
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        background.setPosition(0, 0);
        stage.addActor(background);


        //label message and colour which depends on the boolean escaped
        Label.LabelStyle style =  escaped ? new Label.LabelStyle(font, Color.GREEN) : new Label.LabelStyle(font, Color.RED);
        String message = escaped ? "Congratulations! You Escaped!" : "Unlucky, Better luck next time!";
        messageLabel = new Label(message, style);
        messageLabel.setFontScale(2f);
        messageLabel.setPosition(
            Gdx.graphics.getWidth() / 2f - messageLabel.getWidth() / 2f,
            Gdx.graphics.getHeight() - 100
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
    //Used to clear old stage + skin to free memory
    public void dispose() {
        stage.dispose();
        skin.dispose();
        backgroundTexture.dispose();
    }

}
