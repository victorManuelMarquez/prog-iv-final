package ar.com.baden.gui.component;

import ar.com.baden.main.App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;

public class ThemesPanel extends SettingsPanel implements ItemListener {

    private final JComboBox<UIManager.LookAndFeelInfo> lafCombo;

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
        lafCombo = new JComboBox<>(lafModel);
        lafCombo.setRenderer(new LookAndFeelInfoRenderer());

        // instalando componentes
        add(lafLabel);
        add(lafCombo);

        // eventos
        lafCombo.addItemListener(this);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            if (e.getItem() instanceof UIManager.LookAndFeelInfo info) {
                App.settings.setProperty("settings.lookAndFeel", info.getClassName());
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("defaultsRestored".equals(evt.getPropertyName())) {
            lafCombo.removeItemListener(this);
            for (int i = 0; i < lafCombo.getItemCount(); i++) {
                UIManager.LookAndFeelInfo info = lafCombo.getItemAt(i);
                if (info.getClassName().equals(App.settings.getDefault("settings.lookAndFeel"))) {
                    lafCombo.setSelectedItem(info);
                }
            }
            lafCombo.addItemListener(this);
        }
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
