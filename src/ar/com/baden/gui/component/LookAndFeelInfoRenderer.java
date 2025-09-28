package ar.com.baden.gui.component;

import javax.swing.*;
import java.awt.*;

public class LookAndFeelInfoRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(
            JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof UIManager.LookAndFeelInfo info) {
            ((JLabel) c).setText(info.getName());
        }
        return c;
    }

}
