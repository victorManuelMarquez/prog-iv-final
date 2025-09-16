package ar.com.baden.gui;

import javax.swing.*;
import java.awt.*;

public class ModalDialog extends JDialog {

    public ModalDialog(Window owner, String title) {
        super(owner, title, ModalityType.APPLICATION_MODAL);
        if (UIManager.getLookAndFeel().getSupportsWindowDecorations()) {
            if (!JDialog.isDefaultLookAndFeelDecorated()) {
                setUndecorated(true);
                getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
            }
        }
    }

}
