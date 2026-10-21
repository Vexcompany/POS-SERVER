/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view;

import java.awt.Frame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.GroupLayout;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import minimart.engine.controller.Helper;
import minimart.engine.model.Barang;
import minimart.engine.service.BarangService;
import minimart.ui.listener.SearchPanelListener;

public class SearchPanel
extends JDialog {
    private JScrollPane jScrollPane1;
    private JTable searchTable;
    private DefaultTableModel dtm;
    private SearchPanelListener listener;
    private BarangService barangService = new BarangService();
    private Barang barang;
    private Helper helper = Helper.getInstance();

    public SearchPanel(Frame parent, boolean modal, DefaultTableModel dtm, String title) {
        super(parent, modal);
        this.setTitle(title);
        this.initComponents();
        this.dtm = dtm;
        this.searchTable.setModel(dtm);
    }

    public void addSearchPanelListener(SearchPanelListener listener) {
        this.listener = listener;
    }

    private void initComponents() {
        this.jScrollPane1 = new JScrollPane();
        this.searchTable = new JTable();
        this.setDefaultCloseOperation(2);
        this.searchTable.setModel(new DefaultTableModel(new Object[0][], new String[0]));
        this.searchTable.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                SearchPanel.this.searchTableKeyPressed(evt);
            }
        });
        this.jScrollPane1.setViewportView(this.searchTable);
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jScrollPane1, -1, 780, Short.MAX_VALUE).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jScrollPane1, -1, 601, Short.MAX_VALUE).addContainerGap()));
        this.pack();
    }

    private void searchTableKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.listener.tableClick(this.dtm.getValueAt(this.searchTable.getSelectedRow(), 0).toString());
            this.dispose();
        }
        if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }
}

