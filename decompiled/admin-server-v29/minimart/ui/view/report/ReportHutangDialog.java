/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view.report;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DocumentFilter;
import minimart.engine.component.UppercaseDocumentFilter;
import minimart.engine.model.Supplier;
import minimart.engine.service.SupplierService;
import minimart.ui.listener.ReportHutangDialogListener;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;

public class ReportHutangDialog
extends JDialog {
    private JTextField idSupplierField;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JButton keluarButton;
    private JComboBox keteranganCmb;
    private JLabel supplierNameField;
    private JButton tampilkanButton;
    private ReportHutangDialogListener reportHutangDialogListener;
    private int screenWidth;
    private int screenHeight;
    private DocumentFilter filter = new UppercaseDocumentFilter();
    private SupplierService supplierService = new SupplierService();

    public ReportHutangDialog(Frame parent, boolean modal) {
        super(parent, modal);
        this.initComponents();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.setTitle("Report Hutang");
    }

    public void addListener(ReportHutangDialogListener reportHutangDialogListener) {
        this.reportHutangDialogListener = reportHutangDialogListener;
    }

    private void loadKodeSupplier() {
        Supplier supplier = this.supplierService.getSupplier(this.idSupplierField.getText());
        if (supplier == null) {
            JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan");
        } else {
            this.supplierNameField.setText(supplier.getNama());
            this.tampilkanButton.requestFocus();
        }
    }

    private void initComponents() {
        this.tampilkanButton = new JButton();
        this.keluarButton = new JButton();
        this.supplierNameField = new JLabel();
        this.idSupplierField = new JTextField();
        this.jLabel4 = new JLabel();
        this.jLabel3 = new JLabel();
        this.keteranganCmb = new JComboBox();
        this.jLabel5 = new JLabel();
        this.setDefaultCloseOperation(2);
        this.tampilkanButton.setText("Tampilkan");
        this.tampilkanButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportHutangDialog.this.tampilkanButtonActionPerformed(evt);
            }
        });
        this.tampilkanButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportHutangDialog.this.tampilkanButtonKeyPressed(evt);
            }
        });
        this.keluarButton.setText("Keluar");
        this.keluarButton.setPreferredSize(new Dimension(79, 23));
        this.keluarButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportHutangDialog.this.keluarButtonActionPerformed(evt);
            }
        });
        this.keluarButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportHutangDialog.this.keluarButtonKeyPressed(evt);
            }
        });
        this.supplierNameField.setText("-");
        this.idSupplierField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportHutangDialog.this.idSupplierFieldKeyPressed(evt);
            }
        });
        this.jLabel4.setText("Supplier");
        this.jLabel3.setText("Kosong=Tampilkan Semua");
        this.keteranganCmb.setModel(new DefaultComboBoxModel<String>(new String[]{"SEMUA", "SUDAH JATUH TEMPO"}));
        this.jLabel5.setText("Keterangan");
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(31, 31, 31).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel4).addComponent(this.jLabel5)).addGap(30, 30, 30).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.supplierNameField, -2, 222, -2).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.keteranganCmb, GroupLayout.Alignment.LEADING, 0, -1, Short.MAX_VALUE).addComponent(this.idSupplierField, GroupLayout.Alignment.LEADING, -1, 146, Short.MAX_VALUE)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel3)))).addGroup(layout.createSequentialGroup().addComponent(this.tampilkanButton).addGap(18, 18, 18).addComponent(this.keluarButton, -2, -1, -2).addGap(129, 129, 129))).addContainerGap(28, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.idSupplierField, -2, -1, -2).addComponent(this.jLabel4).addComponent(this.jLabel3)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.supplierNameField, -2, 14, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.keteranganCmb, -2, -1, -2).addComponent(this.jLabel5)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.keluarButton, -2, -1, -2).addComponent(this.tampilkanButton)).addContainerGap(-1, Short.MAX_VALUE)));
        this.pack();
    }

    private void escapeClick() {
        this.idSupplierField.requestFocus();
        this.idSupplierField.selectAll();
    }

    private void idSupplierFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 113) {
            SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Supplier");
            sd.addSearchDialogListener(new searchSupplierDialogEventHandler());
            int w = sd.getWidth();
            int h = sd.getHeight();
            sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            sd.setVisible(true);
        } else if (evt.getKeyCode() == 10) {
            if (this.idSupplierField.getText().equals("")) {
                this.tampilkanButton.requestFocus();
            } else {
                this.loadKodeSupplier();
            }
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void keluarButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 37) {
            this.keluarButton.requestFocus();
        } else if (evt.getKeyCode() == 10) {
            this.dispose();
        }
    }

    private void keluarButtonActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void tampilkanButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 38) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 39) {
            this.keluarButton.requestFocus();
        } else if (evt.getKeyCode() == 10) {
            this.reportHutangDialogListener.tampilkanClick(this.idSupplierField.getText(), this.keteranganCmb.getSelectedItem().toString());
            this.dispose();
        }
    }

    private void tampilkanButtonActionPerformed(ActionEvent evt) {
        this.reportHutangDialogListener.tampilkanClick(this.idSupplierField.getText(), this.keteranganCmb.getSelectedItem().toString());
        this.dispose();
    }

    private class searchPanelSupplierEventHandler
    implements SearchPanelListener {
        private searchPanelSupplierEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            ReportHutangDialog.this.idSupplierField.setText(chooseValue);
            ReportHutangDialog.this.loadKodeSupplier();
        }
    }

    class searchSupplierDialogEventHandler
    implements SearchDialogListener {
        searchSupplierDialogEventHandler() {
        }

        @Override
        public void searchClick(String searchWord) {
            Object[] colName = new String[]{"KODE SUPPLIER", "NAMA SUPPLIER", "ALAMAT", "KOTA"};
            DefaultTableModel dtm = new DefaultTableModel(new String[0][1], colName);
            List<Supplier> suppliers = ReportHutangDialog.this.supplierService.getSuppliers(searchWord);
            for (Supplier supplier : suppliers) {
                Object[] temp = new String[]{supplier.getId(), supplier.getNama(), supplier.getAlamat(), supplier.getKota()};
                dtm.addRow(temp);
            }
            SearchPanel sp = new SearchPanel(null, true, dtm, "Data Supplier");
            sp.addSearchPanelListener(new searchPanelSupplierEventHandler());
            sp.setVisible(true);
        }

        public void dispose() {
        }
    }
}

