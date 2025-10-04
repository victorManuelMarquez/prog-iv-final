package ar.com.baden.gui;

import ar.com.baden.gui.component.LookAndFeelInfoRenderer;

import javax.swing.*;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.MetalTheme;
import javax.swing.plaf.metal.OceanTheme;
import java.awt.*;

public class SettingsDialog extends JDialog {

    private SettingsDialog(Window owner, String title) {
        super(owner, title, ModalityType.APPLICATION_MODAL);
        // variables
        UIManager.LookAndFeelInfo[] installed = UIManager.getInstalledLookAndFeels();
        DefaultComboBoxModel<UIManager.LookAndFeelInfo> lafModel = new DefaultComboBoxModel<>();
        for (UIManager.LookAndFeelInfo info : installed) {
            lafModel.addElement(info);
            if (info.getClassName().equals(UIManager.getLookAndFeel().getClass().getName())) {
                lafModel.setSelectedItem(info);
            }
        }
        LookAndFeel lookAndFeel = UIManager.getLookAndFeel();
        CardLayout cardLayout = new CardLayout();
        MetalTheme currentTheme = MetalLookAndFeel.getCurrentTheme();
        OceanTheme oceanTheme = new OceanTheme();
        DefaultMetalTheme steelTheme = new DefaultMetalTheme();
        String swingBoldMetalKey = "swing.boldMetal";
        Object swingBoldValue = UIManager.get(swingBoldMetalKey);

        // componentes
        JPanel themePanel = new JPanel();
        JComboBox<UIManager.LookAndFeelInfo> lafCombo = new JComboBox<>(lafModel);
        lafCombo.setRenderer(new LookAndFeelInfoRenderer());
        JPanel cardsPanel = new JPanel(cardLayout);
        JPanel metalPanel = new JPanel();
        JCheckBox swingBold = new JCheckBox("Negrita");
        swingBold.setSelected(swingBoldValue == null || Boolean.parseBoolean(swingBoldValue.toString()));
        JCheckBox lafDecorated = new JCheckBox("Aplicar a ventanas");
        lafDecorated.setSelected(JDialog.isDefaultLookAndFeelDecorated()&&JFrame.isDefaultLookAndFeelDecorated());
        JComboBox<String> themeCombo = new JComboBox<>();
        themeCombo.addItem(oceanTheme.getName());
        themeCombo.addItem(steelTheme.getName());
        themeCombo.setSelectedItem(currentTheme.getName());

        // instalando componentes
        themePanel.add(new JLabel("Tema"));
        themePanel.add(lafCombo);
        metalPanel.add(swingBold);
        metalPanel.add(lafDecorated);
        metalPanel.add(new JLabel("Estilo"));
        metalPanel.add(themeCombo);
        for (UIManager.LookAndFeelInfo info : installed) {
            if ("Metal".equals(info.getName())) {
                cardsPanel.add(metalPanel, info.getClassName());
            } else {
                cardsPanel.add(new JLabel(), info.getClassName());
            }
        }
        themePanel.add(cardsPanel);
        cardLayout.show(cardsPanel, lookAndFeel.getClass().getName());
        getContentPane().add(themePanel);

        // ajustes
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // eventos
        lafCombo.addActionListener(_ -> {
            Object selection = lafCombo.getSelectedItem();
            if (selection instanceof UIManager.LookAndFeelInfo info) {
                if (UIManager.getLookAndFeel().getName().equals(info.getName())) {
                    return;
                }
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                    updateTheme(UIManager.getLookAndFeel());
                    cardLayout.show(cardsPanel, info.getName());
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        });
        swingBold.addActionListener(_ -> {
            try {
                UIManager.put(swingBoldMetalKey, swingBold.isSelected());
                MetalLookAndFeel metalLookAndFeel = new MetalLookAndFeel();
                UIManager.setLookAndFeel(metalLookAndFeel);
                SwingUtilities.updateComponentTreeUI(getOwner());
                SwingUtilities.updateComponentTreeUI(getRootPane());
                pack();
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
        });
        lafDecorated.addActionListener(_ -> {
            if (lookAndFeel.getSupportsWindowDecorations()) {
                JDialog.setDefaultLookAndFeelDecorated(lafDecorated.isSelected());
                JFrame.setDefaultLookAndFeelDecorated(lafDecorated.isSelected());
                updateTheme(lookAndFeel);
            }
        });
        themeCombo.addActionListener(_ -> {
            String themeSelected = (String) themeCombo.getSelectedItem();
            if (currentTheme.getName().equals(themeSelected)) {
                return;
            }
            if (themeSelected != null) {
                MetalLookAndFeel.setCurrentTheme(oceanTheme.getName().equals(themeSelected) ? oceanTheme : steelTheme);
                try {
                    MetalLookAndFeel newLookAndFeel = new MetalLookAndFeel();
                    UIManager.setLookAndFeel(newLookAndFeel);
                    updateTheme(newLookAndFeel);
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        });
    }

    private void updateTheme(LookAndFeel lookAndFeel) {
        dispose();
        MainFrame mainFrame = (MainFrame) getOwner();
        SwingUtilities.updateComponentTreeUI(mainFrame.getRootPane());
        mainFrame.dispose();
        if (lookAndFeel.getSupportsWindowDecorations()) {
            if (JFrame.isDefaultLookAndFeelDecorated()) {
                mainFrame.setUndecorated(true);
                mainFrame.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
            } else {
                mainFrame.setUndecorated(false);
                mainFrame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
            }
        } else {
            mainFrame.setUndecorated(false);
        }
        mainFrame.setVisible(true);
        createAndShow(mainFrame);
    }

    public static void createAndShow(MainFrame mainFrame) {
        try {
            SettingsDialog dialog = new SettingsDialog(mainFrame, "Configuración");
            dialog.pack();
            dialog.setLocationRelativeTo(mainFrame);
            dialog.setVisible(true);
        } catch (RuntimeException e) {
            e.printStackTrace(System.err);
        }
    }

}
