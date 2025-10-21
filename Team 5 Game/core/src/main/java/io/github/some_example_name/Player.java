package io.github.some_example_name;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private Sprite sprite;
    private Vector2 position;
    private float playerSpeed = 10f; // Add modifier
    private Rectangle playerCollision;

    // PLayer constructor
    public Player(Texture playerTexture, float startXPosition, float startYPosition) {
        sprite = new Sprite(playerTexture);
        position = new Vector2(startXPosition, startYPosition);
        sprite.setPosition(position.x, position.y);
        sprite.setSize(20, 20);
        playerCollision = new Rectangle(position.x, position.y, sprite.getWidth(), sprite.getHeight());
    }

    // Movement and timer
    public void update(List<Door> doors) {
        float delta = Gdx.graphics.getDeltaTime();

        float moveX = 0;
        float moveY = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) moveX += playerSpeed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)  || Gdx.input.isKeyPressed(Input.Keys.A)) moveX -= playerSpeed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.UP)    || Gdx.input.isKeyPressed(Input.Keys.W)) moveY += playerSpeed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)  || Gdx.input.isKeyPressed(Input.Keys.S)) moveY -= playerSpeed * delta;

        // block from moving through locked doors
        // calculate next position before moving to see if collides with the door
        boolean blocked = false;
        
        Rectangle nextPosition = new Rectangle(playerCollision);
        nextPosition.x += moveX;
        nextPosition.y += moveY;

        for (Door door : doors) {
            if (door.isLocked() && door.collides(nextPosition)) {
                blocked = true;
            }
        }

        if (!blocked) {
        	position.x += moveX;
        	position.y += moveY;
        }

        // Update sprite and bounds
        sprite.setPosition(position.x, position.y);
        playerCollision.setPosition(position.x, position.y);
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
        playerCollision.setPosition(clampX, clampY);
    }

    public void dispose() {
        // Dispose here
    }
    
    public Vector2 getPosition() {
    	return position;
    }
    
    public Rectangle getCollision() {
    	return playerCollision;
    }
}
