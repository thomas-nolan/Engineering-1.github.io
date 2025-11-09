package io.github.some_example_name;

import com.badlogic.gdx.math.Rectangle;

/* This abstract class manages the multiple events that appear in the game
     * 
     * Each event is named and has a location.
     * 
*/
public abstract class Event {
    private String name;
    private boolean isTriggered;
    
    private static int eventsCounter; 
    
    public Event(String name) {
        this.name = name;
        this.isTriggered = false;
    }

    public String getName() {
        return this.name;
    }

    protected void incrementEventsCounter () {
        eventsCounter++;
        System.out.println(eventsCounter);
    }

    public static void resetEventsCounter () {
        eventsCounter = 0;
    }


    public void setName(String name) {
        this.name = name;
    }

    public boolean isTriggered() {
        return isTriggered;
    }

    public void setTriggered(boolean triggered) {
        this.isTriggered = triggered;
    }


    public abstract void trigger();
}
