package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/* A class for the key item
 * 
 * The key (keycard) is located in the map and is 
 * necessary to complete the game.
 * 
 * It unlocks the exit door in the Dean's office.
 * It disappears once activated
 */
public class Key {
    private Vector2 keyPosition;
    private Rectangle keyCollision;
    private boolean collected;
    private Texture texture;

    /* Constructor
     * @param x - Position on x-axis
     * @param y - Position on y-axis
     * @param width - Width of the key
     * @param height - height of the key
     * @param texture - The texture of the key (keycard)
     */
    public Key(float x, float y, float width, float height, Texture texture) {
        this.keyPosition = new Vector2(x, y);
        this.keyCollision = new Rectangle(x, y, width, height);
        this.collected = false;
        this.texture = texture;
    }

    public boolean isCollected() {
        return collected;
    }

    public void collect() {
        collected = true;
    }

    // Checks for player collsion
    public boolean collides(Rectangle playerCollision) {
        return keyCollision.overlaps(playerCollision);
    }

    // Only draws item if it has not been collected
    public void draw(SpriteBatch sprite) {
        if (!collected) {
            sprite.draw(texture, keyCollision.x, keyCollision.y, keyCollision.width, keyCollision.height);
        }
    }
}