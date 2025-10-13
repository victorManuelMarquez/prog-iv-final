package ar.com.baden.gui.component;

import ar.com.baden.main.App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

public class ThemesPanel extends JPanel {

    public ThemesPanel() {
        // variables
        LookAndFeel lookAndFeel = UIManager.getLookAndFeel();
        UIManager.LookAndFeelInfo[] installedLaf = UIManager.getInstalledLookAndFeels();
        DefaultComboBoxModel<UIManager.LookAndFeelInfo> lafModel = new DefaultComboBoxModel<>();
        for (UIManager.LookAndFeelInfo info : installedLaf) {
            lafModel.addElement(info);
            if (info.getClassName().equals(lookAndFeel.getClass().getName())) {
                lafModel.setSelectedItem(info);
            }
        }

        // componentes
        JLabel lafLabel = new JLabel("Tema");
        JComboBox<UIManager.LookAndFeelInfo> lafCombo = new JComboBox<>(lafModel);
        lafCombo.setRenderer(new LookAndFeelInfoRenderer());

        // instalando componentes
        add(lafLabel);
        add(lafCombo);

        // eventos
        lafCombo.addItemListener(evt -> {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
                if (evt.getItem() instanceof UIManager.LookAndFeelInfo info) {
                    App.settings.setProperty("settings.lookAndFeel", info.getClassName());
                }
            }
        });
    }

    static class LookAndFeelInfoRenderer extends DefaultListCellRenderer {

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

}
