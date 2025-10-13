package ar.com.baden.gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame(String title) throws HeadlessException {
        super(title);
        // componentes
        setJMenuBar(new JMenuBar());
        JMenu fileMenu = new JMenu("Archivo");
        JMenuItem settingsItem = new JMenuItem("Configuración");

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
