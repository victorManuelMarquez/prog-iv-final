package ar.com.baden.gui.component;

import ar.com.baden.main.App;

import javax.swing.*;
import java.awt.*;

public class GeneralContent extends ContentPanel {

    public GeneralContent(String title) {
        super(title);
        // variables
        GroupLayout groupLayout = new GroupLayout(getMainContentPane());
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);
        String showClosingDialogKey = "settings.showClosingDialog";
        String showDialogValue = App.properties.getProperty(showClosingDialogKey);

        // componentes
        JCheckBox showClosingDialogBtn = new JCheckBox("Confirmar para salir");
        showClosingDialogBtn.setSelected(Boolean.parseBoolean(showDialogValue));
        JLabel lafLabel = new JLabel("Tema");
        JComboBox<UIManager.LookAndFeelInfo> lafCombo = new JComboBox<>();
        lafCombo.setRenderer(new LookAndFeelInfoRenderer());
        UIManager.LookAndFeelInfo[] installedLookAndFeels = UIManager.getInstalledLookAndFeels();
        for (UIManager.LookAndFeelInfo info : installedLookAndFeels) {
            lafCombo.addItem(info);
            if (info.getClassName().equals(UIManager.getLookAndFeel().getClass().getName())) {
                lafCombo.setSelectedItem(info);
            }
        }

        // instalando componentes
        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(showClosingDialogBtn)
                .addGroup(groupLayout.createSequentialGroup()
                        .addComponent(lafLabel)
                        .addComponent(lafCombo)));

        groupLayout.setVerticalGroup(groupLayout.createSequentialGroup()
                .addComponent(showClosingDialogBtn)
                .addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(lafLabel)
                        .addComponent(lafCombo)));

        // ajustes
        setLayout(groupLayout);

        // eventos
        App.properties.addPropertyChangeListener("resetToDefaults", _ -> {
            String value = App.properties.getProperty(showClosingDialogKey);
            showClosingDialogBtn.setSelected(Boolean.parseBoolean(value));
        });
        showClosingDialogBtn.addActionListener(_ -> {
            boolean selected = showClosingDialogBtn.isSelected();
            App.properties.setProperty(showClosingDialogKey, String.valueOf(selected));
        });
        lafCombo.addActionListener(_ -> {
            Object selection = lafCombo.getSelectedItem();
            if (selection instanceof UIManager.LookAndFeelInfo info) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                    Window ancestor = SwingUtilities.getWindowAncestor(getMainContentPane());
                    if (ancestor != null) {
                        SwingUtilities.updateComponentTreeUI(ancestor);
                        for (Window owner = ancestor.getOwner(); owner != null; owner = owner.getOwner()) {
                            SwingUtilities.updateComponentTreeUI(owner);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
            }
        });
    }

}
