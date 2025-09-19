package ar.com.baden.gui;

import ar.com.baden.main.App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame implements IScreenSizeDimension {

    public MainFrame(String title) throws HeadlessException {
        super(title);
        // variables
        int ctrlAlt = KeyEvent.CTRL_DOWN_MASK | KeyEvent.ALT_DOWN_MASK;

        // componentes
        setJMenuBar(new JMenuBar());
        JMenu fileMenu = new JMenu("Archivo");
        fileMenu.setMnemonic(KeyEvent.VK_A);
        JMenuItem settingsItem = new JMenuItem("Configuración");
        settingsItem.setMnemonic(KeyEvent.VK_C);
        settingsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ctrlAlt));

        // instalando componentes
        fileMenu.add(settingsItem);
        getJMenuBar().add(fileMenu);

        // ajustes
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);

        // eventos
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                String showValue = App.properties.getProperty("settings.showClosingDialog");
                if (Boolean.parseBoolean(showValue)) {
                    int response = ClosingDialog.createAndShow(e.getWindow());
                    if (response == JOptionPane.OK_OPTION) {
                        dispose();
                    }
                } else {
                    dispose();
                }
            }
        });
        settingsItem.addActionListener(_ -> SettingsDialog.createAndShow(this));
    }

    @Override
    public void calculateSizeOnScreen() {
        Dimension mainScreen = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) Math.ceil(mainScreen.width * .75f);
        int height = (int) Math.ceil(mainScreen.height * .75f);
        setSize(new Dimension(width, height));
    }

    public void showOnScreen() {
        calculateSizeOnScreen();
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
