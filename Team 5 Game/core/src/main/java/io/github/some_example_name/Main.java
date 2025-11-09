package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/* Main class of the application. */
public class Main extends Game {
	public SpriteBatch batch;
    @Override
    public void create() {

    	batch = new SpriteBatch();
        //Calling the new class here
        setScreen(new MainMenu(this)); // Start with the menu

    }

    @Override
    public void render() {
    	super.render();
    }

  //This function disposes of application resources freeing up memory
    @Override
    public void dispose() {
        batch.dispose();
        if (getScreen() != null) {
        	getScreen().dispose();
        }
    }

    // Method to start the game
    public void startGame() {setScreen(new GamePlay(this)); }

    public void endGame() {setScreen(new EndGameScreen(this, false, 0));}

    public void winGame(int points) {setScreen(new EndGameScreen(this, true, points));}

}
