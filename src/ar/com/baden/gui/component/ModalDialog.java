package ar.com.baden.gui.component;

import javax.swing.*;
import java.awt.*;

public class ModalDialog extends JDialog {

    public ModalDialog(Window owner, String title) {
        super(owner, title);
        // ajustes
        setModal(true);
    }

}
