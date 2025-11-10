package io.github.some_example_name;

/**
 * Utility class for tracking triggered events in the game.
 */
public class EventTracker {
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
