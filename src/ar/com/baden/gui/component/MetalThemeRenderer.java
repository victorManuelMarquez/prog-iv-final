package ar.com.baden.gui.component;

import javax.swing.*;
import javax.swing.plaf.metal.MetalTheme;
import java.awt.*;

public class MetalThemeRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(
            JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof MetalTheme theme) {
            JLabel label = (JLabel) component;
            label.setText(theme.getName());
        }
        return component;
    }

}
