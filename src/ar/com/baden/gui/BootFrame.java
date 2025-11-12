package ar.com.baden.gui;

import javax.swing.*;
import java.awt.*;

public class BootFrame extends JFrame implements Runnable {

    public BootFrame(String title) throws HeadlessException {
        super(title);
        // componentes
        getContentPane().add(new JScrollPane(new JTextArea(20, 40)));
        getContentPane().add(new JProgressBar(), BorderLayout.SOUTH);

        // ajustes
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void run() {
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
