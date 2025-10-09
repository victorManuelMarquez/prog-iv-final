package ar.com.baden.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {

    public MainFrame(String title) throws HeadlessException {
        super(title);
        // componentes
        setJMenuBar(new JMenuBar());
        JMenu fileMenu = new JMenu("Archivo");
        fileMenu.setMnemonic(KeyEvent.VK_A);
        JMenuItem settingsMenuItem = new JMenuItem("Configuración");
        JMenuItem exitItem = new JMenuItem("Salir");

        // instalando componentes
        fileMenu.add(settingsMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        getJMenuBar().add(fileMenu);

        // ajustes
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);

        // eventos
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                showClosingDialog();
            }
        });
        settingsMenuItem.addActionListener(_ -> SettingsDialog.createAndShow(this));
        exitItem.addActionListener(_ -> showClosingDialog());
    }

    @Override
    public void pack() {
        Dimension mainScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int mainWidth = (int) Math.ceil(mainScreenSize.width * .75);
        int mainHeight = (int) Math.ceil(mainScreenSize.height * .75);
        setSize(new Dimension(mainWidth, mainHeight));
    }

    private void showClosingDialog() {
        int response = ClosingDialog.createAndShow(this);
        if (response == JOptionPane.OK_OPTION) {
            dispose();
        }
    }

}
