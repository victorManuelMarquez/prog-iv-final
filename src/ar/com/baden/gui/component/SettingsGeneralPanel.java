package ar.com.baden.gui.component;

import ar.com.baden.main.App;

import javax.swing.*;

public class SettingsGeneralPanel extends JPanel {

    public SettingsGeneralPanel() {
        super(null);
        /* variables */
        GroupLayout groupLayout = new GroupLayout(this);
        boolean confirmToClose = Boolean.parseBoolean(App.settings.getProperty("settings.showClosingDialog"));
        // componentes
        JCheckBox showClosingDialog = new JCheckBox("Confirmar para salir del programa");

        // instalando componentes
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup()
                .addComponent(showClosingDialog));

        groupLayout.setVerticalGroup(groupLayout.createSequentialGroup()
                .addComponent(showClosingDialog));

        // ajustes
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);
        setLayout(groupLayout);
        showClosingDialog.setSelected(confirmToClose);

        // eventos
        showClosingDialog.addActionListener(_ -> {
            boolean value = showClosingDialog.isSelected();
            App.settings.setProperty("settings.showClosingDialog", String.valueOf(value));
        });
    }

}
