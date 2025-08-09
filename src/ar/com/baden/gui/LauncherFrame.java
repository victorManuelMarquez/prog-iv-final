package ar.com.baden.gui;

import ar.com.baden.gui.component.InfoPane;

import javax.swing.*;
import javax.swing.text.Caret;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LauncherFrame extends JFrame {

    private LauncherFrame(String title) throws HeadlessException {
        super(title);
        // variables
        LaunchWorker worker;

        // componentes
        InfoPane infoPane = new InfoPane();
        JScrollPane scrollPane = new JScrollPane(infoPane);
        JProgressBar progressBar = new JProgressBar();

        // instalando componentes
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(progressBar, BorderLayout.SOUTH);

        // se requiere que el componente esté instalado para las operaciones
        worker = new LaunchWorker(infoPane);

        /* AJUSTES */
        // ventana
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        if (UIManager.getLookAndFeel().getSupportsWindowDecorations()) {
            setUndecorated(true);
            if (JFrame.isDefaultLookAndFeelDecorated()) {
                getRootPane().setWindowDecorationStyle(JRootPane.NONE);
            }
        }
        // área de texto
        infoPane.setEditable(false);
        infoPane.setFocusable(false);
        Caret caret = infoPane.getCaret();
        caret.setVisible(false);
        // barra de progreso
        progressBar.setStringPainted(true);

        // eventos
        worker.addPropertyChangeListener(evt -> {
            if ("progress".equals(evt.getPropertyName())) {
                if (evt.getNewValue() instanceof Integer percentage) {
                    progressBar.setValue(percentage);
                }
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                worker.execute();
            }

            @Override
            public void windowClosing(WindowEvent e) {
                worker.cancel(true);
            }
        });
    }

    private void calculateSize() {
        Dimension mainScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = Math.ceilDiv(mainScreenSize.width, 3);
        int height = Math.ceilDiv(mainScreenSize.height, 3);
        setSize(new Dimension(width, height));
    }

    public static void createAndShow() {
        try {
            LauncherFrame frame = new LauncherFrame("Bienvenido");
            frame.calculateSize();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } catch (RuntimeException e) {
            e.printStackTrace(System.err);
        }
    }

}
