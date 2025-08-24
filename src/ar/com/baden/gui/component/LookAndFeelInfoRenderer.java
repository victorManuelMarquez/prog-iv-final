package ar.com.baden.gui.component;

import javax.swing.*;
import java.awt.*;

public class LookAndFeelInfoRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(
            JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (c instanceof JLabel label && value instanceof UIManager.LookAndFeelInfo info) {
            label.setText(info.getName());
        }
        return c;
    }

}
