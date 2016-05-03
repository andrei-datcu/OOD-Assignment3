package gui;

import refrigerator.Refrigerator;
import refrigerator.RefrigeratorComponent;
import refrigerator.RefrigeratorConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by andrei on 5/3/16.
 */
public class MainWindow extends JFrame {

    private Refrigerator refrigerator;

    public MainWindow(Refrigerator refrigerator) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("OOP Assignment 3");
        this.setMinimumSize(new Dimension(500, 350));
        this.refrigerator = refrigerator;
        initLayout();
    }

    private void addDoorButtons(JPanel container, String componentName, final RefrigeratorComponent refrigeratorComponent) {
        final JButton openDoorButton = new JButton("Open " + componentName + " door");
        final JButton closeDoorButton = new JButton("Close " + componentName + " door");
        closeDoorButton.setEnabled(false);

        openDoorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                refrigeratorComponent.setDoor(false);
                openDoorButton.setEnabled(false);
                closeDoorButton.setEnabled(true);
            }
        });

        closeDoorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                refrigeratorComponent.setDoor(true);
                closeDoorButton.setEnabled(false);
                openDoorButton.setEnabled(true);
            }
        });
        container.add(openDoorButton);
        container.add(closeDoorButton);
    }

    private void initLayout() {
        Container contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
        contentPane.add(new TempInputPanel(refrigerator));

        JPanel fridgeDoorPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        addDoorButtons(fridgeDoorPanel, "fridge", refrigerator.getFridge());
        addDoorButtons(fridgeDoorPanel, "freezer", refrigerator.getFreezer());
        contentPane.add(fridgeDoorPanel);
        contentPane.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPane.add(new JLabel("Status"));
        contentPane.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel statusPanels = new JPanel();
        statusPanels.setLayout(new BoxLayout(statusPanels, BoxLayout.LINE_AXIS));
        statusPanels.add(new StatusPanel(refrigerator.getFridge(), "Fridge"));
        statusPanels.add(new StatusPanel(refrigerator.getFreezer(), "Freezer"));
        contentPane.add(statusPanels);
    }

    public static void main(String args[]) {
        MainWindow mainWindow = new MainWindow(new Refrigerator(new RefrigeratorConfig("wgateva")));
        mainWindow.setVisible(true);
    }
}
