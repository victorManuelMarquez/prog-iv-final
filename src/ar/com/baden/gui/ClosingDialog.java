package ar.com.baden.gui;

import ar.com.baden.main.App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class ClosingDialog extends ModalDialog {

    private int response = JOptionPane.UNDEFINED_CONDITION;

    private ClosingDialog(Window owner, String title) {
        super(owner, title);
        // variables
        GroupLayout groupLayout = new GroupLayout(getContentPane());
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);
        LayoutStyle.ComponentPlacement unrelated = LayoutStyle.ComponentPlacement.UNRELATED;

        // componentes
        JLabel iconLabel = new JLabel(UIManager.getIcon("OptionPane.questionIcon"));
        JLabel messageLabel = new JLabel("¿Está seguro de abandonar el programa?");
        JCheckBox confirmExit = new JCheckBox("No preguntar de nuevo");
        confirmExit.setMnemonic(KeyEvent.VK_N);
        JButton exitBtn = new JButton("Salir");
        exitBtn.setMnemonic(KeyEvent.VK_S);
        JButton cancelBtn = new JButton("Cancelar");
        cancelBtn.setMnemonic(KeyEvent.VK_C);

        // instalando componentes
        groupLayout.setHorizontalGroup(groupLayout.createSequentialGroup()
                .addComponent(iconLabel)
                .addGroup(groupLayout.createParallelGroup()
                        .addComponent(messageLabel)
                        .addComponent(confirmExit)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addPreferredGap(unrelated, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
                                .addComponent(exitBtn)))
                .addGroup(groupLayout.createParallelGroup()
                        .addComponent(cancelBtn)));

        groupLayout.setVerticalGroup(groupLayout.createSequentialGroup()
                .addGroup(groupLayout.createParallelGroup()
                        .addComponent(iconLabel)
                        .addComponent(messageLabel))
                .addComponent(confirmExit)
                .addGroup(groupLayout.createParallelGroup()
                        .addComponent(exitBtn)
                        .addComponent(cancelBtn)));

        // ajustes
        setLayout(groupLayout);
        getRootPane().setDefaultButton(exitBtn);

        // eventos
        SwingUtilities.invokeLater(confirmExit::requestFocusInWindow);
        confirmExit.addActionListener(_ -> {
            boolean showClosingDialog = !confirmExit.isSelected();
            App.properties.setProperty("settings.showClosingDialog", String.valueOf(showClosingDialog));
        });
        exitBtn.addActionListener(_ -> {
            response = JOptionPane.OK_OPTION;
            dispose();
        });
        cancelBtn.addActionListener(_ -> {
            response = JOptionPane.CANCEL_OPTION;
            dispose();
        });
    }

    public static int createAndShow(Window owner) {
        try {
            ClosingDialog dialog = new ClosingDialog(owner, "Atención");
            dialog.pack();
            dialog.setLocationRelativeTo(owner);
            dialog.setVisible(true);
            return dialog.response;
        } catch (RuntimeException e) {
            e.printStackTrace(System.err);
            return 0;
        }
    }

}
