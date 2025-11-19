package ar.com.baden.gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame(String title) throws HeadlessException {
        super(title);
        // componentes
        setJMenuBar(new JMenuBar());
        JMenu fileMenu = new JMenu("Archivo");
        JMenuItem exitItem = new JMenuItem("Salir");

        // instalando componentes
        fileMenu.add(exitItem);
        getJMenuBar().add(fileMenu);

        // ajustes
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);

        // eventos
        exitItem.addActionListener(_ -> dispose());
    }

    @Override
    public void pack() {
        Dimension mainScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) Math.ceil(mainScreenSize.width * .75);
        int height = (int) Math.ceil(mainScreenSize.height * .7);
        setSize(new Dimension(width, height));
    }

}
