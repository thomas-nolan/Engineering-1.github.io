package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Door {
	private Rectangle doorCollision;
    private boolean isLocked;
    private Texture texture;

    public Door(float x, float y, float width, float height, Texture texture) {
        this.doorCollision = new Rectangle(x, y, width, height);
        this.isLocked = true;
        this.texture = texture;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void lock() {
        if (!isLocked) {
            isLocked = true;
        }
    }

    public void unlock() {
        if (isLocked) {
            isLocked = false;
        }
    }

    public boolean collides(Rectangle playerCollision) {
    	return doorCollision.overlaps(playerCollision);
    }

    public void draw(SpriteBatch sprite) {
    	if (isLocked) {
    		sprite.draw(texture, doorCollision.x, doorCollision.y, doorCollision.width, doorCollision.height);
    	}
    }


}
