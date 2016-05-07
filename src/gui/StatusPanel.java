package gui;

import refrigerator.RefrigeratorComponent;
import refrigerator.Signals;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Panel that shows status for a refrigerator component (either fridge or freezer)
 * It automatically gets updated whenever the state of the component gets updated
 * This implements the Observer pattern
 */
class StatusPanel extends JPanel implements Observer {

    private JLabel currentTemp, unitStatus, doorStatus;

    /**
     * @param refrigeratorComponent The components this shows the status for
     * @param componentName The component name (fridge or freezer). Used for label captions
     */
    StatusPanel(RefrigeratorComponent refrigeratorComponent, String componentName) {
        super(new GridLayout(3, 2, 10, 10));

        JLabel doorDesc = new JLabel(componentName + " door", JLabel.RIGHT);
        JLabel tempDesc = new JLabel(componentName + " temp", JLabel.RIGHT);
        JLabel statusDesc = new JLabel(componentName, JLabel.RIGHT);

        doorStatus = new JLabel("", JLabel.LEFT);
        currentTemp = new JLabel("", JLabel.LEFT);
        unitStatus = new JLabel("", JLabel.LEFT);

        add(doorDesc);
        add(doorStatus);
        add(tempDesc);
        add(currentTemp);
        add(statusDesc);
        add(unitStatus);
        setIdle();
        setCurrentTemp(refrigeratorComponent);
        setDoorClosed();
        refrigeratorComponent.addObserver(this);
    }

    @Override
    public void update(Observable observable, Object o) {
        if (o.equals(Signals.COOLING)) {
            setCooling();
        } else if (o.equals(Signals.IDLE)) {
            setIdle();
        } else if (o.equals(Signals.TEMP_CHANGED)) {
            setCurrentTemp((RefrigeratorComponent) observable);
        } else if (o.equals(Signals.DOOR_OPENED)) {
            setDoorOpened();
        } else if (o.equals(Signals.DOOR_CLOSED)) {
            setDoorClosed();
        } else {
            throw new RuntimeException("Unknown signal");
        }
    }

    private void setCooling() {
        unitStatus.setText("cooling");
    }

    private void setIdle() {
        unitStatus.setText("idle");
    }

    private void setCurrentTemp(RefrigeratorComponent refrigeratorComponent) {
        currentTemp.setText(refrigeratorComponent.getCurrentTemp().toString());
    }

    private void setDoorOpened() {
        doorStatus.setText("opened");
    }

    private void setDoorClosed() {
        doorStatus.setText("closed");
    }
}
