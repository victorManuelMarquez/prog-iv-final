package ar.com.baden.gui.component;

import ar.com.baden.main.App;
import ar.com.baden.utils.Settings;

import javax.swing.*;
import java.beans.PropertyChangeEvent;

public class GeneralPanel extends SettingsPanel {

    private final JCheckBox confirmToExitBtn;

    public GeneralPanel() {
        // variables
        String property = App.settings.getProperty(Settings.K_CONFIRM_EXIT);

        // componentes
        confirmToExitBtn = new JCheckBox("Confirmar para salir");
        confirmToExitBtn.setSelected(Boolean.parseBoolean(property));

        // instalando componentes
        add(confirmToExitBtn);

        // eventos
        confirmToExitBtn.addActionListener(_ -> {
            boolean status = confirmToExitBtn.isSelected();
            App.settings.setProperty(Settings.K_CONFIRM_EXIT, String.valueOf(status));
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("defaultsRestored".equals(evt.getPropertyName())) {
            String defaultValue = App.settings.getDefault(Settings.K_CONFIRM_EXIT);
            confirmToExitBtn.setSelected(Boolean.parseBoolean(defaultValue));
        }
    }

}
