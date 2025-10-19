package io.github.some_example_name;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Player {
    private Sprite sprite;
    private Vector2 position;
    private float playerSpeed = 10f; // Add modifier

    // PLayer constructor
    public Player(Texture playerTexture, float startXPosition, float startYPosition) {
        sprite = new Sprite(playerTexture);
        position = new Vector2(startXPosition, startYPosition);
        sprite.setPosition(position.x, position.y);
        sprite.setSize(20, 20);
    }

    // Movement and timer
    public void update() {
        float delta = Gdx.graphics.getDeltaTime();
        // Movement controls for arrow keys and WASD
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            position.x += playerSpeed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            position.x -= playerSpeed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            position.y += playerSpeed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            position.y -= playerSpeed * delta;
        }

        sprite.setPosition(position.x, position.y);
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
