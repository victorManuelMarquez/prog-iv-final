package ar.com.baden.main.ar.com.baden.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);

        // eventos
        addWindowListener(new ClosingMainFrame());
        exitItem.addActionListener(evt -> {
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        });
    }

    @Override
    public void pack() {
        // dejo que la función original establezca el tamaño adecuado
        super.pack();
        Dimension packSize = getSize(); // resguardo el tamaño calculado previamente

        // obtengo las dimensiones de la pantalla principal
        Dimension mainSize = Toolkit.getDefaultToolkit().getScreenSize();

        // nuevos valores para el ancho y alto
        int width = (int) Math.ceil(mainSize.width * .75);
        int height = (int) Math.ceil(mainSize.height * .7);
        Dimension size = new Dimension(width, height);

        // establezco los nuevos valores
        setSize(size);
        setPreferredSize(packSize);
        setMinimumSize(new Dimension(Math.min(720, size.width), Math.min(540, size.height)));
    }

    static class ClosingMainFrame extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent e) {
            Window sourceWindow = e.getWindow();
            int response = ClosingDialog.createAndShow(sourceWindow);
            if (response == JOptionPane.OK_OPTION || response == JOptionPane.DEFAULT_OPTION) {
                sourceWindow.dispose();
            }
        }

    }

}
