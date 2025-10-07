package ar.com.baden.gui.component;

import ar.com.baden.gui.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

public class ThemesPanel extends JPanel {

    public ThemesPanel() {
        // variables
        UIManager.LookAndFeelInfo[] installed = UIManager.getInstalledLookAndFeels();
        DefaultComboBoxModel<UIManager.LookAndFeelInfo> lafComboModel;
        lafComboModel = new DefaultComboBoxModel<>();
        CardLayout themesCardLayout = new CardLayout();

        // componentes
        JLabel themeLabel = new JLabel("Tema");
        JComboBox<UIManager.LookAndFeelInfo> lafCombo = new JComboBox<>(lafComboModel);
        lafCombo.setRenderer(new LookAndFeelInfoRenderer());
        JPanel themeToolsPanel = new JPanel(themesCardLayout);
        for (UIManager.LookAndFeelInfo info : installed) {
            lafComboModel.addElement(info);
            if (info.getName().equals("Metal")) {
                themeToolsPanel.add(new MetalLookAndFeelPanel(this), info.getClassName());
            } else {
                themeToolsPanel.add(new JLabel(), info.getClassName());
            }
            if (UIManager.getLookAndFeel().getName().equals(info.getName())) {
                lafComboModel.setSelectedItem(info);
                themesCardLayout.show(themeToolsPanel, info.getClassName());
            }
        }

        // instalando componentes
        add(themeLabel);
        add(lafCombo);
        add(themeToolsPanel);

        // eventos
        lafCombo.addItemListener(evt -> {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
                if (evt.getItem() instanceof UIManager.LookAndFeelInfo info) {
                    try {
                        themesCardLayout.show(themeToolsPanel, info.getClassName());
                        UIManager.setLookAndFeel(info.getClassName());
                        Window window = SwingUtilities.windowForComponent(getRootPane());
                        MainFrame mainFrame = findMainFrame(window);
                        updateTheme(mainFrame);
                    } catch (Exception e) {
                        e.printStackTrace(System.err);
                    }
                }
            }
        });
    }

    protected MainFrame findMainFrame(Window window) {
        if (window == null) {
            return null;
        } else if (window instanceof MainFrame frame) {
            return frame;
        } else {
            return findMainFrame(window.getOwner());
        }
    }

    public void updateTheme(Window window) {
        if (window == null) {
            return;
        }
        JRootPane windowRootPane = SwingUtilities.getRootPane(window);
        SwingUtilities.updateComponentTreeUI(windowRootPane);
        Window[] ownedWindows = window.getOwnedWindows();
        Window owner = window.getOwner();
        window.dispose();
        boolean supportDecorations = UIManager.getLookAndFeel().getSupportsWindowDecorations();
        if (window instanceof JFrame frame) {
            boolean decorated = JFrame.isDefaultLookAndFeelDecorated();
            frame.setUndecorated(supportDecorations && decorated);
            if (decorated) {
                windowRootPane.setWindowDecorationStyle(JRootPane.FRAME);
            } else {
                windowRootPane.setWindowDecorationStyle(JRootPane.NONE);
            }
        } else if (window instanceof JDialog dialog) {
            boolean decorated = JDialog.isDefaultLookAndFeelDecorated();
            dialog.setUndecorated(supportDecorations && decorated);
            if (decorated) {
                windowRootPane.setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
            } else {
                windowRootPane.setWindowDecorationStyle(JRootPane.NONE);
            }
        }
        window.pack();
        window.setLocationRelativeTo(owner);
        window.setVisible(true);
        for (Window owned : ownedWindows) {
            updateTheme(owned);
        }
    }

}
