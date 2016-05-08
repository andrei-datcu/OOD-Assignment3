package refrigerator;

import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Simple tick clock. Tick interval is statically configurable
 */
class Clock extends Observable {
    // minimum interval in which the state changes in MILLISECONDS -- defaults to 1 minute
    // change this for debug / testing purposes
    private static final long TICK_INTERVAL = 1000;
    private Timer tickTimer = new Timer();
    private static Clock instance;

    /**
     * Schedule regular ticks
     */
    private Clock() {
        tickTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                setChanged();
                notifyObservers(Events.CLOCK_TICKED_EVENT);
            }
        }, TICK_INTERVAL, TICK_INTERVAL);
    }

    /**
     * Get the instance
     *
     * @return returns the Clock
     */
    static Clock instance() {
        if (instance == null) {
            instance = new Clock();
        }
        return instance;
    }
}
