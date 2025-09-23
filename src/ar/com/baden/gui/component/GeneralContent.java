package ar.com.baden.gui.component;

import ar.com.baden.main.App;

import javax.swing.*;

public class GeneralContent extends ContentPanel {

    public GeneralContent(String title) {
        super(title);
        // variables
        String showClosingDialogKey = "settings.showClosingDialog";
        String showDialogValue = App.properties.getProperty(showClosingDialogKey);

        // componentes
        JCheckBox showClosingDialogBtn = new JCheckBox("Confirmar para salir");
        showClosingDialogBtn.setSelected(Boolean.parseBoolean(showDialogValue));

        // instalando componentes
        add(showClosingDialogBtn);

        // eventos
        App.properties.addPropertyChangeListener("resetToDefaults", _ -> {
            System.out.println("reset");
            String value = App.properties.getProperty(showClosingDialogKey);
            showClosingDialogBtn.setSelected(Boolean.parseBoolean(value));
        });
        showClosingDialogBtn.addActionListener(_ -> {
            boolean selected = showClosingDialogBtn.isSelected();
            App.properties.setProperty(showClosingDialogKey, String.valueOf(selected));
        });
    }

}
