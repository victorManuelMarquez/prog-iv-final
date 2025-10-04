package ar.com.baden.gui.component;

import javax.swing.*;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.MetalTheme;
import javax.swing.plaf.metal.OceanTheme;
import java.awt.*;
import java.awt.event.ItemEvent;

public class MetalLookAndFeelPanel extends JPanel {

    public MetalLookAndFeelPanel() {
        super(null);
        //variables
        GroupLayout layout = new GroupLayout(this);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        MetalTheme currentTheme = MetalLookAndFeel.getCurrentTheme();
        MetalTheme otherTheme = currentTheme instanceof OceanTheme ? new DefaultMetalTheme() : new OceanTheme();
        String swingBoldKey = "swing.boldMetal";
        Object swingBoldValue = UIManager.get(swingBoldKey);

        // componentes
        JCheckBox swingBoldBtn = new JCheckBox("Negrita");
        swingBoldBtn.setSelected(swingBoldValue == null || Boolean.parseBoolean(swingBoldValue.toString()));
        JCheckBox decorationsBtn = new JCheckBox("Decorar ventanas");
        decorationsBtn.setSelected(JFrame.isDefaultLookAndFeelDecorated() && JDialog.isDefaultLookAndFeelDecorated());
        JLabel stylesLabel = new JLabel("Estilo");
        JComboBox<MetalTheme> stylesCombo = new JComboBox<>();
        stylesCombo.setRenderer(new MetalThemeRenderer());
        stylesCombo.addItem(currentTheme);
        stylesCombo.addItem(otherTheme);

        // instalando componentes
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(swingBoldBtn)
                        .addComponent(stylesLabel))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(decorationsBtn)
                        .addComponent(stylesCombo)));

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(swingBoldBtn)
                        .addComponent(decorationsBtn))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(stylesLabel)
                        .addComponent(stylesCombo)));

        // ajustes
        setLayout(layout);

        // eventos
        swingBoldBtn.addActionListener(_ -> {
            UIManager.put(swingBoldKey, swingBoldBtn.isSelected());
            updateMetal();
        });
        decorationsBtn.addActionListener(_ -> {
            JFrame.setDefaultLookAndFeelDecorated(decorationsBtn.isSelected());
            JDialog.setDefaultLookAndFeelDecorated(decorationsBtn.isSelected());
            updateMetal();
        });
        stylesCombo.addItemListener(evt -> {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
                if (evt.getItem() instanceof MetalTheme theme) {
                    MetalLookAndFeel.setCurrentTheme(theme);
                    updateMetal();
                }
            }
        });
    }

    private void updateMetal() {
        MetalLookAndFeel metalLookAndFeel = new MetalLookAndFeel();
        try {
            UIManager.setLookAndFeel(metalLookAndFeel);
            updateGUI(SwingUtilities.windowForComponent(getRootPane()));
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    public void updateGUI(Window window) {
        if (window == null) {
            return;
        }
        JRootPane rootPane = SwingUtilities.getRootPane(window);
        SwingUtilities.updateComponentTreeUI(rootPane);
        Window owner = window.getOwner();
        boolean supportsWindowDecorations = UIManager.getLookAndFeel().getSupportsWindowDecorations();
        if (window instanceof JFrame frame) {
            frame.dispose();
            boolean decorated = JFrame.isDefaultLookAndFeelDecorated() && supportsWindowDecorations;
            frame.setUndecorated(decorated);
            if (decorated) {
                rootPane.setWindowDecorationStyle(JRootPane.FRAME);
            } else {
                rootPane.setWindowDecorationStyle(JRootPane.NONE);
            }
            updateGUI(owner);
        } else if (window instanceof JDialog dialog) {
            dialog.dispose();
            boolean decorated = JDialog.isDefaultLookAndFeelDecorated() && supportsWindowDecorations;
            dialog.setUndecorated(decorated);
            if (decorated) {
                rootPane.setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
            } else {
                rootPane.setWindowDecorationStyle(JRootPane.NONE);
            }
            updateGUI(owner);
        }
        window.pack();
        window.setLocationRelativeTo(owner);
        window.setVisible(true);
    }

}
