package ar.com.baden.main.ar.com.baden.gui;

import java.awt.Window;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class ClosingDialog extends ModalDialog {
    
    private int response = JOptionPane.DEFAULT_OPTION;

    private ClosingDialog(Window owner, String title) {
        super(owner, title);
        // componentes
        GroupLayout gl = new GroupLayout(getContentPane());
        JLabel iconLabel = new JLabel(UIManager.getIcon("OptionPane.questionIcon"));
        JLabel msgLabel = new JLabel("¿Está seguro que desea abandonar el programa?");
        JButton okButton = new JButton("Salir");
        JButton cancelBtn = new JButton("Cancelar");

        // instalando componentes
        gl.setAutoCreateContainerGaps(true);
        gl.setAutoCreateGaps(true);
        gl.setHorizontalGroup(gl.createParallelGroup()
            .addGroup(gl.createSequentialGroup()
                .addComponent(iconLabel)
                .addComponent(msgLabel))
            .addGroup(gl.createSequentialGroup()
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(okButton)
                .addComponent(cancelBtn)));
        gl.setVerticalGroup(gl.createSequentialGroup()
            .addGroup(gl.createParallelGroup()
                .addComponent(iconLabel)
                .addComponent(msgLabel))
            .addGroup(gl.createParallelGroup()
                .addComponent(okButton)
                .addComponent(cancelBtn)));
        
        // ajustes
        setLayout(gl);

        // eventos
        SwingUtilities.invokeLater(() -> {
            getRootPane().setDefaultButton(okButton);
        });
        okButton.addActionListener(evt -> {
            response = JOptionPane.OK_OPTION;
            dispose();
        });
        cancelBtn.addActionListener(evt -> {
            response = JOptionPane.CANCEL_OPTION;
            dispose();
        });
    }

    public static int createAndShow(Window owner) {
        ClosingDialog dialog = new ClosingDialog(owner, "Salir");
        dialog.pack();
        dialog.setLocationRelativeTo(owner);
        dialog.setVisible(true);
        return dialog.response;
    }

}
