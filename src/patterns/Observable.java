package patterns;

import java.util.LinkedList;

/**
 * Created by andrei on 5/2/16.
 */
public abstract class Observable {
    private LinkedList<Observer> observers = new LinkedList<>();

    public void attach(Observer observer) {
        observers.add(observer);
    }

    public void detach(Observer observer) {
        observers.remove(observer);
    }

    protected void notifyObservers() {
        for (Observer o : observers) {
            o.notifyChange();
        }
    }
}
