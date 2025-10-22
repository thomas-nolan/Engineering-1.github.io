package io.github.some_example_name;

public class KeyEvent extends Event {
	private Door door;
	
	
	public KeyEvent(String name, String location, Door door) {
		super(name, location);
		this.door = door;
	}
	
	public Door getDoorId() {
		return door;
	}
	
	public void setDoorId(Door door) {
		this.door = door;
	}
	
	@Override
	public void trigger() {
		if (!isTriggered()) {
			door.unlock();
			setTriggered(true);
		}
	}
}
