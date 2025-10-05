package ar.com.baden.gui.component;

import ar.com.baden.gui.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class ClosingDialog extends JDialog {

    private int response = JOptionPane.DEFAULT_OPTION;

    private ClosingDialog(Window owner, String title) {
        super(owner, title, ModalityType.APPLICATION_MODAL);
        // variables
        GroupLayout layout = new GroupLayout(getContentPane());
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        LayoutStyle.ComponentPlacement unrelated = LayoutStyle.ComponentPlacement.UNRELATED;

        // componentes
        JLabel questionIconLabel = new JLabel(UIManager.getIcon("OptionPane.questionIcon"));
        JLabel messageLabel = new JLabel("¿Está seguro que desea abandonar el programa?");
        JCheckBox confirmExitBtn = new JCheckBox("No preguntar nuevamente");
        confirmExitBtn.setMnemonic(KeyEvent.VK_N);
        JButton exitBtn = new JButton("Salir");
        exitBtn.setMnemonic(KeyEvent.VK_S);
        JButton cancelBtn = new JButton("Cancelar");
        cancelBtn.setMnemonic(KeyEvent.VK_C);

        // instalando componentes
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addComponent(questionIconLabel)
                .addGroup(layout.createParallelGroup()
                        .addComponent(messageLabel)
                        .addComponent(confirmExitBtn)
                        .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(unrelated, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
                                .addComponent(exitBtn)))
                .addGroup(layout.createParallelGroup()
                        .addComponent(cancelBtn)));

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                        .addComponent(questionIconLabel)
                        .addComponent(messageLabel))
                .addComponent(confirmExitBtn)
                .addGroup(layout.createParallelGroup()
                        .addComponent(exitBtn)
                        .addComponent(cancelBtn)));

        // ajustes
        setLayout(layout);
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
            ClosingDialog dialog = new ClosingDialog(mainFrame, "Atención");
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
