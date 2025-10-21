package io.github.some_example_name;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Player {
    private Sprite sprite;
    private Vector2 position;
    private float speedModifier = 1.5f;
    private float playerSpeed = 20f * speedModifier; // Add modifier
    TiledMapTileLayer nonWalkable;

    // PLayer constructor
    public Player(Texture playerTexture, float startXPosition, float startYPosition, TiledMapTileLayer nonWalkableLayer) {
        sprite = new Sprite(playerTexture);
        position = new Vector2(startXPosition, startYPosition);
        sprite.setPosition(position.x, position.y);
        sprite.setSize(20, 20);
        this.nonWalkable = nonWalkableLayer;
    }

    // Movement and timer
    public void update() {
        float delta = Gdx.graphics.getDeltaTime();
        float newXPosition = position.x;
        float newYPosition = position.y;
        // Movement controls for arrow keys and WASD
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            newXPosition += playerSpeed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            newXPosition -= playerSpeed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            newYPosition += playerSpeed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            newYPosition -= playerSpeed * delta;
        }

        // Checks the new position is not a non-walkable surface (Wall, Table etc.)
        if (isWalkable(newXPosition, position.y)) {
            position.x = newXPosition;
        }
        if (isWalkable(position.x, newYPosition)) {
            position.y = newYPosition;
        }

        sprite.setPosition(position.x, position.y);
    }

    public boolean isWalkable(float xPosition, float yPosition) {
        int tileXPosition = (int) (xPosition / nonWalkable.getTileWidth());
        int tileYPosition = (int) (yPosition / nonWalkable.getTileHeight());

        TiledMapTileLayer.Cell cell = nonWalkable.getCell(tileXPosition, tileYPosition);
        if (cell != null && cell.getTile() != null) {
            return false;
        }
        else {
            return true;
        }
    }

    // Generates the player character on the game scene
    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }
    // Keeps the player character within the map
    public void clamp(float worldWidth, float worldHeight) {
        float width = sprite.getWidth();
        float height = sprite.getHeight();

        float clampX = MathUtils.clamp(sprite.getX(), 0, worldWidth - width);
        float clampY = MathUtils.clamp(sprite.getY(), 0, worldHeight - height);

        sprite.setPosition(clampX, clampY);
    }

    public void dispose() {
        // Dispose here
    }
}
