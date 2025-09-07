package ar.com.baden.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MainFrame extends JFrame implements IScreenSizeDimension {

    public MainFrame(String title) throws HeadlessException {
        super(title);
        // componentes
        setJMenuBar(new JMenuBar());
        JMenu fileMenu = new JMenu("Archivo");
        fileMenu.setMnemonic(KeyEvent.VK_A);

        // instalando componentes
        getJMenuBar().add(fileMenu);

        // ajustes
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
    }

    @Override
    public void calculateSizeOnScreen() {
        Dimension mainScreen = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) Math.ceil(mainScreen.width * .75f);
        int height = (int) Math.ceil(mainScreen.height * .75f);
        setSize(new Dimension(width, height));
    }

    public void showOnScreen() {
        calculateSizeOnScreen();
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
