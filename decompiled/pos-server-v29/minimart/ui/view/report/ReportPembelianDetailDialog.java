/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.toedter.calendar.JDateChooser
 */
package minimart.ui.view.report;

import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import minimart.engine.model.Barang;
import minimart.engine.model.Supplier;
import minimart.engine.service.BarangService;
import minimart.engine.service.SupplierService;
import minimart.ui.listener.ReportPembelianDetailDialogListener;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;

public class ReportPembelianDetailDialog
extends JDialog {
    private JDateChooser endDateField;
    private JTextField idBarangField;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JButton keluarButton;
    private JLabel namaBarangField;
    private JDateChooser startDateField;
    private JLabel supplierAlamatField;
    private JTextField supplierIdField;
    private JLabel supplierNameField;
    private JButton tampilkanButton;
    private ReportPembelianDetailDialogListener reportPembelianDetailDialogListener;
    private SearchDialog sd;
    private int screenHeight;
    private int screenWidth;
    private BarangService barangService = new BarangService();
    private Barang barang;
    private SupplierService supplierService = new SupplierService();

    public ReportPembelianDetailDialog(Frame parent, boolean modal) {
        super(parent, modal);
        this.initComponents();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.startDateField.setDate(new Date());
        this.endDateField.setDate(new Date());
        this.startDateField.setFocusCycleRoot(false);
        this.setTitle("Report Detail Penjualan");
    }

    public void addListener(ReportPembelianDetailDialogListener reportPembelianDetailDialogListener) {
        this.reportPembelianDetailDialogListener = reportPembelianDetailDialogListener;
    }

    private void loadKodeBarang(String idBarang) {
        this.barang = this.barangService.getBarang(idBarang);
        if (this.barang != null) {
            this.idBarangField.setText(this.barang.getId());
            this.namaBarangField.setText(this.barang.getNama());
            this.tampilkanButton.requestFocus();
        } else if (this.barang == null) {
            JOptionPane.showMessageDialog(null, "Barang Tidak Ditemukan");
        }
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.startDateField = new JDateChooser();
        this.jLabel2 = new JLabel();
        this.endDateField = new JDateChooser();
        this.tampilkanButton = new JButton();
        this.keluarButton = new JButton();
        this.jLabel4 = new JLabel();
        this.idBarangField = new JTextField();
        this.namaBarangField = new JLabel();
        this.jLabel5 = new JLabel();
        this.jLabel3 = new JLabel();
        this.supplierIdField = new JTextField();
        this.supplierNameField = new JLabel();
        this.supplierAlamatField = new JLabel();
        this.jLabel6 = new JLabel();
        this.setDefaultCloseOperation(2);
        this.jLabel1.setText("Dari Tanggal");
        this.jLabel2.setText("Sampai Tanggal");
        this.tampilkanButton.setText("Tampilkan");
        this.tampilkanButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPembelianDetailDialog.this.tampilkanButtonActionPerformed(evt);
            }
        });
        this.tampilkanButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportPembelianDetailDialog.this.tampilkanButtonKeyPressed(evt);
            }
        });
        this.keluarButton.setText("Keluar");
        this.keluarButton.setPreferredSize(new Dimension(79, 23));
        this.keluarButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPembelianDetailDialog.this.keluarButtonActionPerformed(evt);
            }
        });
        this.keluarButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportPembelianDetailDialog.this.keluarButtonKeyPressed(evt);
            }
        });
        this.jLabel4.setText("Kode Barang");
        this.idBarangField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportPembelianDetailDialog.this.idBarangFieldKeyPressed(evt);
            }
        });
        this.namaBarangField.setText("-");
        this.jLabel5.setText("Supplier");
        this.jLabel3.setText("Kosong=Tampilkan Semua");
        this.supplierIdField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportPembelianDetailDialog.this.supplierIdFieldKeyPressed(evt);
            }
        });
        this.supplierNameField.setText("-");
        this.supplierAlamatField.setText("-");
        this.jLabel6.setText("Kosong=Tampilkan Semua");
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(31, 31, 31).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel5).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.supplierIdField, -1, 143, Short.MAX_VALUE).addComponent(this.supplierNameField, -1, -1, Short.MAX_VALUE)).addGap(18, 18, 18).addComponent(this.jLabel3)).addGroup(layout.createSequentialGroup().addGap(53, 53, 53).addComponent(this.tampilkanButton).addGap(18, 18, 18).addComponent(this.keluarButton, -2, -1, -2).addGap(0, 0, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel2, GroupLayout.Alignment.TRAILING).addComponent(this.jLabel1)).addComponent(this.jLabel4)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.idBarangField).addComponent((Component)this.endDateField, -1, 146, Short.MAX_VALUE).addComponent((Component)this.startDateField, -1, 146, Short.MAX_VALUE).addComponent(this.namaBarangField, -1, -1, Short.MAX_VALUE).addComponent(this.supplierAlamatField, -1, -1, Short.MAX_VALUE)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE).addComponent(this.jLabel6))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap(-1, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.supplierIdField, -2, -1, -2).addComponent(this.jLabel5).addComponent(this.jLabel3)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.supplierNameField).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.supplierAlamatField).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel4).addComponent(this.idBarangField, -2, -1, -2).addComponent(this.jLabel6)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.namaBarangField).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addGroup(layout.createSequentialGroup().addComponent(this.jLabel1, -1, -1, Short.MAX_VALUE).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jLabel2)).addGroup(layout.createSequentialGroup().addComponent((Component)this.startDateField, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent((Component)this.endDateField, -2, -1, -2))).addGap(11, 11, 11).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.keluarButton, -2, -1, -2).addComponent(this.tampilkanButton)).addContainerGap()));
        this.pack();
    }

    private void escapeClick() {
        this.dispose();
    }

    private void tampilkanButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 39) {
            this.keluarButton.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.supplierIdField.requestFocus();
        } else if (evt.getKeyCode() == 38) {
            this.idBarangField.requestFocus();
        } else if (evt.getKeyCode() == 10) {
            this.reportPembelianDetailDialogListener.tampilkanClick(this.startDateField.getDate(), this.endDateField.getDate(), this.supplierIdField.getText(), this.idBarangField.getText());
            this.dispose();
        }
    }

    private void keluarButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.supplierIdField.requestFocus();
        } else if (evt.getKeyCode() == 37) {
            this.tampilkanButton.requestFocus();
        } else if (evt.getKeyCode() == 38) {
            this.idBarangField.requestFocus();
        } else if (evt.getKeyCode() == 10) {
            this.dispose();
        }
    }

    private void tampilkanButtonActionPerformed(ActionEvent evt) {
        this.reportPembelianDetailDialogListener.tampilkanClick(this.startDateField.getDate(), this.endDateField.getDate(), this.supplierIdField.getText(), this.idBarangField.getText());
        this.dispose();
    }

    private void keluarButtonActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void idBarangFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 113) {
            this.sd = new SearchDialog(null, true, "Pencarian Data Barang");
            this.sd.addSearchDialogListener(new searchBarangDialogEventHandler());
            int w = this.sd.getWidth();
            int h = this.sd.getHeight();
            this.sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            this.sd.setVisible(true);
        } else if (evt.getKeyCode() == 10 || evt.getKeyCode() == 9) {
            if (this.idBarangField.getText().equals("")) {
                this.tampilkanButton.requestFocus();
            } else {
                this.loadKodeBarang(this.idBarangField.getText());
            }
        } else if (evt.getKeyCode() == 27) {
            this.supplierIdField.requestFocus();
        } else if (evt.getKeyCode() == 38) {
            this.supplierIdField.requestFocus();
        }
    }

    private void loadKodeCustomer() {
        Supplier supplier = this.supplierService.getSupplier(this.supplierIdField.getText());
        if (supplier == null) {
            JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan");
        } else {
            this.supplierNameField.setText(supplier.getNama());
            this.supplierAlamatField.setText(supplier.getAlamat());
            this.idBarangField.requestFocus();
        }
    }

    private void supplierIdFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 113) {
            SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Supplier");
            sd.addSearchDialogListener(new searchSupplierDialogEventHandler());
            int w = sd.getWidth();
            int h = sd.getHeight();
            sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            sd.setVisible(true);
        } else if (evt.getKeyCode() == 10) {
            if (this.supplierIdField.getText().equals("")) {
                this.idBarangField.requestFocus();
            } else {
                this.loadKodeCustomer();
            }
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private class searchPanelCustomerEventHandler
    implements SearchPanelListener {
        private searchPanelCustomerEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            ReportPembelianDetailDialog.this.supplierIdField.setText(chooseValue);
            ReportPembelianDetailDialog.this.loadKodeCustomer();
        }
    }

    private class searchSupplierDialogEventHandler
    implements SearchDialogListener {
        private searchSupplierDialogEventHandler() {
        }

        @Override
        public void searchClick(String searchWord) {
            Object[] colName = new String[]{"KODE CUSTOMER", "NAMA SUPPLIER", "ALAMAT", "KOTA"};
            DefaultTableModel dtm = new DefaultTableModel(new String[0][1], colName);
            List<Supplier> suppliers = ReportPembelianDetailDialog.this.supplierService.getSuppliers(searchWord);
            for (Supplier supplier : suppliers) {
                Object[] temp = new String[]{supplier.getId(), supplier.getNama(), supplier.getAlamat(), supplier.getKota()};
                dtm.addRow(temp);
            }
            SearchPanel sp = new SearchPanel(null, true, dtm, "Data Supplier");
            sp.addSearchPanelListener(new searchPanelCustomerEventHandler());
            sp.setVisible(true);
        }

        public void dispose() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private class searchPanelBarangEventHandler
    implements SearchPanelListener {
        private searchPanelBarangEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            ReportPembelianDetailDialog.this.loadKodeBarang(chooseValue);
        }
    }

    private class searchBarangDialogEventHandler
    implements SearchDialogListener {
        private searchBarangDialogEventHandler() {
        }

        @Override
        public void searchClick(String searchWord) {
            Object[] colName = new String[]{"KODE BARANG", "NAMA BARANG", "SATUAN", "JENIS"};
            DefaultTableModel dtm = new DefaultTableModel(new String[0][1], colName);
            List<Barang> barangs = ReportPembelianDetailDialog.this.barangService.getBarangs("", searchWord, false, "", true);
            for (Barang barang : barangs) {
                Object[] temp = new String[]{barang.getId(), barang.getNama(), barang.getSatuan1(), barang.getJenis()};
                dtm.addRow(temp);
            }
            SearchPanel sp = new SearchPanel(null, true, dtm, "Data Barang");
            sp.addSearchPanelListener(new searchPanelBarangEventHandler());
            sp.setVisible(true);
        }
    }
}

