package ais.app;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JFrame;

public class MainFrame {

    private final JFrame frame;

    public MainFrame(String title) {
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new GridBagLayout());
    }

    public void addLeftPanel(Component panel) {
        GridBagConstraints constraints = baseConstraints();
        constraints.gridx = 0;
        constraints.weightx = 0.9;
        frame.add(panel, constraints);
    }

    public void addRightPanel(Component panel) {
        GridBagConstraints constraints = baseConstraints();
        constraints.gridx = 1;
        constraints.weightx = 0.7;
        frame.add(panel, constraints);
    }

    public void show() {
        frame.setVisible(true);
    }

    private GridBagConstraints baseConstraints() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weighty = 1.0;
        return constraints;
    }
}
