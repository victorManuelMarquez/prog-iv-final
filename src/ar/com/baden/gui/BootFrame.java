package ar.com.baden.gui;

import javax.swing.*;
import java.awt.*;

public class BootFrame extends JFrame implements Runnable {

    public BootFrame(String title) throws HeadlessException {
        super(title);
        // componentes
        JTextArea infoArea = new InfoArea();
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(infoArea);
        JProgressBar progressBar = new JProgressBar();
        progressBar.setStringPainted(true);

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

    private static class InfoArea extends JTextArea {

        public InfoArea() {
            super(15, 40);
            setEditable(false);
            setFocusable(false);
            getCaret().setVisible(false);
            setWrapStyleWord(true);
            setLineWrap(true);
        }

    }

}
