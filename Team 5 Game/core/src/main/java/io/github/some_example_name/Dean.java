package io.github.some_example_name;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;



public class Dean extends Player{
    private float deanSpeed = 15f;
    TiledMapTileLayer nonWalkable;
    TiledMapTileLayer walls;
    TiledMapTileLayer corners;
    private Vector2 targetPosition;
    private float tolerance = 2f;
    private float roomX, roomY, roomWidth, roomHeight;
    private Rectangle deanRect;
    

    public Dean(Texture deanTexture, float startXPosition, float startYPosition, 
                Array<TiledMapTileLayer> nonWalkableLayer, TiledMapTileLayer wallLayer, 
                TiledMapTileLayer cornerLayer, float roomX, float roomY, float roomWidth, float roomHeight) {
        super(deanTexture, startXPosition, startYPosition, nonWalkableLayer, wallLayer, cornerLayer);
        this.roomX = roomX;
        this.roomY = roomY;
        this.roomWidth = roomWidth;
        this.roomHeight = roomHeight;
        deanRect = new Rectangle(startXPosition, startYPosition, 20, 20);

        targetPosition = new Vector2(
            roomX + (float)Math.random() * roomWidth,
            roomY + (float)Math.random() * roomHeight
        );
    }

    public void update() {
        // calc direction needed to get to target position
        float delta = Gdx.graphics.getDeltaTime();
        Vector2 direction = new Vector2(targetPosition.x - position.x, targetPosition.y - position.y);
        float distance = direction.len();

        if(distance < tolerance){
            targetPosition.set(
                roomX + (float)Math.random() * roomWidth,
                roomY + (float)Math.random() * roomHeight
            );
        }else{
            direction.nor();
            float newX = position.x + direction.x * deanSpeed * delta;
            float newY = position.y + direction.y * deanSpeed * delta;

            //checks if there is a collision
            if(isWalkable(newX, position.y)){
                position.x = newX;
            }
            if(isWalkable(position.x, newY)){
                position.y = newY;
            }
        }

        float clampedX = MathUtils.clamp(position.x, roomX, roomX + roomWidth - sprite.getWidth());
        float clampedY = MathUtils.clamp(position.y, roomY, roomY + roomHeight-sprite.getHeight());
        position.set(clampedX,clampedY);

        //update the sprite and rectangle position
        sprite.setPosition(position.x, position.y);
        deanRect.setPosition(position.x, position.y);
        
    }
    // if dean catches you
    public boolean checkCollision(Rectangle playerRect){
        return deanRect.overlaps(playerRect);
    }

}

