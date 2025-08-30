package ar.com.baden.gui.component;

import ar.com.baden.main.App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;
import java.util.stream.Stream;

public class LookPanel extends SectionPanel {

    public LookPanel(String name) {
        super(name);
        // variables
        GroupLayout groupLayout = new GroupLayout(getMainContainer());
        GroupLayout.Alignment leading = GroupLayout.Alignment.LEADING;
        GroupLayout.Alignment baseline = GroupLayout.Alignment.BASELINE;

        // componentes
        JLabel themeLabel = new JLabel("Tema");
        JComboBox<UIManager.LookAndFeelInfo> lafCombo = new JComboBox<>(createModel());
        lafCombo.setRenderer(new LookAndFeelInfoRenderer());
        JLabel familyLabel = new JLabel("Familia");
        JComboBox<String> familiesCombo = new JComboBox<>(new Vector<>(App.availableFontFamilyNames));
        familiesCombo.setSelectedItem(familiesCombo.getFont().getFamily());

        // instalando componentes
        groupLayout.setHorizontalGroup(groupLayout.createSequentialGroup()
                .addGroup(groupLayout.createParallelGroup(leading)
                        .addComponent(themeLabel)
                        .addComponent(familyLabel))
                .addGroup(groupLayout.createParallelGroup(leading)
                        .addComponent(lafCombo)
                        .addComponent(familiesCombo)));

        groupLayout.setVerticalGroup(groupLayout.createSequentialGroup()
                .addGroup(groupLayout.createParallelGroup(baseline)
                        .addComponent(themeLabel)
                        .addComponent(lafCombo))
                .addGroup(groupLayout.createParallelGroup(baseline)
                        .addComponent(familyLabel)
                        .addComponent(familiesCombo)));

        // ajustes
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);
        getMainContainer().setLayout(groupLayout);

        // eventos
        ActionListener familyChangeListener = _ -> {
            Object selection = familiesCombo.getSelectedItem();
            if (selection != null) {
                Stream<Object> families;
                families = App.settings.keySet().stream().filter(k -> k.toString().endsWith(".family"));
                families.forEach(k -> App.settings.setProperty(k.toString(), selection.toString()));
            }
        };
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
                    familiesCombo.removeActionListener(familyChangeListener);
                    familiesCombo.setSelectedItem(familiesCombo.getFont().getFamily());
                    familiesCombo.addActionListener(familyChangeListener);
                    App.settings.setProperty("settings.lookAndFeel.className", info.getClassName());
                    App.settings.setProperty("settings.lookAndFeel.id", info.getName());
                    ancestor.setCursor(Cursor.getDefaultCursor());
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        });
        familiesCombo.addActionListener(familyChangeListener);
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
