package ar.com.baden.gui;

import ar.com.baden.gui.component.GeneralPanel;
import ar.com.baden.gui.component.ThemesPanel;
import ar.com.baden.main.App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeListener;

public class SettingsDialog extends JDialog {

    private SettingsDialog(Frame owner, String title) {
        super(owner, title, ModalityType.APPLICATION_MODAL);
        // componentes
        Box verticalBox = Box.createVerticalBox();
        GeneralPanel generalPanel = new GeneralPanel();
        ThemesPanel themesPanel = new ThemesPanel();
        ToolsPanel toolsPanel = new ToolsPanel();

        // instalando componentes
        verticalBox.add(generalPanel);
        verticalBox.add(themesPanel);
        getContentPane().add(verticalBox);
        getContentPane().add(toolsPanel, BorderLayout.SOUTH);

        // eventos
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                App.settings.addPropertyChangeListener(toolsPanel.getChangeListener());
            }

            @Override
            public void componentHidden(ComponentEvent e) {
                App.settings.removePropertyChangeListener(toolsPanel.getChangeListener());
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

        private final PropertyChangeListener changeListener;

        public ToolsPanel() {
            super(null);
            // variables
            GroupLayout groupLayout = new GroupLayout(this);
            groupLayout.setAutoCreateContainerGaps(true);
            groupLayout.setAutoCreateGaps(true);
            LayoutStyle.ComponentPlacement unrelated = LayoutStyle.ComponentPlacement.UNRELATED;

            // componentes
            JButton resetBtn = new JButton("Restablecer");
            JButton okBtn = new JButton("Aceptar");
            JButton cancelBtn = new JButton("Cancelar");
            JButton applyBtn = new JButton("Aplicar");
            applyBtn.setEnabled(App.settings.hasChanges());

            // instalando componentes
            groupLayout.setHorizontalGroup(groupLayout.createSequentialGroup()
                    .addComponent(resetBtn)
                    .addPreferredGap(unrelated, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
                    .addComponent(okBtn)
                    .addComponent(cancelBtn)
                    .addComponent(applyBtn));

            groupLayout.setVerticalGroup(groupLayout.createParallelGroup()
                    .addComponent(resetBtn)
                    .addComponent(okBtn)
                    .addComponent(cancelBtn)
                    .addComponent(applyBtn));

            // ajustes
            setLayout(groupLayout);

            // eventos
            changeListener = _ -> applyBtn.setEnabled(App.settings.hasChanges());
            resetBtn.addActionListener(_ -> App.settings.restoreDefaults());
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

        public PropertyChangeListener getChangeListener() {
            return changeListener;
        }

    }

}
