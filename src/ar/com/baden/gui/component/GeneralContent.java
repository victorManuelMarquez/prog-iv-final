package ar.com.baden.gui.component;

import ar.com.baden.main.App;

import javax.swing.*;

public class GeneralContent extends ContentPanel {

    public GeneralContent(String title) {
        super(title);
        // variables
        String showDialog = App.properties.getProperty("settings.showClosingDialog");

        // componentes
        JCheckBox showClosingDialogBtn = new JCheckBox("Confirmar para salir");
        showClosingDialogBtn.setSelected(Boolean.parseBoolean(showDialog));

        // instalando componentes
        add(showClosingDialogBtn);
    }

}
