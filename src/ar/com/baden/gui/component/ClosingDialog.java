package ar.com.baden.gui.component;

import ar.com.baden.main.App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;

public class ClosingDialog extends ModalDialog {

    private int response = JOptionPane.DEFAULT_OPTION;

    private ClosingDialog(Window owner, String title) {
        super(owner, title);
        /* variables */
        GroupLayout.Alignment leading = GroupLayout.Alignment.LEADING;
        GroupLayout.Alignment baseline = GroupLayout.Alignment.BASELINE;
        LayoutStyle.ComponentPlacement unrelated = LayoutStyle.ComponentPlacement.UNRELATED;
        GroupLayout groupLayout = new GroupLayout(getContentPane());
        String message = "¿Está seguro de abandonar del programa?";
        String confirmExit = "No preguntar nuevamente";
        String exit = "Salir";
        String cancel = "Cancelar";

        // componentes
        JLabel iconLabel = new JLabel(UIManager.getIcon("OptionPane.questionIcon"));
        JLabel messageLabel = new JLabel(message);
        JCheckBox confirmExitBtn = new JCheckBox(confirmExit);
        JButton exitButton = new JButton(exit);
        JButton cancelBtn = new JButton(cancel);

        // instalando componentes
        groupLayout.setHorizontalGroup(groupLayout.createSequentialGroup()
                .addComponent(iconLabel)
                .addGroup(groupLayout.createParallelGroup(leading)
                        .addComponent(messageLabel)
                        .addComponent(confirmExitBtn)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addPreferredGap(unrelated, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
                                .addComponent(exitButton)))
                .addGroup(groupLayout.createParallelGroup(leading)
                        .addComponent(cancelBtn)));

        groupLayout.setVerticalGroup(groupLayout.createSequentialGroup()
                .addGroup(groupLayout.createParallelGroup(baseline)
                        .addComponent(iconLabel)
                        .addComponent(messageLabel))
                .addGroup(groupLayout.createParallelGroup(leading)
                        .addComponent(confirmExitBtn))
                .addGroup(groupLayout.createParallelGroup(leading)
                        .addComponent(exitButton)
                        .addComponent(cancelBtn)));

        // ajustes
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);
        setLayout(groupLayout);
        getRootPane().setDefaultButton(exitButton);
        confirmExitBtn.setMnemonic(KeyEvent.VK_N);
        exitButton.setMnemonic(KeyEvent.VK_S);
        cancelBtn.setMnemonic(KeyEvent.VK_C);

        // eventos
        SwingUtilities.invokeLater(exitButton::requestFocusInWindow);
        PropertyChangeListener changeListener = evt -> {
            App.settings.forEach((k, v) -> System.out.println(k + " -> " + v));
            System.out.print(evt.getPropertyName() + ": ");
            System.out.println(evt.getOldValue() + " -> " + evt.getNewValue());
        };
        App.settings.addPropertyChangeListener(changeListener);
        confirmExitBtn.addActionListener(_ -> {
            String newValue = Boolean.toString(!confirmExitBtn.isSelected());
            App.settings.setProperty("settings.showClosingDialog", newValue);
        });
        exitButton.addActionListener(_ -> {
            response = JOptionPane.OK_OPTION;
            App.settings.removePropertyChangeListener(changeListener);
            dispose();
        });
        cancelBtn.addActionListener(_ -> {
            response = JOptionPane.CANCEL_OPTION;
            App.settings.removePropertyChangeListener(changeListener);
            dispose();
        });
    }

    public static int createAndShow(Window owner) {
        try {
            ClosingDialog dialog = new ClosingDialog(owner, "Confirmar cierre");
            dialog.pack();
            dialog.setLocationRelativeTo(owner);
            dialog.setVisible(true);
            return dialog.response;
        } catch (RuntimeException e) {
            e.printStackTrace(System.err);
        }
        return JOptionPane.CLOSED_OPTION;
    }

}
