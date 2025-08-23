package ar.com.baden.gui.component;

import ar.com.baden.main.App;

import javax.swing.*;

public class GeneralPanel extends JPanel {

    public GeneralPanel() {
        // variables
        boolean confirmToClose = Boolean.parseBoolean(App.settings.getProperty("settings.showClosingDialog"));

        // componentes
        JCheckBox showClosingDialog = new JCheckBox("Confirmar para salir del programa");

        // ajustes
        showClosingDialog.setSelected(confirmToClose);

        // eventos
        showClosingDialog.addActionListener(_ -> {
            boolean value = showClosingDialog.isSelected();
            App.settings.setProperty("settings.showClosingDialog", String.valueOf(value));
        });
    }

}
