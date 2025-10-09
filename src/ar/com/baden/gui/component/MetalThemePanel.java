package ar.com.baden.gui.component;

import javax.swing.*;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.MetalTheme;
import javax.swing.plaf.metal.OceanTheme;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;

public class MetalThemePanel extends JPanel {

    public MetalThemePanel() {
        // variables
        GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setAutoCreateGaps(true);
        String key = "swing.boldMetal";
        Object value = UIManager.get(key);
        boolean swingBold = value == null || Boolean.parseBoolean(value.toString());

        // componentes
        JCheckBox swingBoldBtn = new JCheckBox("Negrita");
        swingBoldBtn.setActionCommand(key);
        swingBoldBtn.setMnemonic(KeyEvent.VK_N);
        swingBoldBtn.setSelected(swingBold);
        JCheckBox windowDecorationsBtn = new JCheckBox("Decorar ventanas");
        windowDecorationsBtn.setActionCommand("decorations");
        windowDecorationsBtn.setMnemonic(KeyEvent.VK_V);
        JLabel metalThemeLabel = new JLabel("Estilo");
        JComboBox<MetalTheme> themesCombo = new JComboBox<>();
        themesCombo.addItem(new OceanTheme());
        themesCombo.addItem(new DefaultMetalTheme());
        themesCombo.setSelectedItem(MetalLookAndFeel.getCurrentTheme());
        themesCombo.setRenderer(new MetalThemeRenderer());

        // instalando componentes
        groupLayout.setHorizontalGroup(groupLayout.createSequentialGroup()
                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(swingBoldBtn)
                        .addComponent(metalThemeLabel))
                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(windowDecorationsBtn)
                        .addComponent(themesCombo)));

        groupLayout.setVerticalGroup(groupLayout.createSequentialGroup()
                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(swingBoldBtn)
                        .addComponent(windowDecorationsBtn))
                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(metalThemeLabel)
                        .addComponent(themesCombo)));

        // ajustes
        metalThemeLabel.setLabelFor(themesCombo);
        setLayout(groupLayout);

        // eventos
        swingBoldBtn.addActionListener(evt -> {
            if (key.equals(evt.getActionCommand())) {
                UIManager.put(key, swingBoldBtn.isSelected());
                updateMetalLookAndFeel();
            }
        });
        windowDecorationsBtn.addActionListener(evt -> {
            if ("decorations".equals(evt.getActionCommand())) {
                JDialog.setDefaultLookAndFeelDecorated(windowDecorationsBtn.isSelected());
                JFrame.setDefaultLookAndFeelDecorated(windowDecorationsBtn.isSelected());
                updateMetalLookAndFeel();
            }
        });
        themesCombo.addItemListener(evt -> {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
                if (evt.getItem() instanceof MetalTheme theme) {
                    MetalLookAndFeel.setCurrentTheme(theme);
                    updateMetalLookAndFeel();
                }
            }
        });
    }

    void updateMetalLookAndFeel() {
        try {
            MetalLookAndFeel metalLookAndFeel = new MetalLookAndFeel();
            UIManager.setLookAndFeel(metalLookAndFeel);
            Window window = SwingUtilities.windowForComponent(this);
            ThemesPanel.updateGUI(window, metalLookAndFeel.getSupportsWindowDecorations());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace(System.err);
        }

    }

    static class MetalThemeRenderer extends DefaultListCellRenderer {

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
