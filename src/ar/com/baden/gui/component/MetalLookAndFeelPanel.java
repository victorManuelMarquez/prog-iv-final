package ar.com.baden.gui.component;

import ar.com.baden.gui.MainFrame;

import javax.swing.*;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.MetalTheme;
import javax.swing.plaf.metal.OceanTheme;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;

public class MetalLookAndFeelPanel extends JPanel {

    public MetalLookAndFeelPanel(ThemesPanel themesPanel) {
        // variables
        GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setAutoCreateContainerGaps(true);
        groupLayout.setAutoCreateGaps(true);
        String swingBoldKey = "swing.boldMetal";
        Object swingBoldValue = UIManager.get(swingBoldKey);
        boolean swingBoldStatus = swingBoldValue == null || Boolean.parseBoolean(swingBoldValue.toString());
        MetalTheme currentMetalTheme = MetalLookAndFeel.getCurrentTheme();
        MetalTheme metalTheme = currentMetalTheme instanceof OceanTheme ? new DefaultMetalTheme() : new OceanTheme();

        // componentes
        JCheckBox swingBoldBtn = new JCheckBox("Negrita");
        swingBoldBtn.setSelected(swingBoldStatus);
        swingBoldBtn.setMnemonic(KeyEvent.VK_N);
        JCheckBox decorationsBtn = new JCheckBox("Decoración en ventanas");
        decorationsBtn.setSelected(JFrame.isDefaultLookAndFeelDecorated() && JDialog.isDefaultLookAndFeelDecorated());
        decorationsBtn.setMnemonic(KeyEvent.VK_V);
        JLabel metalThemesLabel = new JLabel("Estilo");
        JComboBox<MetalTheme> metalThemesCombo = new JComboBox<>();
        metalThemesCombo.setRenderer(new MetalThemeRenderer());
        metalThemesCombo.addItem(currentMetalTheme);
        metalThemesCombo.addItem(metalTheme);

        // instalando componentes
        groupLayout.setHorizontalGroup(groupLayout.createSequentialGroup()
                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(swingBoldBtn)
                        .addComponent(metalThemesLabel))
                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(decorationsBtn)
                        .addComponent(metalThemesCombo)));

        groupLayout.setVerticalGroup(groupLayout.createSequentialGroup()
                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(swingBoldBtn)
                        .addComponent(decorationsBtn))
                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(metalThemesLabel)
                        .addComponent(metalThemesCombo)));

        // ajustes
        setLayout(groupLayout);

        // eventos
        swingBoldBtn.addActionListener(_ -> {
            UIManager.put(swingBoldKey, swingBoldBtn.isSelected());
            updateMetal(themesPanel);
        });
        decorationsBtn.addActionListener(_ -> {
            JFrame.setDefaultLookAndFeelDecorated(decorationsBtn.isSelected());
            JDialog.setDefaultLookAndFeelDecorated(decorationsBtn.isSelected());
            updateMetal(themesPanel);
        });
        metalThemesCombo.addItemListener(evt -> {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
                if (evt.getItem() instanceof MetalTheme theme) {
                    MetalLookAndFeel.setCurrentTheme(theme);
                    updateMetal(themesPanel);
                }
            }
        });
    }

    protected void updateMetal(ThemesPanel themesPanel) {
        try {
            UIManager.setLookAndFeel(new MetalLookAndFeel());
            Window window = SwingUtilities.windowForComponent(this);
            MainFrame frame = themesPanel.findMainFrame(window);
            themesPanel.updateTheme(frame);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace(System.err);
        }
    }

}
