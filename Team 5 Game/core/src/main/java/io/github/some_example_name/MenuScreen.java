package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Main menu screen
 * This is the first thing players see which is called from the lwjgl3
 * links to start the game class -->
 */
public class MenuScreen implements Screen {

    // Need the main game class to switch between screens
    private final Main game;

    // Stage holds all our UI elements - buttons, labels, etc.
    private Stage stage;

    // Fonts for different text sizes
    private BitmapFont titleFont;
    private BitmapFont buttonFont;

    //Colourss
    private final Color BACKGROUND = new Color(0.219f, 0.110f, 0.165f, 1f); // Main background
    private final Color TUTORIAL_BG = new Color(0.300f, 0.110f, 0.265f, 1f); // tutorial background
    private final Color BUTTON_MAIN = new Color(0.639f, 0.345f, 0.427f, 1f); //buttons (N)
    private final Color BUTTON_HOVER = new Color(0.941f, 0.396f, 0.325f, 1f); //buttons (A)
    private final Color TITLE_COLOR = new Color(1f, 0.957f, 0.455f, 1f); // Title

    /**
     * Constructor: sets up the menu when the game starts
     * game to pass onto the next class. so when the start
     */
    public MenuScreen(final Main game) {
        this.game = game;
        getFonts();
        setupUI();
    }

    /**
     * Sets up the fonts with all of their propoties
     */
    private void getFonts() {
        //Title font
        //Change in future to a bitmap as its tiny and blury x
        titleFont = new BitmapFont();
        titleFont.getData().setScale(4.0f); //Size
        titleFont.setColor(TITLE_COLOR);

        //buttons
        buttonFont = new BitmapFont();
        buttonFont.getData().setScale(2.0f); //Size
        buttonFont.setColor(Color.WHITE); //Buttons
    }

    /**
     * Creates the buttons on the screen with the size and stuff
     */
    private TextureRegionDrawable createPixelButton(int width, int height, Color color) {
        // Create a rectangle
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();

        // Add a boarder to the button
        pixmap.setColor(new Color(color).sub(0.15f, 0.15f, 0.15f, 0f));
        pixmap.drawRectangle(0, 0, width, height);

        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return new TextureRegionDrawable(texture);
    }

