package ar.com.baden.gui;

import ar.com.baden.gui.component.LookAndFeelInfoRenderer;
import ar.com.baden.gui.component.MetalLookAndFeelPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

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

        // componentes
        JPanel themePanel = new JPanel();
        JComboBox<UIManager.LookAndFeelInfo> lafCombo = new JComboBox<>(lafModel);
        lafCombo.setRenderer(new LookAndFeelInfoRenderer());
        JPanel cardsPanel = new JPanel(cardLayout);
        MetalLookAndFeelPanel metalPanel = new MetalLookAndFeelPanel();

        // instalando componentes
        themePanel.add(new JLabel("Tema"));
        themePanel.add(lafCombo);
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
        lafCombo.addItemListener(evt -> {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
                if (evt.getItem() instanceof UIManager.LookAndFeelInfo info) {
                    try {
                        UIManager.setLookAndFeel(info.getClassName());
                        cardLayout.show(cardsPanel, info.getClassName());
                        metalPanel.updateGUI(SwingUtilities.windowForComponent(getRootPane()));
                    } catch (Exception e) {
                        e.printStackTrace(System.err);
                    }
                }
            }
        });
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
