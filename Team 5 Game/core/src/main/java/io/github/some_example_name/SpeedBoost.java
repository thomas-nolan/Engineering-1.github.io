package io.github.some_example_name;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class SpeedBoost {
    private Texture texture;
    private Sprite boostSprite;
    private Vector2 position;
    private float collisionRadius;
    private boolean active;


    public SpeedBoost(Texture texture, float xPosition, float yPosition) {
        this.texture = texture;
        this.boostSprite = new Sprite(texture);
        this.position = new Vector2(xPosition, yPosition);
        this.collisionRadius = 10f;
        this.active = true;
    }

    public boolean checkCollision(Player player) {
        Vector2 playerPosition = player.getPosition();
        if (Math.abs(playerPosition.x - position.x) <= collisionRadius && Math.abs(playerPosition.y - position.y) <= collisionRadius) {
            return true;
        }
        return false;
    }

    public void draw(SpriteBatch batch) {
        if (this.active) {
            boostSprite.setPosition(position.x, position.y);
            boostSprite.draw(batch);
        }
    }

    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }

    public void deactivate() {
        this.active = false;
    }

    public boolean getActive() {
        return this.active;
    }
}
