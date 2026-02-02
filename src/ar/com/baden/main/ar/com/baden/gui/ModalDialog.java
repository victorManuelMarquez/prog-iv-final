package ar.com.baden.main.ar.com.baden.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import javax.swing.JDialog;

public class ModalDialog extends JDialog {
    
    public ModalDialog(Window owner, String title) {
        super(owner, title);
        setModal(true);
    }

    @Override
    public void pack() {
        super.pack();
        if (getOwner() != null) {
            Dimension ownerSize = getOwner().getSize();
            fitSize(ownerSize, .50f);
        } else {
            Dimension mainScreen = Toolkit.getDefaultToolkit().getScreenSize();
            fitSize(mainScreen, .75f);
        }
    }
    
    private void fitSize(Dimension targetDimension, float factor) {
        int width = (int) Math.ceil(targetDimension.width * factor);
        int height = (int) Math.ceil(targetDimension.height * factor);
        int idealWidth = Math.min(width, getWidth());
        int idealHeight = Math.min(height, getHeight());
        Dimension fitted = new Dimension(idealWidth, idealHeight);
        setSize(fitted);
    }

}
