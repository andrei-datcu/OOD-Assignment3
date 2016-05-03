package gui;

import patterns.Observer;
import refrigerator.RefrigeratorComponent;

import javax.swing.*;
import java.awt.*;

/**
 * Created by andrei on 5/3/16.
 */
public class StatusPanel extends JPanel implements Observer {

    private JLabel currentTemp, unitStatus;
    private RefrigeratorComponent refrigeratorComponent;

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
