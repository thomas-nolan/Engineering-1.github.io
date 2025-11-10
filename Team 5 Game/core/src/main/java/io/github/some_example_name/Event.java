package io.github.some_example_name;

import com.badlogic.gdx.math.Rectangle;

/* This interface manages the multiple events that appear in the game
     * 
     * Each event is named and has a location.
     * 
*/
public interface Event {
    
    String getName(); 
    void setName(String name);
    boolean isTriggered();
    void setTriggered(boolean triggered);
    void trigger();
}
