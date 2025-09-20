package ar.com.baden.gui;

import ar.com.baden.gui.component.GeneralContent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class SettingsDialog extends ModalDialog {

    private SettingsDialog(Window owner, String title) {
        super(owner, title);
        // variables
        GroupLayout groupLayout = new GroupLayout(getContentPane());
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);

        // componentes
        GeneralContent generalContent = new GeneralContent("General");
        JSeparator horizontalSeparator = new JSeparator(JSeparator.HORIZONTAL);
        JButton okBtn = new JButton("Ok");
        okBtn.setMnemonic(KeyEvent.VK_O);
        JButton cancelBtn = new JButton("Cancelar");
        cancelBtn.setMnemonic(KeyEvent.VK_C);
        JButton applyBtn = new JButton("Aplicar");
        applyBtn.setEnabled(false);
        applyBtn.setMnemonic(KeyEvent.VK_A);

        // instalando componentes
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup()
                .addComponent(generalContent)
                .addComponent(horizontalSeparator)
                .addGroup(groupLayout.createSequentialGroup()
                        .addComponent(okBtn)
                        .addComponent(cancelBtn)
                        .addComponent(applyBtn)));

        groupLayout.setVerticalGroup(groupLayout.createSequentialGroup()
                .addComponent(generalContent)
                .addComponent(horizontalSeparator)
                .addGroup(groupLayout.createParallelGroup()
                        .addComponent(okBtn)
                        .addComponent(cancelBtn)
                        .addComponent(applyBtn)));

        // ajustes
        getRootPane().setDefaultButton(okBtn);
        setLayout(groupLayout);

        // eventos
        SwingUtilities.invokeLater(okBtn::requestFocusInWindow);
        okBtn.addActionListener(_ -> dispose());
        cancelBtn.addActionListener(_ -> dispose());
        applyBtn.addActionListener(_ -> dispose());
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
