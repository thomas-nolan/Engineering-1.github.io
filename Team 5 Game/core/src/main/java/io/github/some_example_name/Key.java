package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Key {
    private Vector2 keyPosition;
    private Rectangle keyCollision;
    private boolean collected;
    private Texture texture;

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

    public boolean collides(Rectangle playerCollision) {
        return keyCollision.overlaps(playerCollision);
    }

    public void draw(SpriteBatch sprite) {
        if (!collected) {
            sprite.draw(texture, keyCollision.x, keyCollision.y, keyCollision.width, keyCollision.height);
        }
    }
}