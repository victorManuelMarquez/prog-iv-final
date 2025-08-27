package ar.com.baden.gui.component;

import javax.swing.*;
import java.awt.*;

public class SectionPanel extends JPanel {

    private final JPanel mainContainer;

    public SectionPanel(String name) {
        super(new BorderLayout());

        // componentes
        JPanel north = new JPanel(new GridBagLayout());
        mainContainer = new JPanel();

        // instalando componentes
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        north.add(new JLabel(name), gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1.0;
        north.add(new JSeparator(), gbc);
        super.add(north, BorderLayout.NORTH);
        super.add(mainContainer, BorderLayout.CENTER);

        // ajustes
        setName(name);
    }

    @Override
    public Component add(Component comp) {
        return mainContainer.add(comp);
    }

    @Override
    public Component add(String name, Component comp) {
        return mainContainer.add(name, comp);
    }

    @Override
    public Component add(Component comp, int index) {
        return mainContainer.add(comp, index);
    }

    public JPanel getMainContainer() {
        return mainContainer;
    }

}
