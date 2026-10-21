/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view.report;

import java.awt.Frame;
import javax.swing.GroupLayout;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class KenaikanHargaDialog
extends JDialog {
    private JScrollPane jScrollPane1;
    private JTable kenaikanTable;

    public KenaikanHargaDialog(Frame parent, boolean modal, DefaultTableModel tableModel) {
        super(parent, modal);
        this.initComponents();
        this.kenaikanTable.setModel(tableModel);
    }

    private void initComponents() {
        this.jScrollPane1 = new JScrollPane();
        this.kenaikanTable = new JTable();
        this.setDefaultCloseOperation(2);
        this.jScrollPane1.setViewportView(this.kenaikanTable);
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jScrollPane1, -1, 709, Short.MAX_VALUE).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jScrollPane1, -1, 432, Short.MAX_VALUE).addContainerGap()));
        this.pack();
    }
}

