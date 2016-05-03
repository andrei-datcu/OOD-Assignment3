package patterns;

import java.util.LinkedList;

/**
 * Abstract class for an Observable item
 */
public abstract class Observable {
    private LinkedList<Observer> observers = new LinkedList<>();

    /**
     * Attach a new observer for changes to the current instance
     * @param observer The observer to attach
     */
    public void attach(Observer observer) {
        observers.add(observer);
    }

    /**
     * Detach the observer
     * @param observer The observer to detach
     */
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Notify all observers that a change in the current instance has been produced
     * This should be manually called whenever a monitored property in the current instance has changed
     */
    protected void notifyObservers() {
        for (Observer o : observers) {
            o.notifyChange();
        }
    }
}