    /**
     * Sets up all the interface
     */
    private void setupUI() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage); // Make sure our buttons can be interacted with

        //main menu buttons
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        int buttonWidth = 300;
        int buttonHeight = 80;

        // Button states so the user knows when they hover over this. "Accessability ig"
        buttonStyle.up = createPixelButton(buttonWidth, buttonHeight, BUTTON_MAIN);    // Normal purple
        buttonStyle.over = createPixelButton(buttonWidth, buttonHeight, BUTTON_HOVER); // Hover dark blue
        buttonStyle.down = createPixelButton(buttonWidth, buttonHeight, BUTTON_MAIN);  // Clicked (same as normal)
        buttonStyle.font = buttonFont;

        // Create the buttons text
        TextButton startButton = new TextButton("START", buttonStyle);
        TextButton tutorialButton = new TextButton("HOW TO PLAY", buttonStyle);
        TextButton exitButton = new TextButton("EXIT", buttonStyle);

        //get center position
        float centerX = (Gdx.graphics.getWidth() - buttonWidth) / 2;
        float startY = Gdx.graphics.getHeight() * 0.4f;
        float spacing = 20;

        //set the boundries
        startButton.setBounds(centerX, startY, buttonWidth, buttonHeight);
        tutorialButton.setBounds(centerX, startY - (buttonHeight + spacing), buttonWidth, buttonHeight);
        exitButton.setBounds(centerX, startY - 2 * (buttonHeight + spacing), buttonWidth, buttonHeight);

        //adding listeners to buttons
        //start button
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Starting the escape mission!");
                game.startGame(); //Calling the main game
            }
        });

        //tutorial button
        tutorialButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showTutorial(); //instructions
            }
        });

        //close button
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Closing game... thanks for playing!");
                Gdx.app.exit(); //exits
            }
        });

        // Add all buttons to the stage so they appear
        stage.addActor(startButton);
        stage.addActor(tutorialButton);
        stage.addActor(exitButton);
    }

    /**
     * Shows the tutorial window lil pop up might change later
     */
    private void showTutorial() {
        // Style for the close button (uses the same as main buttons)
        TextButton.TextButtonStyle closeButtonStyle = new TextButton.TextButtonStyle();
        int closeButtonWidth = 200;
        int closeButtonHeight = 60;

        closeButtonStyle.up = createPixelButton(closeButtonWidth, closeButtonHeight, BUTTON_MAIN);
        closeButtonStyle.over = createPixelButton(closeButtonWidth, closeButtonHeight, BUTTON_HOVER);
        closeButtonStyle.down = createPixelButton(closeButtonWidth, closeButtonHeight, BUTTON_MAIN);
        closeButtonStyle.font = buttonFont;

        // Style for the tutorial window
        Window.WindowStyle windowStyle = new Window.WindowStyle();
        windowStyle.titleFont = buttonFont;

        // Window background for the pop up
        Pixmap windowBg = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        windowBg.setColor(new Color(TUTORIAL_BG.r, TUTORIAL_BG.g, TUTORIAL_BG.b, 0.98f));
        windowBg.fill();
        windowStyle.background = new TextureRegionDrawable(new Texture(windowBg));
        windowBg.dispose();

        // Create the window with title
        Window tutorialWindow = new Window("HOW TO PLAY!!", windowStyle);
        tutorialWindow.setModal(true); // Blocks clicks to other buttons when open the ones behind it

        // Table organizes the content
        Table content = new Table();
        content.pad(40);

        // Style for the instruction text - dark blue for good contrast on light background
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = buttonFont;
        labelStyle.fontColor = BUTTON_HOVER;

        // All the game instructions can change or like add a interactive window maybe but needs the whole game to do
        content.add(new Label("WASD KEYS - to move around", labelStyle)).padBottom(20).row();
        content.add(new Label("Find keys to unlock doors", labelStyle)).padBottom(20).row();
        content.add(new Label("Try not to get caught before the time runs out!", labelStyle)).padBottom(20).row();
        content.add(new Label("get the best score possible", labelStyle)).padBottom(20).row();
        content.add(new Label("SMTH button TO INTERACT WITH OBJECTS", labelStyle)).padBottom(30).row();

        // Button to close the tutorial
        TextButton closeButton = new TextButton("Closed", closeButtonStyle);
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                tutorialWindow.remove(); // Makes the window disappear
            }
        });

        content.add(closeButton).width(closeButtonWidth).height(closeButtonHeight);
        tutorialWindow.add(content);

        // Size and center the window
        tutorialWindow.setSize(650, 500);
        tutorialWindow.setPosition(
            (Gdx.graphics.getWidth() - tutorialWindow.getWidth()) / 2,
            (Gdx.graphics.getHeight() - tutorialWindow.getHeight()) / 2
        );

        stage.addActor(tutorialWindow); // Show the window
    }

    /**
     * Main render loop - called 60 times per second, draws everything
     * delta Time since last frame
     */
    @Override
    public void render(float delta) {
        // Clear screen
        Gdx.gl.glClearColor(BACKGROUND.r, BACKGROUND.g, BACKGROUND.b, BACKGROUND.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Start drawing text and sprites
        game.batch.begin();

        // Draw the main game title
        String title = "ESCAPE THE UNI";
        GlyphLayout titleLayout = new GlyphLayout(titleFont, title);
        float titleX = (Gdx.graphics.getWidth() - titleLayout.width) / 2;
        float titleY = Gdx.graphics.getHeight() - 150;
        titleFont.draw(game.batch, titleLayout, titleX, titleY);

        game.batch.end();

        // Update and draw all UI elements
        stage.act(delta);
        stage.draw();
    }

    /**
     * Called when window is resized doesnt work .. yay
     */
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    /**
     * Called when this screen is showing
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage); // Enable clicking on our buttons
    }

    /**
     * Called when switching away from this screen
     */
    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null); // Stop listening for clicks
    }

    @Override
    public void pause() {}
    @Override
    public void resume() {}

    /**
     * Clean up memory - important for avoiding memory leaks!
     * Called when game closes or screen is permanently gone
     */
    @Override
    public void dispose() {
        stage.dispose();
        titleFont.dispose();
        buttonFont.dispose();
    }
}
