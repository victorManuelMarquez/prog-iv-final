package ar.com.baden.gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame(String title) throws HeadlessException {
        super(title);
        // ajustes
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
    }

    @Override
    public void pack() {
        Dimension mainScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) Math.ceil(mainScreenSize.width * .75);
        int height = (int) Math.ceil(mainScreenSize.height * .7);
        setSize(new Dimension(width, height));
    }

}
