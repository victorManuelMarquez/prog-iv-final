package ar.com.baden.gui;

import javax.swing.*;
import java.awt.*;

public class BootFrame extends JFrame implements Runnable {

    public BootFrame(String title) throws HeadlessException {
        super(title);
        // componentes
        JTextArea infoPane = new JTextArea(15, 40);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(infoPane);
        JProgressBar progressBar = new JProgressBar();

        // instalando componentes
        getContentPane().add(scrollPane);
        getContentPane().add(progressBar, BorderLayout.SOUTH);

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
