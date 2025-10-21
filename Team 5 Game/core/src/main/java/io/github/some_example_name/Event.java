package io.github.some_example_name;

public abstract class Event {
    private String name;
    private boolean isTriggered;
    private String location;

    public Event(String name, String location) {
        this.name = name;
        this.location = location;
        this.isTriggered = false;
    }

    public String getName() {
        return name;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public abstract void trigger();
}
