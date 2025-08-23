package ar.com.baden.gui.component;

import javax.swing.*;

public class SettingsGeneralPanel extends JPanel {

    public SettingsGeneralPanel() {
        super(null);
        /* variables */
        GroupLayout groupLayout = new GroupLayout(this);

        // componentes
        GeneralPanel generalPanel = new GeneralPanel();

        // instalando componentes
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup()
                .addComponent(generalPanel));

        groupLayout.setVerticalGroup(groupLayout.createSequentialGroup()
                .addComponent(generalPanel));

        // ajustes
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);
        setLayout(groupLayout);
    }

}
