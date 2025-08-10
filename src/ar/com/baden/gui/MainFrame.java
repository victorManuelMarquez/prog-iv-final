package ar.com.baden.gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame implements ISizeCalculation {

    private MainFrame(String title) throws HeadlessException {
        super(title);
        // ajustes
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
    }

    public void calculateSize() {
        Dimension mainMonitorSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) Math.ceil(mainMonitorSize.width * 0.75);
        int height = (int) Math.ceil(mainMonitorSize.height * 0.75);
        setSize(new Dimension(width, height));
    }

    public static void createAndShow() {
        try {
            MainFrame frame = new MainFrame("Bienvenido");
            frame.calculateSize();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } catch (RuntimeException e) {
            e.printStackTrace(System.err);
        }
    }

}
