package ar.com.baden.gui.component;

import ar.com.baden.main.App;

import javax.swing.*;
import java.beans.PropertyChangeEvent;

public class GeneralPanel extends SettingsPanel {

    private final JCheckBox confirmToExitBtn;

    public GeneralPanel() {
        // variables
        String confirmToExitKey = "settings.confirmToExit";
        String property = App.settings.getProperty(confirmToExitKey);

        // componentes
        confirmToExitBtn = new JCheckBox("Confirmar para salir");
        confirmToExitBtn.setSelected(Boolean.parseBoolean(property));

        // instalando componentes
        add(confirmToExitBtn);

        // eventos
        confirmToExitBtn.addActionListener(_ -> {
            boolean status = confirmToExitBtn.isSelected();
            App.settings.setProperty(confirmToExitKey, String.valueOf(status));
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("defaultsRestored".equals(evt.getPropertyName())) {
            String defaultValue = App.settings.getDefault("settings.confirmToExit");
            confirmToExitBtn.setSelected(Boolean.parseBoolean(defaultValue));
        }
    }

}
