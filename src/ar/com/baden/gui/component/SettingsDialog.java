package ar.com.baden.gui.component;

import ar.com.baden.gui.ISizeCalculation;
import ar.com.baden.main.App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeListener;

public class SettingsDialog extends ModalDialog implements ISizeCalculation {

    private SettingsDialog(Window owner, String title) {
        super(owner, title);
        /* variables */
        GroupLayout groupLayout = new GroupLayout(getContentPane());
        LayoutStyle.ComponentPlacement related = LayoutStyle.ComponentPlacement.RELATED;
        GroupLayout.Alignment leading = GroupLayout.Alignment.LEADING;
        PropertyChangeListener settingsChanges;
        // componentes
        SettingsTreePanel treePanel = new SettingsTreePanel();
        JButton resetButton = new JButton("Restablecer");
        JButton okButton = new JButton("Aceptar");
        JButton applyBtn = new JButton("Aplicar");
        JButton cancelBtn = new JButton("Cancelar");

        // instalando componentes
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup()
                .addComponent(treePanel)
                .addGroup(groupLayout.createSequentialGroup()
                        .addComponent(resetButton)
                        .addPreferredGap(related, GroupLayout.PREFERRED_SIZE, Short.MAX_VALUE)
                        .addComponent(okButton)
                        .addComponent(cancelBtn)
                        .addComponent(applyBtn)));

        groupLayout.setVerticalGroup(groupLayout.createSequentialGroup()
                .addComponent(treePanel)
                .addGroup(groupLayout.createParallelGroup(leading)
                    .addComponent(resetButton)
                    .addComponent(okButton)
                    .addComponent(cancelBtn)
                    .addComponent(applyBtn)));

        // ajustes
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);
        setLayout(groupLayout);
        getRootPane().setDefaultButton(okButton);
        resetButton.setMnemonic(KeyEvent.VK_R);
        okButton.setMnemonic(KeyEvent.VK_A);
        applyBtn.setEnabled(false);
        applyBtn.setMnemonic(KeyEvent.VK_P);
        cancelBtn.setMnemonic(KeyEvent.VK_C);
        setResizable(false);

        /* eventos */
        SwingUtilities.invokeLater(okButton::requestFocusInWindow);
        // este oyente notificará al botón "Aplicar" ante cualquier cambio en la configuración
        // y deberá removerse de la misma al cerrar esta ventana
        settingsChanges = _ -> applyBtn.setEnabled(App.settings.hasChanges());
        App.settings.addPropertyChangeListener(settingsChanges);
        // Botones
        resetButton.addActionListener(_ -> App.settings.loadDefaults());
        okButton.addActionListener(_ -> {
            App.settings.applyChanges();
            dispose();
        });
        applyBtn.addActionListener(_ -> {
            App.settings.applyChanges();
            okButton.requestFocusInWindow();
        });
        cancelBtn.addActionListener(_ -> {
            App.settings.clearChanges();
            dispose();
        });
        // ventana
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                App.settings.clearChanges();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                App.settings.removePropertyChangeListener(settingsChanges);
            }
        });
    }

    @Override
    public void calculateSize() {
        Dimension mainScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) Math.ceil(mainScreenSize.width * 0.5);
        int height = (int) Math.ceil(mainScreenSize.height * 0.75);
        setSize(new Dimension(width, height));
    }

    public static void createAndShow(Window owner) {
        try {
            SettingsDialog dialog = new SettingsDialog(owner, "Configuración");
            dialog.calculateSize();
            dialog.setLocationRelativeTo(owner);
            dialog.setVisible(true);
        } catch (RuntimeException e) {
            e.printStackTrace(System.err);
        }
    }

}
