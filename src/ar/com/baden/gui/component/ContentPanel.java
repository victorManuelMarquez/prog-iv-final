package ar.com.baden.gui.component;

import javax.swing.*;
import java.awt.*;

public class ContentPanel extends JPanel {

    private final JPanel mainContentPane;

    public ContentPanel(String title) {
        super(new BorderLayout());
        JPanel titledPanel = createTitledSeparator(title);
        mainContentPane = new JPanel();
        super.add(titledPanel, BorderLayout.NORTH);
        super.add(mainContentPane, BorderLayout.CENTER);
    }

    @Override
    public void setLayout(LayoutManager mgr) {
        if (mainContentPane != null) {
            mainContentPane.setLayout(mgr);
        } else {
            super.setLayout(mgr);
        }
    }

    @Override
    public Component add(Component comp) {
        return mainContentPane.add(comp);
    }

    @Override
    public Component add(Component comp, int index) {
        return mainContentPane.add(comp, index);
    }

    @Override
    public void add(Component comp, Object constraints) {
        mainContentPane.add(comp, constraints);
    }

    @Override
    public void add(Component comp, Object constraints, int index) {
        mainContentPane.add(comp, constraints, index);
    }

    @Override
    public Component add(String name, Component comp) {
        return mainContentPane.add(name, comp);
    }

    protected JPanel createTitledSeparator(String title) {
        JPanel panel = new JPanel(null);
        BoxLayout boxLayout = new BoxLayout(panel, BoxLayout.LINE_AXIS);
        panel.setLayout(boxLayout);
        panel.add(new JLabel(title));
        panel.add(Box.createHorizontalStrut(5));
        panel.add(new JSeparator(JSeparator.HORIZONTAL));
        return panel;
    }

    public JPanel getMainContentPane() {
        return mainContentPane;
    }

}
