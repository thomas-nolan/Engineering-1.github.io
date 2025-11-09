package io.github.some_example_name;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/* A class for the SpeedBoost item.
 * 
 * This item looks like a coffee cup and is 
 * located somewhere in the game
 * When the player touches it, their speed is doubled
 * for the rest of the game
 * It disappears once activated
 */
public class SpeedBoostEvent extends Event{
    private Texture texture;
    private Sprite boostSprite;
    private Vector2 position;
    private float collisionRadius;
    private boolean active;

    /* Constructor for the SpeedBoost
     * @param texture - The texture for the item (coffee cup)
     * @param xPostion - The item's position on the x-axis
     * @param yPostion - The item's postion on the y-axis
     */
    public SpeedBoostEvent(String name, Texture texture, float xPosition, float yPosition) {
        super(name);
        this.texture = texture;
        this.boostSprite = new Sprite(texture);
        this.position = new Vector2(xPosition, yPosition);
        this.collisionRadius = 10f;
        this.active = true;
    }

    // Checks for player collision
    public boolean checkCollision(Player player) {
        Vector2 playerPosition = player.getPosition();
        if (Math.abs(playerPosition.x - position.x) <= collisionRadius && Math.abs(playerPosition.y - position.y) <= collisionRadius) {
            return true;
        }
        return false;
    }

    public void draw(SpriteBatch batch) {
        if (!isTriggered()) {
            boostSprite.setPosition(position.x, position.y);
            boostSprite.draw(batch);
        }
    }

    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }

    // Removes item once activated
    public void deactivate() {
        setTriggered(false);
    }

    public boolean getActive() {
        return isTriggered();
    }

    @Override
    public void trigger() {
        setTriggered(true);
        incrementEventsCounter();
    }
}
