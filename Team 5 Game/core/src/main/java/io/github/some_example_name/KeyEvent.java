package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/* A class for the key item
 *
 * The key (keycard) is located in the map and is
 * necessary to complete the game.
 *
 * It unlocks the exit door in the Dean's office.
 * It disappears once activated
 */
public class KeyEvent extends Event {
    private Rectangle keyCollision;
    private Texture texture;

    /* Constructor
     * @param x - Position on x-axis
     * @param y - Position on y-axis
     * @param width - Width of the key
     * @param height - height of the key
     * @param texture - The texture of the key (keycard)
     */
    public KeyEvent(String name, Rectangle keyZone, Texture texture) {
        super(name);
        this.keyCollision = keyZone;
        this.texture = texture;
        setTriggered(false);
    }


    // Checks for player collsion
    public boolean collides(Rectangle playerCollision) {
        return keyCollision.overlaps(playerCollision);
    }

    // Only draws item if it has not been collected
    public void draw(SpriteBatch sprite) {
        if (!isTriggered()) {
            sprite.draw(texture, keyCollision.x, keyCollision.y, keyCollision.width, keyCollision.height);
        }
    }

    @Override
    public void trigger() {
        if (!isTriggered()) {
            setTriggered(true);
            incrementEventsCounter();
        }
    }
}
