package ar.com.baden.gui;

import javax.swing.*;
import java.awt.*;

public class ClosingDialog extends JDialog {

    private int response = JOptionPane.DEFAULT_OPTION;

    private ClosingDialog(Frame owner, String title) {
        super(owner, title, ModalityType.APPLICATION_MODAL);
        // variables
        GroupLayout groupLayout = new GroupLayout(getContentPane());
        groupLayout.setAutoCreateContainerGaps(true);
        groupLayout.setAutoCreateGaps(true);
        LayoutStyle.ComponentPlacement unrelated = LayoutStyle.ComponentPlacement.UNRELATED;

        // componentes
        JLabel iconLabel = new JLabel(UIManager.getIcon("OptionPane.questionIcon"));
        JLabel messageLabel = new JLabel("¿Está seguro que desea abandonar el programa?");
        JCheckBox confirmExitBtn = new JCheckBox("No preguntar nuevamente");
        JButton exitBtn = new JButton("Salir");
        JButton cancelBtn = new JButton("Cancel");

        // instalando componentes
        groupLayout.setHorizontalGroup(groupLayout.createSequentialGroup()
                .addComponent(iconLabel)
                .addGroup(groupLayout.createParallelGroup()
                        .addComponent(messageLabel)
                        .addComponent(confirmExitBtn)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addPreferredGap(unrelated, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
                                .addComponent(exitBtn)))
                .addGroup(groupLayout.createParallelGroup()
                        .addComponent(cancelBtn)));

        groupLayout.setVerticalGroup(groupLayout.createSequentialGroup()
                .addGroup(groupLayout.createParallelGroup()
                        .addComponent(iconLabel)
                        .addComponent(messageLabel))
                .addComponent(confirmExitBtn)
                .addGroup(groupLayout.createParallelGroup()
                        .addComponent(exitBtn)
                        .addComponent(cancelBtn)));

        // ajustes
        setLayout(groupLayout);
        getRootPane().setDefaultButton(exitBtn);

        // eventos
        SwingUtilities.invokeLater(exitBtn::requestFocusInWindow);
        exitBtn.addActionListener(_ -> {
            response = JOptionPane.OK_OPTION;
            dispose();
        });
        cancelBtn.addActionListener(_ -> {
            response = JOptionPane.CANCEL_OPTION;
            dispose();
        });
    }

    public static int createAndShow(MainFrame mainFrame) {
        try {
            ClosingDialog dialog = new ClosingDialog(mainFrame, "Confirmar cierre");
            dialog.pack();
            dialog.setLocationRelativeTo(mainFrame);
            dialog.setVisible(true);
            return dialog.response;
        } catch (RuntimeException e) {
            e.printStackTrace(System.err);
        }
        return JOptionPane.UNDEFINED_CONDITION;
    }

}
