package ar.com.baden.gui.component;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import java.awt.*;

public class InfoPane extends JTextPane {

    private final Style errorStyle;

    public InfoPane() {
        Style boldStyle = getStyledDocument().addStyle("boldStyle", null);
        StyleConstants.setBold(boldStyle, true);
        errorStyle = getStyledDocument().addStyle("errorStyle", boldStyle);
        StyleConstants.setForeground(errorStyle, Color.RED);
    }

    public void appendText(String value, Style style) {
        try {
            int offset = getStyledDocument().getLength();
            getStyledDocument().insertString(offset, value, style);
            moveCaretPosition(offset + value.length());
        } catch (BadLocationException e) {
            e.printStackTrace(System.err);
        }
    }

    public Style getErrorStyle() {
        return errorStyle;
    }

}
