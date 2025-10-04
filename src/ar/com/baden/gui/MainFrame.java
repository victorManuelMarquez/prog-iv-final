package ar.com.baden.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.CTRL_DOWN_MASK;
import static java.awt.event.KeyEvent.ALT_DOWN_MASK;

public class MainFrame extends JFrame {

    public MainFrame(String title) throws HeadlessException {
        super(title);
        // componentes
        JMenu fileMenu = new JMenu("Archivo");
        fileMenu.setMnemonic(KeyEvent.VK_A);
        JMenuItem settingsItem = new JMenuItem("Configuración");
        settingsItem.setMnemonic(KeyEvent.VK_C);
        settingsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, CTRL_DOWN_MASK|ALT_DOWN_MASK));
        JMenuItem exitItem = new JMenuItem("Salir");
        exitItem.setMnemonic(KeyEvent.VK_S);

        // instalando componentes
        setJMenuBar(new JMenuBar());
        fileMenu.add(settingsItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        getJMenuBar().add(fileMenu);

        // ajustes
        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // eventos
        settingsItem.addActionListener(_ -> SettingsDialog.createAndShow(this));
        exitItem.addActionListener(_ -> dispose());
    }

    @Override
    public void pack() {
        Dimension mainScreen = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) Math.ceil(mainScreen.width * .75f);
        int height = (int) Math.ceil(mainScreen.height * .75f);
        setSize(new Dimension(width, height));
    }

    public void displayOnScreen() {
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
