package ar.com.baden.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame implements ISizeCalculation {

    private MainFrame(String title) throws HeadlessException {
        super(title);
        // variables
        setJMenuBar(new JMenuBar());

        // componentes
        JMenu fileMenu = new JMenu("Archivo");
        JMenuItem exitItem = new JMenuItem("Salir");

        // instalando componentes
        fileMenu.add(exitItem);
        getJMenuBar().add(fileMenu);

        /* ajustes */
        // ventana
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        // componentes
        fileMenu.setMnemonic(KeyEvent.VK_A);
        exitItem.setMnemonic(KeyEvent.VK_S);
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, KeyEvent.ALT_DOWN_MASK));

        // eventos
        exitItem.addActionListener(_ -> showClosingDialog());
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                showClosingDialog();
            }
        });
    }

    public void calculateSize() {
        Dimension mainMonitorSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) Math.ceil(mainMonitorSize.width * 0.75);
        int height = (int) Math.ceil(mainMonitorSize.height * 0.75);
        setSize(new Dimension(width, height));
    }

    private void showClosingDialog() {
        int option = JOptionPane.OK_CANCEL_OPTION;
        int icon = JOptionPane.QUESTION_MESSAGE;
        String message = "¿Está seguro de abandonar el programa?";
        String title = "Confirmar cierre";
        int response = JOptionPane.showConfirmDialog(this, message, title, option, icon);
        if (response == JOptionPane.OK_OPTION) {
            dispose();
        }
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
