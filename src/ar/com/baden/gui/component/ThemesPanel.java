package ar.com.baden.gui.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

public class ThemesPanel extends JPanel {

    public ThemesPanel() {
        // variables
        UIManager.LookAndFeelInfo[] installed = UIManager.getInstalledLookAndFeels();
        DefaultComboBoxModel<UIManager.LookAndFeelInfo> lafComboModel;
        lafComboModel = new DefaultComboBoxModel<>();
        CardLayout cardLayout = new CardLayout();

        // componentes
        JLabel themesLabel = new JLabel("Tema");
        JComboBox<UIManager.LookAndFeelInfo> lafCombo = new JComboBox<>(lafComboModel);
        lafCombo.setRenderer(new LookAndFeelInfoRenderer());
        JPanel toolsPanel = new JPanel(cardLayout);

        for (UIManager.LookAndFeelInfo info : installed) {
            lafComboModel.addElement(info);
            LookAndFeel lookAndFeel = UIManager.getLookAndFeel();
            String infoClassName = info.getClassName();
            if ("Metal".equals(info.getName())) {
                toolsPanel.add(new MetalThemePanel(), infoClassName);
            } else {
                toolsPanel.add(new JLabel(), infoClassName);
            }
            if (lookAndFeel.getClass().getName().equals(infoClassName)) {
                lafComboModel.setSelectedItem(info);
                cardLayout.show(toolsPanel, infoClassName);
            }
        }

        // instalando componentes
        add(themesLabel);
        add(lafCombo);
        add(toolsPanel);

        // eventos
        lafCombo.addItemListener(evt -> {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
                if (evt.getItem() instanceof UIManager.LookAndFeelInfo info) {
                    try {
                        UIManager.setLookAndFeel(info.getClassName());
                        cardLayout.show(toolsPanel, info.getClassName());
                        Window window = SwingUtilities.windowForComponent(getRootPane());
                        updateGUI(window, UIManager.getLookAndFeel().getSupportsWindowDecorations());
                    } catch (Exception e) {
                        e.printStackTrace(System.err);
                    }
                }
            }
        });
    }

    public static void updateGUI(Window window, boolean supportsWindowDecorations) {
        if (window == null) {
            return;
        }
        Window owner = window.getOwner();
        SwingUtilities.updateComponentTreeUI(window);
        window.dispose();
        updateGUI(owner, supportsWindowDecorations);
        if (window instanceof JFrame frame) {
            boolean isDecorated = JFrame.isDefaultLookAndFeelDecorated();
            if (isDecorated) {
                frame.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
            } else {
                frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
            }
            frame.setUndecorated(supportsWindowDecorations && isDecorated);
        } else if (window instanceof JDialog dialog) {
            boolean isDecorated = JDialog.isDefaultLookAndFeelDecorated();
            if (isDecorated) {
                dialog.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
            } else {
                dialog.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
            }
            dialog.setUndecorated(supportsWindowDecorations && isDecorated);
        }
        window.pack();
        window.setLocationRelativeTo(owner);
        window.setVisible(true);
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
