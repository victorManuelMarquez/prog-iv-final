package ar.com.baden.main.ar.com.baden.gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame(String title) throws HeadlessException {
        super(title);
        // componentes
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Archivo");
        JMenuItem exitItem = new JMenuItem("Salir");

        // instalando componentes
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        // ajustes
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // eventos
        exitItem.addActionListener(_ -> dispose());
    }

}
