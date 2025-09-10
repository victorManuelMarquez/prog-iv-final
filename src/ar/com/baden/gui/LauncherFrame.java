package ar.com.baden.gui;

import ar.com.baden.gui.component.InfoTextPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LauncherFrame extends JFrame implements IScreenSizeDimension {

    private LauncherFrame(String title) throws HeadlessException {
        super(title);
        // componentes
        InfoTextPane textPane = new InfoTextPane();
        textPane.setEditable(false);
        textPane.setFocusable(false);
        textPane.getCaret().setVisible(false);
        JProgressBar progressBar = new JProgressBar();

        // instalando componentes
        getContentPane().add(new JScrollPane(textPane));
        getContentPane().add(progressBar, BorderLayout.SOUTH);

        // ajustes
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        if (UIManager.getLookAndFeel().getSupportsWindowDecorations()) {
            setUndecorated(true);
            if (JFrame.isDefaultLookAndFeelDecorated()) {
                getRootPane().setWindowDecorationStyle(JRootPane.NONE);
            }
        }

        // eventos
        addWindowListener(new WindowAdapter() {
            final BootstrapWorker worker = new BootstrapWorker(textPane);

            @Override
            public void windowOpened(WindowEvent e) {
                worker.addPropertyChangeListener(evt -> {
                    if ("progress".equals(evt.getPropertyName()) && evt.getNewValue() instanceof Integer progress) {
                        progressBar.setValue(progress);
                    } else if ("indeterminate".equals(evt.getPropertyName()) && evt.getNewValue() instanceof Boolean active) {
                        progressBar.setIndeterminate(active);
                    }
                });
                worker.execute();
            }

            @Override
            public void windowClosing(WindowEvent e) {
                worker.stop();
            }
        });
    }

    @Override
    public void calculateSizeOnScreen() {
        Dimension mainScreen = Toolkit.getDefaultToolkit().getScreenSize();
        int width = Math.ceilDiv(mainScreen.width, 3);
        int height = Math.ceilDiv(mainScreen.height, 3);
        setSize(new Dimension(width, height));
    }

    public static void createAndShow() {
        try {
            LauncherFrame frame = new LauncherFrame("Bienvenido");
            frame.calculateSizeOnScreen();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } catch (RuntimeException e) {
            e.printStackTrace(System.err);
        }
    }

}
