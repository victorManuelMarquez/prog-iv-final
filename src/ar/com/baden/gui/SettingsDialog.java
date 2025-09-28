package ar.com.baden.gui;

import ar.com.baden.gui.component.LookAndFeelInfoRenderer;

import javax.swing.*;
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

        // componentes
        JPanel themePanel = new JPanel();
        JComboBox<UIManager.LookAndFeelInfo> lafCombo = new JComboBox<>(lafModel);
        lafCombo.setRenderer(new LookAndFeelInfoRenderer());
        JCheckBox lafDecorated = new JCheckBox("Decoración nativa");
        lafDecorated.setSelected(JDialog.isDefaultLookAndFeelDecorated()&&JFrame.isDefaultLookAndFeelDecorated());

        // instalando componentes
        themePanel.add(new JLabel("Tema"));
        themePanel.add(lafCombo);
        themePanel.add(lafDecorated);
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
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        });
        lafDecorated.addActionListener(_ -> {
            if (lookAndFeel.getSupportsWindowDecorations()) {
                JDialog.setDefaultLookAndFeelDecorated(lafDecorated.isSelected());
                JFrame.setDefaultLookAndFeelDecorated(lafDecorated.isSelected());
                updateTheme(lookAndFeel);
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
