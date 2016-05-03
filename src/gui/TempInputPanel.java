package gui;

import refrigerator.Refrigerator;
import refrigerator.RefrigeratorComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

/**
 * Created by andrei on 5/3/16.
 */
public class TempInputPanel extends JPanel {
    private final static String[] labels = {"Desired freezer temp", "Desired fridge temp", "Room temp"};
    private final static String[] buttonCaptions = {"Set freezer temp", "Set fridge temp", "Set room temp"};
    private JButton[] buttons = new JButton[3];
    private JFormattedTextField[] textInputs = new JFormattedTextField[3];
    private Refrigerator refrigerator;

    public TempInputPanel(final Refrigerator refrigerator) {
        super(new SpringLayout());

        this.refrigerator = refrigerator;
        SpringLayout layout = (SpringLayout) getLayout();

        JLabel lastLabel = null;
        for (int i = 0; i < 3; ++i) {
            JLabel l = new JLabel(labels[i], JLabel.TRAILING);
            this.add(l);
            textInputs[i] = new JFormattedTextField(NumberFormat.getNumberInstance());
            textInputs[i].setColumns(10);
            l.setLabelFor(textInputs[i]);
            this.add(textInputs[i]);
            buttons[i] = new JButton(buttonCaptions[i]);
            this.add(buttons[i]);

            if (lastLabel != null) {
                layout.putConstraint(SpringLayout.NORTH, l, 30, SpringLayout.SOUTH, lastLabel);
                layout.putConstraint(SpringLayout.EAST, l, 0, SpringLayout.EAST, lastLabel);
            } else {
                layout.putConstraint(SpringLayout.WEST, l, 5, SpringLayout.WEST, this);
                layout.putConstraint(SpringLayout.NORTH, l, 30, SpringLayout.NORTH, this);
            }

            layout.putConstraint(SpringLayout.WEST, textInputs[i], 5, SpringLayout.EAST, l);
            layout.putConstraint(SpringLayout.NORTH, textInputs[i], 0, SpringLayout.NORTH, l);
            layout.putConstraint(SpringLayout.WEST, buttons[i], 5, SpringLayout.EAST, textInputs[i]);
            layout.putConstraint(SpringLayout.NORTH, buttons[i], 0, SpringLayout.NORTH, textInputs[i]);

            lastLabel = l;
        }

        RefrigeratorComponent refrigeratorComponents[] = {refrigerator.getFreezer(), refrigerator.getFridge()};
        for (int i = 0; i < 2; ++i) {
            final JFormattedTextField textInput = textInputs[i];
            final RefrigeratorComponent refrigeratorComponent = refrigeratorComponents[i];
            buttons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    if (!refrigeratorComponent.setDesiredTemp(Integer.parseInt(textInput.getText()))) {
                        textInput.setText(refrigeratorComponent.getCurrentTemp().toString());
                    }
                }
            });
        }

        buttons[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!refrigerator.setRoomTemperature(Integer.parseInt(textInputs[2].getText()))) {
                    textInputs[2].setText(refrigerator.getRoomTemperature().toString());
                }
            }
        });

        textInputs[0].setText(refrigerator.getFreezer().getCurrentTemp().toString());
        textInputs[1].setText(refrigerator.getFridge().getCurrentTemp().toString());
        textInputs[2].setText(refrigerator.getRoomTemperature().toString());
    }
}
