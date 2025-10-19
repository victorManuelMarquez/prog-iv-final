package ar.com.baden.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MainFrame extends JFrame {

    public MainFrame(String title) throws HeadlessException {
        super(title);
        // variables
        int ctrlAltModifier = KeyEvent.CTRL_DOWN_MASK | KeyEvent.ALT_DOWN_MASK;

        // componentes
        setJMenuBar(new JMenuBar());
        JMenu fileMenu = new JMenu("Archivo");
        JMenuItem settingsItem = new JMenuItem("Configuración");
        settingsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ctrlAltModifier));

        // instalando componentes
        fileMenu.add(settingsItem);
        getJMenuBar().add(fileMenu);

        // ajustes
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);

        // eventos
        settingsItem.addActionListener(_ -> SettingsDialog.createAndShow(this));
    }

    @Override
    public void pack() {
        Dimension mainScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int mainWidth = (int) Math.ceil(mainScreenSize.width * .75);
        int mainHeight = (int) Math.ceil(mainScreenSize.height * .75);
        setSize(new Dimension(mainWidth, mainHeight));
    }

}
