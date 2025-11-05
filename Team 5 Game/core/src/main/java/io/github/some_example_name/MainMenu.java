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
public class MainMenu implements Screen {

    private final Main main;
    private Stage MainMenuStage; //Stage for the UI
    private BitmapFont mainTitleFont, buttonsFont; // Fonts for different text sizes

    //Colours
    private final Color BackgroundColour = new Color(0.219f, 0.110f, 0.165f, 1f); // Main background
    private final Color BackgroundTutorialColour = new Color(0.300f, 0.110f, 0.265f, 1f); // tutorial background
    private final Color ButtonColour = new Color(0.639f, 0.345f, 0.427f, 1f); //buttons (N)
    private final Color ButtonInteractColour = new Color(0.941f, 0.396f, 0.325f, 1f); //buttons (A)
    private final Color TitleTextColour = new Color(1f, 0.957f, 0.455f, 1f); // Title

    public MainMenu(final Main game) {
        this.main = game;
        getTextPropoties();
        SetScreen();
    }


    //Sets up the fonts with all of their propoties
    private void getTextPropoties() {
        //Title font
        //Change in future to a bitmap as its tiny and blury
        mainTitleFont = new BitmapFont();
        mainTitleFont.getData().setScale(4.0f); //Size
        mainTitleFont.setColor(TitleTextColour);

        //buttons
        buttonsFont = new BitmapFont();
        buttonsFont.getData().setScale(2.0f); //Size
        buttonsFont.setColor(Color.WHITE); //Buttons
    }


    // Creates the buttons on the screen with the size and stuff
    private TextureRegionDrawable MakeButton(int width, int height, Color color) {
        // Create a rectangle
        Pixmap ButtonMap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        ButtonMap.setColor(color);
        ButtonMap.fill();

        Texture texture = new Texture(ButtonMap);
        ButtonMap.dispose();
        return new TextureRegionDrawable(texture);
    }

