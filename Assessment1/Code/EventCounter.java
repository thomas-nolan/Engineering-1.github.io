package io.github.some_example_name;

/**
 * Class that is used for tracking the number of triggered events in the game.
 */
public class EventCounter {
    private static int eventsCounter = 0;

    public static void incrementEventsCounter() {
        eventsCounter++;
        System.out.println(eventsCounter);
    }

    public static void resetEventsCounter() {
        eventsCounter = 0;
    }

    public static int getEventsCounter() {
        return eventsCounter;
    }
}
