package io.github.some_example_name;
import com.badlogic.gdx.math.Rectangle;

/* A child of the abstract class Event
 * Responsible for the trip wire.
 * 
 * The trip wire locks the door in the dean's office
 * which requires a key to unlock again.
 */
public class TripwireEvent implements Event{
    private String name;
    private boolean isTriggered;
    private Rectangle tripWireZone;
    private Door doorToLock;

    /* Constructor */
    public TripwireEvent(String name, Rectangle zone, Door door){
        this.name = name;
        this.tripWireZone = zone;
        this.doorToLock = door;
        this.isTriggered = false;
    }

    @Override
    public void trigger(){
        if (!isTriggered){
            doorToLock.lock();
            EventCounter.incrementEventsCounter();
            isTriggered = true;
        }
    }

    public boolean checkTrigger(Rectangle playerCollision){
        if(tripWireZone.overlaps(playerCollision)){
            trigger();
            return true;
        }
        return false;
    }

    // Interface methods
    @Override public String getName() { return name; }
    @Override public void setName(String name) { this.name = name; }
    @Override public boolean isTriggered() { return isTriggered; }
    @Override public void setTriggered(boolean triggered) { this.isTriggered = triggered; }
}
