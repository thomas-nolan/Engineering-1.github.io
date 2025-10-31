package io.github.some_example_name;
import com.badlogic.gdx.math.Rectangle;

public class Event_TripWire extends Event{
    private Rectangle tripWireZone;
    private Door doorToLock;

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
