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

/*Main Menu Class : Implements the Screen Interface from LibGDX
 *Class is called From the Main.java Class
 *Will show the user the the main menu and give three options: to start the game, instructions and exit.
 *The class Calls the GamePlay.Java Class when the start button is pressed
*/

public class MainMenu implements Screen {

    private final Main main; //To switch screens
    private Stage MainMenuStage; //To hold the elements - the buttons
    private BitmapFont buttonsFont;

    //Holds the images from the assets folder
    private Texture MainBackgroundTexture;
    private Texture startButtonTexture, tutorialButtonTexture, exitButtonTexture;
    private Texture hoverStartButtonTexture, hoverTutorialButtonTexture, hoverExitButtonTexture;
    private Texture tutorialBackgroundTexture, tutorialExitButtonTexture;

    /**
     * Constructor: MainMenu
     * @param game
     */
    public MainMenu(final Main game) {
        this.main = game;
        MakeFont(); //LibGDX rule
        generateAssets(); //Loading the images from the png files
        ShowScreen(); //Creating and placing the interactive elements (Buttons)
    }

    //Change in future as used when planning the window screening
    private void MakeFont() {
        buttonsFont = new BitmapFont();
        buttonsFont.getData().setScale(1.0f); //Set as small as not used currently
    }

    /**
     * GenerateAssets: Loads all the image files from the assets folder
     * and places them into a Texture
     */
    private void generateAssets(){
        //Loading the assets

        //Main backgrounds
        MainBackgroundTexture = new Texture(Gdx.files.internal("anotherSpritetest.png"));
        tutorialBackgroundTexture =  new Texture(Gdx.files.internal("TutorialPage.png"));

        //Main Menu Buttons
        startButtonTexture =  new Texture(Gdx.files.internal("StartButton.png"));
        tutorialButtonTexture =  new Texture(Gdx.files.internal("TutorialButton.png"));
        exitButtonTexture =  new Texture(Gdx.files.internal("ExitButton.png"));
        hoverStartButtonTexture =  new Texture(Gdx.files.internal("HoverStartButton.png"));
        hoverTutorialButtonTexture =  new Texture(Gdx.files.internal("HoverTutorialButton.png"));
        hoverExitButtonTexture =  new Texture(Gdx.files.internal("HoverExitButton.png"));

        //Tutorial Screen button (only a exit button)
        tutorialExitButtonTexture =  new Texture(Gdx.files.internal("CloseButton.png"));
    }


    /**IsMouseOverTexture: Creating a hover effect which will make it clearer for the user
     * what button is being interacted with.
     *
     * @param isMouseNotOverTexture Contains the XTexture when the mouse isnt over the button
     * @param isMouseOverTexture Contains the hoverXTexture when the mouse is over the button
     * @return the text button style for the button to be generated
     */
    private TextButton.TextButtonStyle IsMouseOverTexture(Texture isMouseNotOverTexture, Texture isMouseOverTexture) {

        //Creating the button conatiner: buttonStyle
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();

        //Setting the state of the button(.Up) Normal state
        buttonStyle.up = new TextureRegionDrawable(isMouseNotOverTexture);
        // (.over) the mouse is hovering over the button
        buttonStyle.over = new TextureRegionDrawable(isMouseOverTexture);
        // (.down) Buttons been pressed
        buttonStyle.down = new TextureRegionDrawable(isMouseNotOverTexture);


        buttonStyle.font = buttonsFont;
        return buttonStyle;
    }

    //Tutorial background
    /**Creates the button style for the exit button on the tutorial window
     * Very simple only has a small red X to indicate Exit.
     *
     * In future could add hover effects or make it an "EXIT" sign to make it clearer
     * @param texture the button image
     * @return the X button style
     */
    private TextButton.TextButtonStyle XTutorialButtonStyle(Texture texture) {
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(texture); //Just kept it one state
        buttonStyle.font = buttonsFont;
        return buttonStyle;
    }


    /**ShowScreen : This method is used to create the "canvas" in which you can add interactive elements
     *  I add buttons to this stage so i call them within, also contains the tutorial window
     */
    private void ShowScreen() {
        MainMenuStage = new Stage(new ScreenViewport()); //Adding the new stage (ScreenViewPort is for screen size) However currently you cant adjust the screen
        Gdx.input.setInputProcessor(MainMenuStage);
        //Sets up and creates the buttons
        setupMainMenuButtons();
    }



    //Setting up the buttons
    /**SetUpMainMenuButtons : Adding the three buttons to the Main menu stage which is just made
     * in the showScreen().
     * Also adding a Click Listener to the buttons so they can be interacted with
     */
    private void setupMainMenuButtons() {
        //Creating the Buttons (BackgroundTexture, state, position)
        TextButton startButton = createMainMenuButton(startButtonTexture, hoverStartButtonTexture, 0);
        TextButton tutorialButton = createMainMenuButton(tutorialButtonTexture, hoverTutorialButtonTexture, 1);
        TextButton exitButton = createMainMenuButton(exitButtonTexture, hoverExitButtonTexture, 2);

        //Adding Click listeners to each button
        startButton.addListener(createStartButtonListener());
        tutorialButton.addListener(createTutorialButtonListener());
        exitButton.addListener(createExitButtonListener());

        //Add the buttons to the stage
        MainMenuStage.addActor(startButton);
        MainMenuStage.addActor(tutorialButton);
        MainMenuStage.addActor(exitButton);
    }

