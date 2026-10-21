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
import minimart.engine.model.Supplier;
import minimart.engine.service.SupplierService;
import minimart.ui.listener.ReportPelunasanHutangDialogListener;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;

public class ReportPelunasantHutangDialog
extends JDialog {
    private JLabel alamatSuppField;
    private JDateChooser endDateField;
    private JTextField idSuppField;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JButton keluarButton;
    private JLabel namaSupField;
    private JDateChooser startDateField;
    private JButton tampilkanButton;
    private ReportPelunasanHutangDialogListener reportPiutangDialogListener;
    private int screenHeight;
    private int screenWidth;
    private SupplierService supplierService = new SupplierService();
    private Supplier supplier;
    private String tipe;

    public ReportPelunasantHutangDialog(Frame parent, boolean modal) {
        super(parent, modal);
        this.initComponents();
        this.tipe = this.tipe;
        this.startDateField.setDate(new Date());
        this.endDateField.setDate(new Date());
        this.startDateField.setFocusCycleRoot(false);
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.setTitle("Report Pelunasan Hutang");
    }

    public void addListener(ReportPelunasanHutangDialogListener reportPelunasanHutangDialogListener) {
        this.reportPiutangDialogListener = reportPelunasanHutangDialogListener;
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.startDateField = new JDateChooser();
        this.jLabel2 = new JLabel();
        this.endDateField = new JDateChooser();
        this.tampilkanButton = new JButton();
        this.keluarButton = new JButton();
        this.jLabel4 = new JLabel();
        this.idSuppField = new JTextField();
        this.namaSupField = new JLabel();
        this.alamatSuppField = new JLabel();
        this.jLabel3 = new JLabel();
        this.setDefaultCloseOperation(2);
        this.jLabel1.setText("Dari Tanggal");
        this.jLabel2.setText("Sampai Tanggal");
        this.tampilkanButton.setText("Tampilkan");
        this.tampilkanButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPelunasantHutangDialog.this.tampilkanButtonActionPerformed(evt);
            }
        });
        this.tampilkanButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportPelunasantHutangDialog.this.tampilkanButtonKeyPressed(evt);
            }
        });
        this.keluarButton.setText("Keluar");
        this.keluarButton.setPreferredSize(new Dimension(79, 23));
        this.keluarButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPelunasantHutangDialog.this.keluarButtonActionPerformed(evt);
            }
        });
        this.keluarButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportPelunasantHutangDialog.this.keluarButtonKeyPressed(evt);
            }
        });
        this.jLabel4.setText("ID Supplier");
        this.idSuppField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportPelunasantHutangDialog.this.idSuppFieldKeyPressed(evt);
            }
        });
        this.namaSupField.setText("-");
        this.alamatSuppField.setText("-");
        this.jLabel3.setText("Kosong = tampilkan semua");
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(31, 31, 31).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(53, 53, 53).addComponent(this.tampilkanButton).addGap(18, 18, 18).addComponent(this.keluarButton, -2, -1, -2)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel2, GroupLayout.Alignment.TRAILING).addComponent(this.jLabel1)).addComponent(this.jLabel4)).addGap(11, 11, 11).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent((Component)this.endDateField, -1, 166, Short.MAX_VALUE).addComponent((Component)this.startDateField, -1, -1, Short.MAX_VALUE).addComponent(this.idSuppField).addGroup(layout.createSequentialGroup().addGap(10, 10, 10).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.alamatSuppField, -1, 156, Short.MAX_VALUE).addComponent(this.namaSupField, -1, -1, Short.MAX_VALUE)))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel3))).addContainerGap(18, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel4).addComponent(this.idSuppField, -2, -1, -2).addComponent(this.jLabel3)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.namaSupField).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.alamatSuppField).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent((Component)this.startDateField, -2, -1, -2).addComponent(this.jLabel1)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent((Component)this.endDateField, -2, -1, -2).addComponent(this.jLabel2)).addGap(11, 11, 11).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.keluarButton, -2, -1, -2).addComponent(this.tampilkanButton)).addContainerGap()));
        this.pack();
    }

    private void escapeClick() {
        this.dispose();
    }

    private void loadKodeSupplier() {
        this.supplier = this.supplierService.getSupplier(this.idSuppField.getText());
        if (this.supplier == null) {
            JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan");
        } else {
            this.namaSupField.setText(this.supplier.getNama());
            this.alamatSuppField.setText(this.supplier.getAlamat());
        }
        this.tampilkanButton.requestFocus();
    }

    private void tampilkanButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27 || evt.getKeyCode() == 38) {
            this.idSuppField.requestFocus();
            this.idSuppField.selectAll();
        } else if (evt.getKeyCode() == 39) {
            this.keluarButton.requestFocus();
        } else if (evt.getKeyCode() == 10) {
            this.reportPiutangDialogListener.tampilkanClick(this.startDateField.getDate(), this.endDateField.getDate(), this.idSuppField.getText());
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

    private void tampilkanButtonActionPerformed(ActionEvent evt) {
        this.reportPiutangDialogListener.tampilkanClick(this.startDateField.getDate(), this.endDateField.getDate(), this.idSuppField.getText());
        this.dispose();
    }

    private void keluarButtonActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void idSuppFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (this.idSuppField.getText().equals("")) {
                this.tampilkanButton.requestFocus();
            } else {
                this.loadKodeSupplier();
            }
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        } else if (evt.getKeyCode() == 113) {
            SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Customer");
            sd.addSearchDialogListener(new searchCustomerDialogEventHandler());
            int w = sd.getWidth();
            int h = sd.getHeight();
            sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            sd.setVisible(true);
        }
    }

    private class searchPanelCustomerEventHandler
    implements SearchPanelListener {
        private searchPanelCustomerEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            ReportPelunasantHutangDialog.this.idSuppField.setText(chooseValue);
            ReportPelunasantHutangDialog.this.loadKodeSupplier();
        }
    }

    private class searchCustomerDialogEventHandler
    implements SearchDialogListener {
        private searchCustomerDialogEventHandler() {
        }

        @Override
        public void searchClick(String searchWord) {
            Object[] colName = new String[]{"KODE SUPPLIER", "NAMA SUPPLIER", "ALAMAT", "KOTA"};
            DefaultTableModel dtm = new DefaultTableModel(new String[0][1], colName);
            List<Supplier> suppliers = ReportPelunasantHutangDialog.this.supplierService.getSuppliers(searchWord);
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
}

