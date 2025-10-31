package ar.com.baden.gui.component;

import ar.com.baden.main.App;
import ar.com.baden.utils.Settings;

import javax.swing.*;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.MetalTheme;
import javax.swing.plaf.metal.OceanTheme;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;

public class ThemesPanel extends SettingsPanel implements ItemListener {

    private final JComboBox<UIManager.LookAndFeelInfo> lafCombo;
    private final CardLayout cardLayout;
    private final JPanel cardPanel;
    private final String metal = "Metal";

    public ThemesPanel() {
        super(null);

        // variables
        cardLayout = new CardLayout();
        GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setAutoCreateContainerGaps(true);
        groupLayout.setAutoCreateGaps(true);
        LookAndFeel lookAndFeel = UIManager.getLookAndFeel();
        UIManager.LookAndFeelInfo[] installedLaf = UIManager.getInstalledLookAndFeels();
        DefaultComboBoxModel<UIManager.LookAndFeelInfo> lafModel = new DefaultComboBoxModel<>();
        String currentLafName = null;
        for (UIManager.LookAndFeelInfo info : installedLaf) {
            lafModel.addElement(info);
            if (info.getClassName().equals(lookAndFeel.getClass().getName())) {
                lafModel.setSelectedItem(info);
                currentLafName = info.getName();
            }
        }

        // componentes
        JLabel lafLabel = new JLabel("Tema");
        lafCombo = new JComboBox<>(lafModel);
        lafCombo.setRenderer(new LookAndFeelInfoRenderer());
        cardPanel = new JPanel(cardLayout);

        // instalando componentes
        cardPanel.add(new JLabel(""), "other");
        cardPanel.add(new MetalPanel(), metal);
        if (metal.equals(currentLafName)) {
            cardLayout.show(cardPanel, metal);
        }

        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup()
                .addGroup(groupLayout.createSequentialGroup()
                        .addComponent(lafLabel)
                        .addComponent(lafCombo))
                .addComponent(cardPanel));

        groupLayout.setVerticalGroup(groupLayout.createSequentialGroup()
                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(lafLabel)
                        .addComponent(lafCombo))
                .addComponent(cardPanel));

        // ajustes
        setLayout(groupLayout);

        // eventos
        lafCombo.addItemListener(this);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            if (e.getItem() instanceof UIManager.LookAndFeelInfo info) {
                cardLayout.show(cardPanel, metal.equals(info.getName()) ? metal : "other");
                App.settings.setProperty(Settings.K_LOOK_AND_FEEL, info.getClassName());
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("defaultsRestored".equals(evt.getPropertyName())) {
            lafCombo.removeItemListener(this);
            for (int i = 0; i < lafCombo.getItemCount(); i++) {
                UIManager.LookAndFeelInfo info = lafCombo.getItemAt(i);
                if (info.getClassName().equals(App.settings.getDefault(Settings.K_LOOK_AND_FEEL))) {
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

    static class MetalPanel extends JPanel {

        public MetalPanel() {
            super(new GridBagLayout());
            // variables
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);
            int row = 0;

            // componentes
            JComboBox<MetalTheme> metalCombo = createMetalThemesCombo();
            JCheckBox boldMetalBtn = new JCheckBox("Usar negrita en etiquetas y botones");
            boldMetalBtn.setActionCommand(Settings.K_SWING_BOLD_METAL);
            boldMetalBtn.setSelected(Boolean.parseBoolean(App.settings.getProperty(Settings.K_SWING_BOLD_METAL)));
            JCheckBox decorationsBtn = new JCheckBox("Decorar ventanas");
            decorationsBtn.setActionCommand(Settings.K_WINDOWS_DECORATIONS);
            decorationsBtn.setSelected(Boolean.parseBoolean(App.settings.getProperty(Settings.K_WINDOWS_DECORATIONS)));

            // instalando componentes
            gbc.anchor = GridBagConstraints.LINE_START;
            gbc.gridy = row;
            gbc.weighty = 1.0;
            add(new JLabel("Estilo"), gbc);
            add(metalCombo, gbc);

            row++;
            gbc.gridy = row;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            add(boldMetalBtn, gbc);

            row++;
            gbc.gridy = row;
            add(decorationsBtn, gbc);

            // eventos
            metalCombo.addItemListener(evt -> {
                if (evt.getStateChange() == ItemEvent.SELECTED) {
                    if (evt.getItem() instanceof MetalTheme theme) {
                        App.settings.setProperty(Settings.K_METAL_THEME, theme.getName());
                    }
                }
            });
            boldMetalBtn.addActionListener(evt -> {
                if (Settings.K_SWING_BOLD_METAL.equals(evt.getActionCommand())) {
                    String value = String.valueOf(boldMetalBtn.isSelected());
                    App.settings.setProperty(Settings.K_SWING_BOLD_METAL, value);
                    UIManager.put(Settings.K_WINDOWS_DECORATIONS, value);
                }
            });
            decorationsBtn.addActionListener(evt -> {
                if (Settings.K_WINDOWS_DECORATIONS.equals(evt.getActionCommand())) {
                    String value = String.valueOf(decorationsBtn.isSelected());
                    App.settings.setProperty(Settings.K_WINDOWS_DECORATIONS, value);
                }
            });
        }

        private JComboBox<MetalTheme> createMetalThemesCombo() {
            MetalTheme currentTheme = MetalLookAndFeel.getCurrentTheme();
            MetalTheme metalTheme;
            metalTheme = currentTheme instanceof OceanTheme ? new DefaultMetalTheme() : new OceanTheme();

            // componentes
            JComboBox<MetalTheme> metalCombo = new JComboBox<>(new MetalTheme[] {
                    currentTheme,
                    metalTheme
            });
            metalCombo.setSelectedItem(currentTheme);
            metalCombo.setRenderer(new MetalThemeListRenderer());
            return metalCombo;
        }

    }

    static class MetalThemeListRenderer extends DefaultListCellRenderer {

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

}