   /**CreateMainMenuButton : Setting up the position for the main menu buttons
    *
    * @param normalTexture The normal state image Texture
    * @param hoverTexture The hover state image texture
    * @param position the position the buttons are appearing to be in (0, 1 2)
    * @return A button
    */
    private TextButton createMainMenuButton(Texture normalTexture, Texture hoverTexture, int position) {

        //Create a button: with hover effectes (normal :hover )
        TextButton.TextButtonStyle buttonStyle = IsMouseOverTexture(normalTexture, hoverTexture);
        TextButton button = new TextButton("", buttonStyle); //LibGDX

        //Calculate the center of the screen
        float centerX = (Gdx.graphics.getWidth() - 400) / 2;
        float startY = Gdx.graphics.getHeight() * 0.4f; //Try to abit above hald way down
        float yPos = startY - (position * (120 + 40)); //Position the buttons in order

        //Set the buttons postions and size of the button. All main menu buttons are the same size
        button.setBounds(centerX, yPos, 400, 120);
        return button;
    }

    //CLICK LISTENERS FOR THE MAIN MENU BUTTONS
    /**CreateStartButtonListener : Creates a clickListener to the start button
     * and if it returns true to clicked.
     * Then Call the nex screen startGame() in the main class
     * @return
     */
    private ClickListener createStartButtonListener() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {main.startGame();}
        };
    }

    /**CreateTutorialButtonListener: Creates a clickListener to teh Tutorial button
     * When true then it shows the tutorial window
     * @return
     */
    private ClickListener createTutorialButtonListener() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {showTutorial();}
        };
    }

    /**CreateExitButtonListener : creates a Click listener to the Exit button
     * When True then exit the app
     * @return
     */
    private ClickListener createExitButtonListener() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {Gdx.app.exit();}
        };
    }



    /**Show Tutorial : shows the tutorial window
     * Will contain the basic controls and the goal for the game
     * In future add more
     */
    private void showTutorial() {
        Window tutorialWindow = createTutorialWindow();
        //Create the exit button the window screen
        TextButton xButton = createTutorialXButton(tutorialWindow);

        //Add the Window and Button to the stage so its visiable
        tutorialWindow.addActor(xButton);
        MainMenuStage.addActor(tutorialWindow);
    }


    /**CreateTutorialWindow : Creates the tutorial little window pop up
     * Should have a btutorail background from the assets
     * @return the set window
     */
    private Window createTutorialWindow() {
        Window.WindowStyle tutorialStyle = new Window.WindowStyle();
        //Add the window Background
        tutorialStyle.background = new TextureRegionDrawable(tutorialBackgroundTexture);
        tutorialStyle.titleFont = buttonsFont; // Font required for window

        Window tutorialWindow = new Window("", tutorialStyle); //Left blank as can be seen
        tutorialWindow.setModal(true); //To prevent the user from interacting with the previous screen

        //Calculate where to put the window: I placed in the middle of the screen with the dimensions fit for the image
        float windowWidth = Gdx.graphics.getWidth() * 0.55f;
        float windowHeight = Gdx.graphics.getHeight() * 0.75f;
        float windowX = (Gdx.graphics.getWidth() - windowWidth) / 2;
        float windowY = (Gdx.graphics.getHeight() - windowHeight) / 2;

        //Set the size and position of the window
        tutorialWindow.setSize(windowWidth, windowHeight);
        tutorialWindow.setPosition(windowX, windowY);

        return tutorialWindow;
    }

    //Making the tutorial X button.
    /**CreateTutorialXButton : Creates the exit button for the tutoral to close it
     *
     * @param tutorialWindow The window the button is on "belongs to"
     * @return the set Exit button
     */
    private TextButton createTutorialXButton(Window tutorialWindow) {
        TextButton.TextButtonStyle xButtonStyle = XTutorialButtonStyle(tutorialExitButtonTexture);
        TextButton xButton = new TextButton("", xButtonStyle);

        //Get how big the button is I placed it into the top right corner of the window screen
        float xButtonX = tutorialWindow.getWidth() - 90;
        float xButtonY = tutorialWindow.getHeight() - 70;

        //Set the position and size of the button
        xButton.setBounds(xButtonX, xButtonY, 50, 50);
        //Adds a Click Listener
        xButton.addListener(createCloseButtonListener(tutorialWindow));

        return xButton;
    }

    //If the X button is interacted with then close the window
    /**CreateCloseButtonListener : Button listener to clicks for the exit button on the window
     *
     * @param tutorialWindow the window its on and clicked on to close it
     * @return
     */
    private ClickListener createCloseButtonListener(Window tutorialWindow) {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {tutorialWindow.remove();} //Removes the tutroial window if closed
        };
    }


    /**Render : Looping in the engine
     */
    @Override
    public void render(float delta) {
        main.batch.begin();
        //Draw the Main menu background
        main.batch.draw(MainBackgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        main.batch.end();

        //Update the buttons elements
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
        //Clean up all the texturesssss
        MainMenuStage.dispose();
        buttonsFont.dispose();
        MainBackgroundTexture.dispose();
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
