package gui;

import patterns.Observer;
import refrigerator.RefrigeratorComponent;

import javax.swing.*;
import java.awt.*;

/**
 * Panel that show status for a refrigerator component (either fridge or freezer)
 * It automatically gets updated whenever the state of the component gets updated
 * Please notice that this implememts the Observer pattern
 */
public class StatusPanel extends JPanel implements Observer {

    private JLabel currentTemp, unitStatus;
    private RefrigeratorComponent refrigeratorComponent;

    /**
     * @param refrigeratorComponent The components this shows the status for
     * @param componentName The component name (fridge or freezer). Used for label captions
     */
    public StatusPanel(RefrigeratorComponent refrigeratorComponent, String componentName) {
        super(new GridLayout(2, 2, 10, 10));

        JLabel tempDesc = new JLabel(componentName + " temp", JLabel.RIGHT);
        JLabel statusDesc = new JLabel(componentName, JLabel.RIGHT);

        this.refrigeratorComponent = refrigeratorComponent;
        currentTemp = new JLabel("", JLabel.LEFT);
        unitStatus = new JLabel("", JLabel.LEFT);

        add(tempDesc);
        add(currentTemp);
        add(statusDesc);
        add(unitStatus);
        notifyChange();
        refrigeratorComponent.attach(this);
    }

    @Override
    public void notifyChange() {
        currentTemp.setText(refrigeratorComponent.getCurrentTemp().toString());
        unitStatus.setText(refrigeratorComponent.isCoolingUnitRunning() ? "cooling" : "idle");
    }
}
