package ar.com.baden.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static java.awt.event.KeyEvent.ALT_DOWN_MASK;
import static java.awt.event.KeyEvent.CTRL_DOWN_MASK;

public class MainFrame extends JFrame {

    private MainFrame(String title) throws HeadlessException {
        super(title);
        // componentes
        setJMenuBar(new JMenuBar());
        JMenu fileMenu = new JMenu("Archivo");
        fileMenu.setMnemonic(KeyEvent.VK_A);
        JMenuItem settingsMenuItem = new JMenuItem("Configuración");
        settingsMenuItem.setMnemonic(KeyEvent.VK_C);
        settingsMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, CTRL_DOWN_MASK|ALT_DOWN_MASK));

        // instalando componentes
        fileMenu.add(settingsMenuItem);
        getJMenuBar().add(fileMenu);

        // ajustes
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);

        // eventos
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
        settingsMenuItem.addActionListener(_ -> SettingsDialog.createAndShow(this));
    }

    @Override
    public void pack() {
        Dimension mainScreen = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) Math.ceil(mainScreen.width * .75);
        int height = (int) Math.ceil(mainScreen.height * .75);
        setSize(new Dimension(width, height));
    }

    public static void createAndShow() {
        try {
            MainFrame frame = new MainFrame("Bienvenido");
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } catch (RuntimeException e) {
            e.printStackTrace(System.err);
        }
    }

}
