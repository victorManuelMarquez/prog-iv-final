package ar.com.baden.gui.component;

import javax.swing.*;
import java.awt.*;

public class LookAndFeelInfoRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(
            JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof UIManager.LookAndFeelInfo info) {
            JLabel label = (JLabel) component;
            label.setText(info.getName());
        }
        return component;
    }

}
