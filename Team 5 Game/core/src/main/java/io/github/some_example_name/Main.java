package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//This class is called from lwjgl3->src->java... _> Lwjgl3Launcher.java
//Mimi - I redid this class and made it to be functional to a actual "main"
//in which the main menu is then called first.
public class Main extends Game {
    public SpriteBatch batch;
    public boolean escaped;
    public int score;

    @Override
    public void create() {
        batch = new SpriteBatch();
        //Calling the new class here
        setScreen(new MainMenu(this)); // Start with the menu
    }

    @Override
    public void render() { super.render(); }

    @Override
    public void dispose() {
        batch.dispose();
        if (getScreen() != null) { getScreen().dispose();}
    }

    // Method to start the game
    public void startGame() {setScreen(new GamePlay(this)); }

    public void endGame() {setScreen(new EndGameScreen(this, false, 0));}




}
