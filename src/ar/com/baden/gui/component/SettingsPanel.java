package ar.com.baden.gui.component;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;

public abstract class SettingsPanel extends JPanel implements PropertyChangeListener {

    public SettingsPanel(LayoutManager layout) {
        super(layout);
    }

    public SettingsPanel() {}

}
