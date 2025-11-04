package ar.com.baden.gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame(String title) throws HeadlessException {
        super(title);
        // ajustes
        setDefaultCloseOperation(EXIT_ON_CLOSE);
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
