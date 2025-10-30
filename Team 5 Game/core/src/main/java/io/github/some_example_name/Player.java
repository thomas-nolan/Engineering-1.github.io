package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

public class Player {
    private Sprite sprite;
    private Vector2 position;
    private float speedModifier = 1.5f;
    private float playerSpeed = 20f * speedModifier;
    private Rectangle playerCollision;

    float score;

    TiledMapTileLayer nonWalkable;
    TiledMapTileLayer walls;
    TiledMapTileLayer corners;

    // Player constructor
    public Player(Texture playerTexture, float startXPosition, float startYPosition,
                  TiledMapTileLayer nonWalkableLayer, TiledMapTileLayer wallLayer, TiledMapTileLayer cornerLayer) {
        sprite = new Sprite(playerTexture);
        position = new Vector2(startXPosition, startYPosition);
        sprite.setPosition(position.x, position.y);
        sprite.setSize(20, 20);

        score = 0f;

        this.nonWalkable = nonWalkableLayer;
        this.walls = wallLayer;
        this.corners = cornerLayer;

        playerCollision = new Rectangle(position.x, position.y, sprite.getWidth(), sprite.getHeight());
    }

    // Movement and collision
    public void update(List<Door> doors) {
        float delta = Gdx.graphics.getDeltaTime();

        float moveX = 0;
        float moveY = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
        	moveX += playerSpeed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)  || Gdx.input.isKeyPressed(Input.Keys.A)) {
        	moveX -= playerSpeed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)    || Gdx.input.isKeyPressed(Input.Keys.W)) {
        	moveY += playerSpeed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)  || Gdx.input.isKeyPressed(Input.Keys.S)) {
        	moveY -= playerSpeed * delta;
        }

        float newXPosition = position.x + moveX;
        float newYPosition = position.y + moveY;

        // door collision
        boolean blocked = false;
        Rectangle nextPosition = new Rectangle(playerCollision);
        nextPosition.x = newXPosition;
        nextPosition.y = newYPosition;

        for (Door door : doors) {
            if (door.isLocked() && door.collides(nextPosition)) {
                blocked = true;
                break;
            }
        }

        if (!blocked) {
            if (isWalkable(newXPosition, position.y)) position.x = newXPosition;
            if (isWalkable(position.x, newYPosition)) position.y = newYPosition;
        }

        sprite.setPosition(position.x, position.y);
        playerCollision.setPosition(position.x, position.y);
    }

    public boolean isWalkable(float xPosition, float yPosition) {
        int tileXPosition = (int) (xPosition / nonWalkable.getTileWidth());
        int tileYPosition = (int) (yPosition / nonWalkable.getTileHeight());

        TiledMapTileLayer.Cell cell1 = nonWalkable.getCell(tileXPosition, tileYPosition);
        TiledMapTileLayer.Cell cell2 = walls.getCell(tileXPosition, tileYPosition);
        boolean isNonWalkable = cell1 != null && cell1.getTile() != null;
        boolean isWall = cell2 != null && cell2.getTile() != null;
        if (isNonWalkable || isWall) {
        	return false;
        }
        else {
        	return true;
        }
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public void clamp(float worldWidth, float worldHeight) {
        float width = sprite.getWidth();
        float height = sprite.getHeight();

        float clampX = MathUtils.clamp(sprite.getX(), 0, worldWidth - width);
        float clampY = MathUtils.clamp(sprite.getY(), 0, worldHeight - height);

        sprite.setPosition(clampX, clampY);
        playerCollision.setPosition(clampX, clampY);
    }

    public Vector2 getPosition() {
        return position;
    }

    public Rectangle getCollision() {
        return playerCollision;
    }

    public void dispose() {
        // Dispose if needed
    }
}
