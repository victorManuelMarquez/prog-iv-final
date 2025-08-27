package ar.com.baden.gui.component;

import javax.swing.*;

public class SettingsGeneralPanel extends JPanel {

    public SettingsGeneralPanel() {
        super(null);
        /* variables */
        BoxLayout verticalBoxLayout = new BoxLayout(this, BoxLayout.PAGE_AXIS);

        // componentes
        GeneralPanel generalPanel = new GeneralPanel();
        LookPanel lookPanel = new LookPanel("Apariencia");

        // instalando componentes
        add(generalPanel);
        add(lookPanel);

        // ajustes
        setLayout(verticalBoxLayout);
    }

}
