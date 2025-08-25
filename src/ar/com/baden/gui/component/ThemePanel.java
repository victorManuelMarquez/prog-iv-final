package ar.com.baden.gui.component;

import ar.com.baden.main.App;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Vector;

public class ThemePanel extends JPanel {

    public ThemePanel(String name) {
        super(new BorderLayout());

        // componentes
        JPanel north = new JPanel(new GridBagLayout());
        JPanel center = new JPanel();
        JComboBox<UIManager.LookAndFeelInfo> lafCombo = new JComboBox<>(createModel());
        lafCombo.setRenderer(new LookAndFeelInfoRenderer());

        // instalando componentes
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        north.add(new JLabel(name), gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1.0;
        north.add(new JSeparator(), gbc);

        add(north, BorderLayout.NORTH);
        center.add(new JLabel("Tema"));
        center.add(lafCombo);
        add(center, BorderLayout.CENTER);

        // ajustes
        setName(name);

        // eventos
        lafCombo.addActionListener(_ -> {
            Object selection = lafCombo.getSelectedItem();
            if (selection instanceof UIManager.LookAndFeelInfo info) {
                Window ancestor = SwingUtilities.getWindowAncestor(this);
                ancestor.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                    SwingUtilities.updateComponentTreeUI(ancestor);
                    Window owner = ancestor.getOwner();
                    if (owner != null) {
                        SwingUtilities.updateComponentTreeUI(owner);
                    }
                    App.settings.setProperty("settings.lookAndFeel.className", info.getClassName());
                    App.settings.setProperty("settings.lookAndFeel.id", info.getName());
                    ancestor.setCursor(Cursor.getDefaultCursor());
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        });
    }

    private ComboBoxModel<UIManager.LookAndFeelInfo> createModel() {
        UIManager.LookAndFeelInfo[] installed = UIManager.getInstalledLookAndFeels();
        UIManager.LookAndFeelInfo current = null;
        for (UIManager.LookAndFeelInfo info : installed) {
            if (info.getName().equals(UIManager.getLookAndFeel().getName())) {
                current = info;
            }
        }
        DefaultComboBoxModel<UIManager.LookAndFeelInfo> model;
        model = new DefaultComboBoxModel<>(new Vector<>(List.of(installed)));
        model.setSelectedItem(current);
        return model;
    }

}
