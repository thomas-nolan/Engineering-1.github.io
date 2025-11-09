package io.github.some_example_name;
import com.badlogic.gdx.math.Rectangle;

/* A child of the abstract class Event
 * Responsible for the trip wire.
 * 
 * The trip wire locks the door in the dean's office
 * which requires a key to unlock again.
 */
public class Event_TripWire extends Event{
    private Rectangle tripWireZone;
    private Door doorToLock;

    /* Constructor */
    public Event_TripWire(String wire, Rectangle zone, Door door){
        super(wire, "Some location" );
        this.tripWireZone = zone;
        this.doorToLock = door;
    }

    @Override
    public void trigger(){
        if (!isTriggered()){
            doorToLock.lock();
            setTriggered(true);
        }
    }

    public boolean checkTrigger(Rectangle playerCollision){
        if(tripWireZone.overlaps(playerCollision)){
            trigger();
            return true;
        }
        return false;
    }

}
