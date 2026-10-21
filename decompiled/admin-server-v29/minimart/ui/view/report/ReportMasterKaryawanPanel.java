/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view.report;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import minimart.engine.component.UppercaseDocumentFilter;
import minimart.engine.model.Karyawan;
import minimart.engine.service.KaryawanService;
import minimart.ui.listener.DefaultPanelListener;

public class ReportMasterKaryawanPanel
extends JPanel {
    private JButton cariButton;
    private JLabel jLabel1;
    private JScrollPane jScrollPane1;
    private JButton keluarButton;
    private JTable reportTable;
    private JTextField searchField;
    private String[] reportColumn = new String[]{"Kode Karyawan", "Nama Karyawan", "Role"};
    private DefaultTableModel reportTableModel = new DefaultTableModel(new String[0][1], this.reportColumn);
    private DefaultPanelListener listener;
    private KaryawanService karyawanService = new KaryawanService();
    private DocumentFilter filter = new UppercaseDocumentFilter();

    public ReportMasterKaryawanPanel() {
        this.initComponents();
        this.loadKaryawan("");
        this.jScrollPane1.setHorizontalScrollBarPolicy(30);
        TableColumn col1 = this.reportTable.getColumnModel().getColumn(1);
        TableColumn col2 = this.reportTable.getColumnModel().getColumn(2);
        col1.setPreferredWidth(130);
        col2.setPreferredWidth(130);
        ((AbstractDocument)this.searchField.getDocument()).setDocumentFilter(this.filter);
    }

    public void addListener(DefaultPanelListener listener) {
        this.listener = listener;
    }

    private void clearTable() {
        int size = this.reportTableModel.getRowCount();
        for (int a = 0; a < size; ++a) {
            this.reportTableModel.removeRow(0);
        }
    }

    private void loadKaryawan(String nama) {
        this.clearTable();
        List<Karyawan> karyawans = this.karyawanService.getKaryawans(nama);
        for (Karyawan karyawan : karyawans) {
            Object[] rowValue = new Object[]{karyawan.getId(), karyawan.getNama(), karyawan.getRole()};
            this.reportTableModel.addRow(rowValue);
        }
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jScrollPane1 = new JScrollPane();
        this.reportTable = new JTable();
        this.keluarButton = new JButton();
        this.cariButton = new JButton();
        this.searchField = new JTextField();
        this.setPreferredSize(new Dimension(800, 600));
        this.jLabel1.setFont(new Font("Tahoma", 1, 18));
        this.jLabel1.setText("Report Master Karyawan");
        this.reportTable.setModel(this.reportTableModel);
        this.jScrollPane1.setViewportView(this.reportTable);
        this.keluarButton.setText("Keluar");
        this.keluarButton.setPreferredSize(new Dimension(73, 23));
        this.keluarButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportMasterKaryawanPanel.this.keluarButtonActionPerformed(evt);
            }
        });
        this.cariButton.setText("Cari");
        this.cariButton.setPreferredSize(new Dimension(73, 23));
        this.cariButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportMasterKaryawanPanel.this.cariButtonActionPerformed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap().addComponent(this.jScrollPane1, -1, 780, Short.MAX_VALUE)).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(this.searchField, -2, 156, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.cariButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.keluarButton, -2, -1, -2)).addGroup(layout.createSequentialGroup().addGap(288, 288, 288).addComponent(this.jLabel1).addGap(0, 0, Short.MAX_VALUE))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel1).addGap(18, 18, 18).addComponent(this.jScrollPane1, -1, 492, Short.MAX_VALUE).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.keluarButton, -2, -1, -2).addComponent(this.cariButton, -2, -1, -2).addComponent(this.searchField, -2, -1, -2)).addGap(23, 23, 23)));
    }

    private void keluarButtonActionPerformed(ActionEvent evt) {
        this.listener.exitClick();
    }

    private void cariButtonActionPerformed(ActionEvent evt) {
        this.loadKaryawan(this.searchField.getText());
    }
}

