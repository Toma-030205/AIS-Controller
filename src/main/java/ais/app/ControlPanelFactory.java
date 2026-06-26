package ais.app;

import ais.controller.InputController;
import ais.controller.RightPaneController;
import ais.view.RoundButton;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public final class ControlPanelFactory {
    private ControlPanelFactory() {
    }

    public static JPanel create(InputController inputController, RightPaneController rightPaneController) {
        Map<String, String> descriptions = ControlButtonDescriptions.create();

        JPanel bottomLeft = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(6, 6, 6, 6);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0.7;
        bottomLeft.add(createFunctionButtons(inputController, rightPaneController, descriptions), constraints);

        constraints.gridx = 1;
        constraints.weightx = 0.3;
        bottomLeft.add(createArrowPad(inputController, rightPaneController, descriptions), constraints);

        return bottomLeft;
    }

    private static JPanel createFunctionButtons(
            InputController inputController,
            RightPaneController rightPaneController,
            Map<String, String> descriptions) {

        JPanel leftButtons = new JPanel(new GridLayout(2, 4, 8, 8));
        String[] labels = {"USER", "SUB", "MENU", "‥", "PWR", "DIM", "DISP", "CLR"};
        for (String label : labels) {
            if ("‥".equals(label)) {
                JLabel dummy = new JLabel("・・", SwingConstants.CENTER);
                dummy.setForeground(Color.DARK_GRAY);
                leftButtons.add(dummy);
                continue;
            }

            RoundButton button = ControlButtonFactory.create(label, descriptions.get(label), rightPaneController);
            attachFunctionAction(button, label, inputController);
            leftButtons.add(button);
        }
        return leftButtons;
    }

    private static void attachFunctionAction(RoundButton button, String label, InputController inputController) {
        switch (label) {
            case "SUB":
                button.addActionListener(e -> inputController.onSubPressed());
                break;
            case "MENU":
                button.addActionListener(e -> inputController.onMenuPressed());
                break;
            case "PWR":
                button.addActionListener(e -> System.exit(0));
                break;
            case "DISP":
                button.addActionListener(e -> inputController.onDispPressed());
                break;
            case "CLR":
                button.addActionListener(e -> inputController.onClrPressed());
                break;
            default:
                break;
        }
    }

    private static JPanel createArrowPad(
            InputController inputController,
            RightPaneController rightPaneController,
            Map<String, String> descriptions) {

        JPanel arrowPad = new JPanel(new GridLayout(3, 3, 4, 4));

        RoundButton upButton = ControlButtonFactory.create("↑", descriptions.get("↑"), rightPaneController);
        upButton.addActionListener(e -> inputController.onUpPressed());
        upButton.setFocusable(false);

        RoundButton leftButton = ControlButtonFactory.create("←", descriptions.get("←"), rightPaneController);
        leftButton.addActionListener(e -> inputController.onLeftPressed());

        RoundButton enterButton = ControlButtonFactory.create("ENTER", descriptions.get("ENTER"), rightPaneController);
        enterButton.addActionListener(e -> inputController.onEnterPressed());

        RoundButton rightButton = ControlButtonFactory.create("→", descriptions.get("→"), rightPaneController);
        rightButton.addActionListener(e -> inputController.onRightPressed());

        RoundButton downButton = ControlButtonFactory.create("↓", descriptions.get("↓"), rightPaneController);
        downButton.addActionListener(e -> inputController.onDownPressed());
        downButton.setFocusable(false);

        arrowPad.add(new JLabel());
        arrowPad.add(upButton);
        arrowPad.add(new JLabel());
        arrowPad.add(leftButton);
        arrowPad.add(enterButton);
        arrowPad.add(rightButton);
        arrowPad.add(new JLabel());
        arrowPad.add(downButton);
        arrowPad.add(new JLabel());
        return arrowPad;
    }
}
