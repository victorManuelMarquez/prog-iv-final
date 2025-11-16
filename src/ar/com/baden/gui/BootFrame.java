package ar.com.baden.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class BootFrame extends JFrame implements Runnable {

    public BootFrame(String title) throws HeadlessException {
        super(title);
        // componentes
        InfoArea infoArea = new InfoArea();
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(infoArea);
        JProgressBar progressBar = new JProgressBar();
        progressBar.setStringPainted(true);

        // instalando componentes
        getContentPane().add(scrollPane);
        getContentPane().add(progressBar, BorderLayout.SOUTH);

        // ajustes
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // eventos
        addWindowListener(new WindowAdapter() {
            final BootstrapWorker bootstrapWorker = new BootstrapWorker(infoArea);
            @Override
            public void windowOpened(WindowEvent e) {
                bootstrapWorker.addPropertyChangeListener(evt -> {
                    if ("progress".equals(evt.getPropertyName())) {
                        int progress = (int) evt.getNewValue();
                        progressBar.setValue(progress);
                    }
                });
                bootstrapWorker.execute();
            }

            @Override
            public void windowClosing(WindowEvent e) {
                bootstrapWorker.cancel(true);
            }
        });
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

    private static class BootstrapWorker extends SwingWorker<MainFrame, String> {

        private final InfoArea infoArea;

        public BootstrapWorker(InfoArea infoArea) {
            this.infoArea = infoArea;
        }

        @Override
        protected MainFrame doInBackground() throws Exception {
            publishNewLine("Bienvenido...");
            for (int i = 0; i < 10; i++) {
                publish(String.format("Procesando... %d\n", i));
                setProgress(i * 100 / 10);
                Thread.sleep(1000);
            }
            return new MainFrame("Bienvenido");
        }

        @Override
        protected void process(List<String> chunks) {
            chunks.forEach(infoArea::append);
        }

        @Override
        protected void done() {
            try {
                Window window = SwingUtilities.windowForComponent(infoArea);
                MainFrame mainFrame = get();
                mainFrame.pack();
                mainFrame.setLocationRelativeTo(null);
                mainFrame.setVisible(true);
                window.dispose();
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
        }

        public void publishNewLine(String value) {
            publish(value + System.lineSeparator());
        }

    }

}