    //Makes the screen
    private void SetScreen() {
        MainMenuStage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(MainMenuStage); // Make sure our buttons can be interacted with


        //main menu buttons
        TextButton.TextButtonStyle buttonPropoties = new TextButton.TextButtonStyle();
        int buttonWidth = 300;
        int buttonHeight = 80;

        //get center position
        float centerX = (Gdx.graphics.getWidth() - buttonWidth) / 2;
        float startY = Gdx.graphics.getHeight() * 0.4f;
        float spacing = 30;

        // Button states so the user knows when they hover over this. "Accessability ig"
        buttonPropoties.up = MakeButton(buttonWidth, buttonHeight, ButtonColour);    // Normal purple
        buttonPropoties.over = MakeButton(buttonWidth, buttonHeight, ButtonInteractColour); // Hover dark blue
        buttonPropoties.down = MakeButton(buttonWidth, buttonHeight, ButtonColour);  // Clicked (same as normal)
        buttonPropoties.font = buttonsFont;

        // Create the buttons text
        TextButton startButton = new TextButton("START", buttonPropoties);
        TextButton tutorialButton = new TextButton("TUTORIAL", buttonPropoties);
        TextButton exitButton = new TextButton("EXIT", buttonPropoties);


        //set the boundries
        startButton.setBounds(centerX, startY, buttonWidth, buttonHeight);
        tutorialButton.setBounds(centerX, startY - (buttonHeight + spacing), buttonWidth, buttonHeight);
        exitButton.setBounds(centerX, startY - 2 * (buttonHeight + spacing), buttonWidth, buttonHeight);

        //adding listeners to buttons
        //start button
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Start button has been interacted with");
                main.startGame(); //Calling the main game
            }
        });

        //tutorial button
        tutorialButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {showTutorial();}
        });

        //close button
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                System.out.println("Exit button interacted with"); //debugging
                Gdx.app.exit();
            }
        });

        // Add all buttons to the stage so they appear
        MainMenuStage.addActor(startButton);
        MainMenuStage.addActor(tutorialButton);
        MainMenuStage.addActor(exitButton);
    }


    // Shows the tutorial window lil pop up might change later
    private void showTutorial() {
        // Style for the close button (uses the same as main buttons)
        TextButton.TextButtonStyle Exitbuttonpropoties = new TextButton.TextButtonStyle();
        int closeButtonWidth = 200;
        int closeButtonHeight = 60;

        Exitbuttonpropoties.up = MakeButton(closeButtonWidth, closeButtonHeight, ButtonColour);
        Exitbuttonpropoties.over = MakeButton(closeButtonWidth, closeButtonHeight, ButtonInteractColour);
        Exitbuttonpropoties.down = MakeButton(closeButtonWidth, closeButtonHeight, ButtonColour);
        Exitbuttonpropoties.font = buttonsFont;

        // Style for the tutorial window
        Window.WindowStyle tutorialPropoties = new Window.WindowStyle();
        tutorialPropoties.titleFont = buttonsFont;

        // Window background for the pop up
        Pixmap windowBackground = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        windowBackground.setColor(new Color(BackgroundTutorialColour.r, BackgroundTutorialColour.g, BackgroundTutorialColour.b, 0.98f));
        windowBackground.fill();
        tutorialPropoties.background = new TextureRegionDrawable(new Texture(windowBackground));
        windowBackground.dispose();

        // Create the window with title
        Window tutorialWindow = new Window("Tutorial", tutorialPropoties);
        tutorialWindow.setModal(true); // Blocks clicks to other buttons when open the ones behind it

        // Table organizes the content
        Table TableLayout = new Table();
        TableLayout.pad(40);

        // Style for the instruction text - dark blue for good contrast on light background
        Label.LabelStyle Labels = new Label.LabelStyle();
        Labels.font = buttonsFont;
        Labels.fontColor = ButtonInteractColour;

        // All the game instructions can change or like add a interactive window maybe but needs the whole game to do
        TableLayout.add(new Label("WASD KEYS - to move around", Labels)).padBottom(20).row();
        TableLayout.add(new Label("Find keys to unlock doors", Labels)).padBottom(20).row();
        TableLayout.add(new Label("Try not to get caught before the time runs out!", Labels)).padBottom(20).row();
        TableLayout.add(new Label("get the best score possible", Labels)).padBottom(20).row();
        TableLayout.add(new Label("SMTH button to interact with the game", Labels)).padBottom(30).row();

        // Button to close the tutorial
        TextButton ExitButton = new TextButton("Closed", Exitbuttonpropoties);
        ExitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) { tutorialWindow.remove();}
        });

        TableLayout.add(ExitButton).width(closeButtonWidth).height(closeButtonHeight);
        tutorialWindow.add(TableLayout);
        // Size and center the window
        tutorialWindow.setSize(650, 500);
        tutorialWindow.setPosition(
            (Gdx.graphics.getWidth() - tutorialWindow.getWidth()) / 2,
            (Gdx.graphics.getHeight() - tutorialWindow.getHeight()) / 2
        );
        MainMenuStage.addActor(tutorialWindow); // Show the window
    }

    //loops all the userinterface
    @Override
    public void render(float delta) {
        // Clear screen
        Gdx.gl.glClearColor(BackgroundColour.r, BackgroundColour.g, BackgroundColour.b, BackgroundColour.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Start drawing text and sprites
        main.batch.begin();

        // Draw the main game title
        String title = "!ESCAPE FROM YORK!";
        GlyphLayout titleLayout = new GlyphLayout(mainTitleFont, title);
        float titleX = (Gdx.graphics.getWidth() - titleLayout.width) / 2;
        float titleY = Gdx.graphics.getHeight() - 150;
        mainTitleFont.draw(main.batch, titleLayout, titleX, titleY);

        main.batch.end();

        // Update and draw all UI elements
        MainMenuStage.act(delta);
        MainMenuStage.draw();
    }

    @Override
    public void resize(int width, int height) {}
    @Override
    public void show() { Gdx.input.setInputProcessor(MainMenuStage); }
    @Override
    public void hide() {}

    @Override
    public void pause() {}
    @Override
    public void resume() {}

    @Override
    public void dispose() {
        MainMenuStage.dispose();
        mainTitleFont.dispose();
        buttonsFont.dispose();
    }
}
