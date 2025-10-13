package ar.com.baden.gui;

import ar.com.baden.gui.component.ThemesPanel;
import ar.com.baden.main.App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeListenerProxy;

public class SettingsDialog extends JDialog {

    private SettingsDialog(Frame owner, String title) {
        super(owner, title, ModalityType.APPLICATION_MODAL);
        // componentes
        ThemesPanel themesPanel = new ThemesPanel();
        ToolsPanel toolsPanel = new ToolsPanel();

        // instalando componentes
        getContentPane().add(themesPanel);
        getContentPane().add(toolsPanel, BorderLayout.SOUTH);

        // eventos
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                for (PropertyChangeListener listener : App.settings.getPropertyChangeListeners()) {
                    if (listener instanceof PropertyChangeListenerProxy proxy) {
                        App.settings.removePropertyChangeListener(proxy.getPropertyName(), proxy);
                    } else {
                        App.settings.removePropertyChangeListener(listener);
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

    static class ToolsPanel extends JPanel {

        public ToolsPanel() {
            super(null);
            // variables
            GroupLayout groupLayout = new GroupLayout(this);
            groupLayout.setAutoCreateContainerGaps(true);
            groupLayout.setAutoCreateGaps(true);

            // componentes
            JButton okBtn = new JButton("Aceptar");
            JButton cancelBtn = new JButton("Cancelar");
            JButton applyBtn = new JButton("Aplicar");
            applyBtn.setEnabled(App.settings.hasChanges());

            // instalando componentes
            groupLayout.setHorizontalGroup(groupLayout.createSequentialGroup()
                    .addComponent(okBtn)
                    .addComponent(cancelBtn)
                    .addComponent(applyBtn));

            groupLayout.setVerticalGroup(groupLayout.createParallelGroup()
                    .addComponent(okBtn)
                    .addComponent(cancelBtn)
                    .addComponent(applyBtn));

            // ajustes
            setLayout(groupLayout);

            // eventos
            App.settings.addPropertyChangeListener(_ -> applyBtn.setEnabled(App.settings.hasChanges()));
            okBtn.addActionListener(_ -> {
                App.settings.applyChanges();
                Window window = SwingUtilities.windowForComponent(this);
                window.dispose();
            });
            cancelBtn.addActionListener(_ -> {
                App.settings.discardChanges();
                Window window = SwingUtilities.windowForComponent(this);
                window.dispose();
            });
            applyBtn.addActionListener(_ -> App.settings.applyChanges());
        }

    }

}
