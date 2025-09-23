package ar.com.baden.gui;

import ar.com.baden.gui.component.GeneralContent;
import ar.com.baden.main.App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeListenerProxy;

public class SettingsDialog extends ModalDialog {

    private SettingsDialog(Window owner, String title) {
        super(owner, title);
        // variables
        GroupLayout groupLayout = new GroupLayout(getContentPane());
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);
        LayoutStyle.ComponentPlacement unrelated = LayoutStyle.ComponentPlacement.UNRELATED;

        // componentes
        GeneralContent generalContent = new GeneralContent("General");
        JSeparator horizontalSeparator = new JSeparator(JSeparator.HORIZONTAL);
        JButton resetBtn = new JButton("Restablecer");
        resetBtn.setMnemonic(KeyEvent.VK_R);
        JButton okBtn = new JButton("Ok");
        okBtn.setMnemonic(KeyEvent.VK_O);
        JButton cancelBtn = new JButton("Cancelar");
        cancelBtn.setMnemonic(KeyEvent.VK_C);
        JButton applyBtn = new JButton("Aplicar");
        applyBtn.setEnabled(App.properties.hasChanges());
        applyBtn.setMnemonic(KeyEvent.VK_A);

        // instalando componentes
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup()
                .addComponent(generalContent)
                .addComponent(horizontalSeparator)
                .addGroup(groupLayout.createSequentialGroup()
                        .addComponent(resetBtn)
                        .addPreferredGap(unrelated, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
                        .addComponent(okBtn)
                        .addComponent(cancelBtn)
                        .addComponent(applyBtn)));

        groupLayout.setVerticalGroup(groupLayout.createSequentialGroup()
                .addComponent(generalContent)
                .addComponent(horizontalSeparator)
                .addGroup(groupLayout.createParallelGroup()
                        .addComponent(resetBtn)
                        .addComponent(okBtn)
                        .addComponent(cancelBtn)
                        .addComponent(applyBtn)));

        // ajustes
        getRootPane().setDefaultButton(okBtn);
        setLayout(groupLayout);

        // eventos
        SwingUtilities.invokeLater(okBtn::requestFocusInWindow);

        App.properties.addPropertyChangeListener(evt -> {
            boolean condition1 = "restore".equals(evt.getPropertyName());
            boolean condition2 = "resetToDefaults".equals(evt.getPropertyName());
            applyBtn.setEnabled(App.properties.hasChanges() && (condition1 || condition2));
        });
        App.properties.addPropertyChangeListener("change", _ -> applyBtn.setEnabled(true));
        App.properties.addPropertyChangeListener("changesApplied", _ -> applyBtn.setEnabled(false));

        resetBtn.addActionListener(_ -> App.properties.resetValues());
        okBtn.addActionListener(_ -> {
            if (App.properties.hasChanges()) {
                App.properties.applyChanges();
            }
            dispose();
        });
        cancelBtn.addActionListener(_ -> {
            App.properties.clearChanges();
            dispose();
        });
        applyBtn.addActionListener(_ -> App.properties.applyChanges());
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                PropertyChangeListener[] listeners = App.properties.getPropertyChangeListeners();
                for (PropertyChangeListener listener : listeners) {
                    if (listener instanceof PropertyChangeListenerProxy proxy) {
                        App.properties.removePropertyChangeListener(proxy.getPropertyName(), listener);
                    } else {
                        App.properties.removePropertyChangeListener(listener);
                    }
                }
            }
        });
    }

    public static void createAndShow(Window owner) {
        try {
            SettingsDialog dialog = new SettingsDialog(owner, "Configuración");
            dialog.pack();
            dialog.setLocationRelativeTo(owner);
            dialog.setVisible(true);
        } catch (RuntimeException e) {
            e.printStackTrace(System.err);
        }
    }

}
