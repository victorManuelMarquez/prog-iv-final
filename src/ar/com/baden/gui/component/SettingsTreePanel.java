package ar.com.baden.gui.component;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;

public class SettingsTreePanel extends JPanel {

    public SettingsTreePanel() {
        super(null);
        /* variables */
        String generalPaneName = "General";
        String themesPaneName = "Temas";
        String fontsPaneName = "Fuentes";
        int mainWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int maxWidthTree = (int) Math.ceil(mainWidth * 0.15);
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Configuración");
        rootNode.add(new DefaultMutableTreeNode(generalPaneName));
        rootNode.add(new DefaultMutableTreeNode(themesPaneName));
        rootNode.add(new DefaultMutableTreeNode(fontsPaneName));
        // layouts
        GroupLayout groupLayout = new GroupLayout(this);
        CardLayout cardLayout = new CardLayout();
        // componentes
        JTree tree = new JTree(rootNode);
        JScrollPane westScrollPane = new JScrollPane(tree);
        JPanel cardsPanel = new JPanel(cardLayout);
        JPanel generalPanel = new SettingsGeneralPanel();
        JPanel themesPanel = new JPanel(new BorderLayout());
        JPanel fontsPanel = new JPanel(new BorderLayout());
        JScrollPane centerScrollPane = new JScrollPane(cardsPanel);

        // instalando componentes
        cardsPanel.add(generalPanel, generalPaneName);
        cardsPanel.add(themesPanel, themesPaneName);
        cardsPanel.add(fontsPanel, fontsPaneName);

        groupLayout.setHorizontalGroup(groupLayout.createSequentialGroup()
                .addComponent(westScrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, maxWidthTree)
                .addComponent(centerScrollPane));

        groupLayout.setVerticalGroup(groupLayout.createSequentialGroup()
                .addGroup(groupLayout.createParallelGroup()
                        .addComponent(westScrollPane)
                        .addComponent(centerScrollPane)));

        // ajustes
        groupLayout.setAutoCreateGaps(true);
        setLayout(groupLayout);
        tree.setRootVisible(false);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        centerScrollPane.setBorder(BorderFactory.createEmptyBorder());
        generalPanel.setName(generalPaneName);
        themesPanel.setName(themesPaneName);
        fontsPanel.setName(fontsPaneName);

        // eventos
        tree.addTreeSelectionListener(_ -> {
            DefaultMutableTreeNode lastSelection = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (lastSelection == null) {
                return;
            }
            Object selectedItem = lastSelection.getUserObject();
            if (lastSelection.isLeaf()) {
                cardLayout.show(cardsPanel, selectedItem.toString());
            }
        });
    }

}
