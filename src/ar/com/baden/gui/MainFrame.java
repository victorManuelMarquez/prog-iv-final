package ar.com.baden.gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame(String title) throws HeadlessException {
        super(title);
        // componentes
        setJMenuBar(new JMenuBar());
        JMenu fileMenu = new JMenu("Archivo");
        JMenuItem exitOption = new JMenuItem("Salir");

        // instalando componentes
        fileMenu.add(exitOption);
        getJMenuBar().add(fileMenu);

        // ajustes
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // eventos
        exitOption.addActionListener(_ -> dispose());
    }

    @Override
    public void pack() {
        Dimension mainScreen = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) Math.ceil(mainScreen.width * .75);
        int height = (int) Math.ceil(mainScreen.height * .7);
        Dimension size = new Dimension(width, height);
        setSize(size);
    }

}
