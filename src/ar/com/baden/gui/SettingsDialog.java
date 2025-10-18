package ar.com.baden.gui;

import ar.com.baden.gui.component.GeneralPanel;
import ar.com.baden.gui.component.SettingsPanel;
import ar.com.baden.gui.component.ThemesPanel;
import ar.com.baden.main.App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeEvent;

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
                App.settings.addPropertyChangeListener(generalPanel);
                App.settings.addPropertyChangeListener(themesPanel);
                App.settings.addPropertyChangeListener(toolsPanel);
            }

            @Override
            public void componentHidden(ComponentEvent e) {
                App.settings.removePropertyChangeListener(generalPanel);
                App.settings.removePropertyChangeListener(themesPanel);
                App.settings.removePropertyChangeListener(toolsPanel);
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

    static class ToolsPanel extends SettingsPanel {

        private final JButton applyBtn;

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
            applyBtn = new JButton("Aplicar");
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

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            applyBtn.setEnabled(App.settings.hasChanges());
        }

    }

}
