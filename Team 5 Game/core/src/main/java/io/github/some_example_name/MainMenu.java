package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Main menu screen
 * This is the first thing players see which is called from the lwjgl3
 * links to start the game class -->
 * All the assets used for this class has been made and should be found in the assets folder
 */
public class MainMenu implements Screen {

    private final Main main;
    private Stage MainMenuStage;
    private BitmapFont buttonsFont; // Required for TextButton even with empty text
    private Texture titleBackgroundTexture;
    private Texture startButtonTexture, tutorialButtonTexture, exitButtonTexture;
    private Texture hoverStartButtonTexture, hoverTutorialButtonTexture, hoverExitButtonTexture;
    private Texture tutorialBackgroundTexture, tutorialExitButtonTexture;

    public MainMenu(final Main game) {
        this.main = game;
        MakeFont();
        generateAssets();
        ShowScreen();
    }

    //Change in future as used when planning the window screening
    private void MakeFont() {
        buttonsFont = new BitmapFont();
        buttonsFont.getData().setScale(1.0f); //Set it at random
    }


    private void generateAssets(){
        //Loading the assets
        titleBackgroundTexture = new Texture(Gdx.files.internal("anotherSpritetest.png"));
        tutorialBackgroundTexture =  new Texture(Gdx.files.internal("TutorialPage.png"));
        startButtonTexture =  new Texture(Gdx.files.internal("StartButton.png"));
        tutorialButtonTexture =  new Texture(Gdx.files.internal("TutorialButton.png"));
        exitButtonTexture =  new Texture(Gdx.files.internal("ExitButton.png"));
        hoverStartButtonTexture =  new Texture(Gdx.files.internal("HoverStartButton.png"));
        hoverTutorialButtonTexture =  new Texture(Gdx.files.internal("HoverTutorialButton.png"));
        hoverExitButtonTexture =  new Texture(Gdx.files.internal("HoverExitButton.png"));
        tutorialExitButtonTexture =  new Texture(Gdx.files.internal("CloseButton.png"));
    }

    //Hover so I changed it so that it would make
    private TextButton.TextButtonStyle IsMousOverTexture(Texture isMouseNotOverTexture, Texture isMouseOverTexture) {
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(isMouseNotOverTexture);
        buttonStyle.over = new TextureRegionDrawable(isMouseOverTexture);
        buttonStyle.down = new TextureRegionDrawable(isMouseNotOverTexture);
        buttonStyle.font = buttonsFont;
        return buttonStyle;
    }
    //Tutorial background
    private TextButton.TextButtonStyle createImageButtonStyle(Texture texture) {
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(texture);
        buttonStyle.font = buttonsFont;
        return buttonStyle;
    }

    private void ShowScreen() {
        MainMenuStage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(MainMenuStage);
        setupMainMenuButtons();
    }

    //Setting up the buttons
    private void setupMainMenuButtons() {
        TextButton startButton = createMainMenuButton(startButtonTexture, hoverStartButtonTexture, 0);
        TextButton tutorialButton = createMainMenuButton(tutorialButtonTexture, hoverTutorialButtonTexture, 1);
        TextButton exitButton = createMainMenuButton(exitButtonTexture, hoverExitButtonTexture, 2);

        startButton.addListener(createStartButtonListener());
        tutorialButton.addListener(createTutorialButtonListener());
        exitButton.addListener(createExitButtonListener());
        MainMenuStage.addActor(startButton);
        MainMenuStage.addActor(tutorialButton);
        MainMenuStage.addActor(exitButton);
    }

    private TextButton createMainMenuButton(Texture normalTexture, Texture hoverTexture, int position) {
        TextButton.TextButtonStyle buttonStyle = IsMousOverTexture(normalTexture, hoverTexture);
        TextButton button = new TextButton("", buttonStyle);

        float centerX = (Gdx.graphics.getWidth() - 400) / 2;
        float startY = Gdx.graphics.getHeight() * 0.4f;
        float yPosition = startY - (position * (120 + 40));

        button.setBounds(centerX, yPosition, 400, 120);
        return button;
    }

    private ClickListener createStartButtonListener() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {main.startGame();}
        };
    }

    private ClickListener createTutorialButtonListener() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {showTutorial();}
        };
    }

    private ClickListener createExitButtonListener() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {Gdx.app.exit();}
        };
    }

    private void showTutorial() {
        Window tutorialWindow = createTutorialWindow();
        TextButton xButton = createTutorialXButton(tutorialWindow);

        tutorialWindow.addActor(xButton);
        MainMenuStage.addActor(tutorialWindow);
    }

    private Window createTutorialWindow() {
        Window.WindowStyle tutorialStyle = new Window.WindowStyle();
        tutorialStyle.background = new TextureRegionDrawable(tutorialBackgroundTexture);
        tutorialStyle.titleFont = buttonsFont; // Font required for window

        Window tutorialWindow = new Window("", tutorialStyle);
        tutorialWindow.setModal(true);

        float windowWidth = Gdx.graphics.getWidth() * 0.55f;
        float windowHeight = Gdx.graphics.getHeight() * 0.75f;
        float windowX = (Gdx.graphics.getWidth() - windowWidth) / 2;
        float windowY = (Gdx.graphics.getHeight() - windowHeight) / 2;

        tutorialWindow.setSize(windowWidth, windowHeight);
        tutorialWindow.setPosition(windowX, windowY);

        return tutorialWindow;
    }

    //Making the tutorial X button.
    private TextButton createTutorialXButton(Window tutorialWindow) {
        TextButton.TextButtonStyle xButtonStyle = createImageButtonStyle(tutorialExitButtonTexture);
        TextButton xButton = new TextButton("", xButtonStyle);

        float xButtonX = tutorialWindow.getWidth() - 90;
        float xButtonY = tutorialWindow.getHeight() - 70;

        xButton.setBounds(xButtonX, xButtonY, 50, 50);
        xButton.addListener(createCloseButtonListener(tutorialWindow));

        return xButton;
    }

    //If the X button is interacted with then close the window
    private ClickListener createCloseButtonListener(Window tutorialWindow) {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {tutorialWindow.remove();}
        };
    }

    @Override
    public void render(float delta) {
        main.batch.begin();
        main.batch.draw(titleBackgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        main.batch.end();

        MainMenuStage.act(delta);
        MainMenuStage.draw();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void show() {
        Gdx.input.setInputProcessor(MainMenuStage);
    }
     @Override
    public void dispose() {
        MainMenuStage.dispose();
        buttonsFont.dispose();
        titleBackgroundTexture.dispose();
        tutorialBackgroundTexture.dispose();
        startButtonTexture.dispose();
        tutorialButtonTexture.dispose();
        exitButtonTexture.dispose();
        hoverStartButtonTexture.dispose();
        hoverTutorialButtonTexture.dispose();
        hoverExitButtonTexture.dispose();
        tutorialExitButtonTexture.dispose();
    }

    @Override
    public void hide() {}
    @Override
    public void pause() {}
    @Override
    public void resume() {}


}
