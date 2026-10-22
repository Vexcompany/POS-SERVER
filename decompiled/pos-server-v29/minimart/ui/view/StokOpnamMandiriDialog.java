/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import minimart.engine.controller.Helper;
import minimart.engine.model.Barang;
import minimart.engine.service.BarangService;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.listener.SoParsialDialogListener;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;

public class StokOpnamMandiriDialog
extends JDialog {
    private JButton cancelBtn;
    private JTextField idField;
    private JLabel idSoDetailLbl;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jenisField;
    private JLabel namaField;
    private JLabel satuanLbl;
    private JButton saveBtn;
    private JTextField stokFisikField;
    private SearchDialog sd;
    private int screenHeight;
    private int screenWidth;
    private BarangService barangService = new BarangService();
    private DefaultTableModel soTableModel;
    private SoParsialDialogListener soParsialDialogListener;
    private Barang barang;

    public StokOpnamMandiriDialog(Frame parent, boolean modal, DefaultTableModel stokOpnamTM) {
        super(parent, modal);
        this.initComponents();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.soTableModel = stokOpnamTM;
        this.stokFisikField.requestFocus();
        this.stokFisikField.selectAll();
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.jLabel5 = new JLabel();
        this.stokFisikField = new JTextField();
        this.satuanLbl = new JLabel();
        this.idField = new JTextField();
        this.namaField = new JLabel();
        this.cancelBtn = new JButton();
        this.saveBtn = new JButton();
        this.jLabel6 = new JLabel();
        this.jenisField = new JLabel();
        this.jLabel7 = new JLabel();
        this.idSoDetailLbl = new JLabel();
        this.setDefaultCloseOperation(2);
        this.jLabel1.setText("ID Barang");
        this.jLabel2.setText("Nama Barang");
        this.jLabel5.setText("Stok Fisik");
        this.stokFisikField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                StokOpnamMandiriDialog.this.stokFisikFieldKeyPressed(evt);
            }
        });
        this.satuanLbl.setText("Satuan");
        this.idField.setEditable(false);
        this.idField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                StokOpnamMandiriDialog.this.idFieldKeyPressed(evt);
            }
        });
        this.namaField.setText("-");
        this.cancelBtn.setText("Cancel");
        this.cancelBtn.setPreferredSize(new Dimension(73, 23));
        this.cancelBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                StokOpnamMandiriDialog.this.cancelBtnActionPerformed(evt);
            }
        });
        this.cancelBtn.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                StokOpnamMandiriDialog.this.cancelBtnKeyPressed(evt);
            }
        });
        this.saveBtn.setText("Save");
        this.saveBtn.setPreferredSize(new Dimension(73, 23));
        this.saveBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                StokOpnamMandiriDialog.this.saveBtnActionPerformed(evt);
            }
        });
        this.saveBtn.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                StokOpnamMandiriDialog.this.saveBtnKeyPressed(evt);
            }
        });
        this.jLabel6.setText("Jenis Barang");
        this.jenisField.setText("-");
        this.jLabel7.setText("ID SO Detail");
        this.idSoDetailLbl.setText("-");
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addComponent(this.jLabel2)).addGap(24, 24, 24).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.namaField, -2, 182, -2).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.idSoDetailLbl, GroupLayout.Alignment.LEADING, -1, -1, Short.MAX_VALUE).addComponent(this.idField, GroupLayout.Alignment.LEADING, -1, 138, Short.MAX_VALUE)).addComponent(this.jenisField, -2, 138, -2))).addComponent(this.jLabel6).addComponent(this.jLabel7)).addGap(0, 0, Short.MAX_VALUE)).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addComponent(this.jLabel5).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(18, 18, Short.MAX_VALUE).addComponent(this.saveBtn, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.cancelBtn, -2, -1, -2).addGap(12, 12, 12)).addGroup(layout.createSequentialGroup().addGap(42, 42, 42).addComponent(this.stokFisikField).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.satuanLbl))))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap(-1, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel7).addComponent(this.idSoDetailLbl)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.idField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.namaField)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel6).addComponent(this.jenisField)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel5).addComponent(this.stokFisikField, -2, -1, -2).addComponent(this.satuanLbl)).addGap(43, 43, 43).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.cancelBtn, -2, -1, -2).addComponent(this.saveBtn, -2, -1, -2)).addContainerGap()));
        this.pack();
    }

    private void idFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == 9) {
            this.stokFisikField.requestFocus();
            this.stokFisikField.selectAll();
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void cancelBtnActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void cancelBtnKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.dispose();
        } else if (evt.getKeyCode() == 37) {
            this.saveBtn.requestFocus();
        }
    }

    private void saveBtnKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.saveClick();
        } else if (evt.getKeyCode() == 39) {
            this.cancelBtn.requestFocus();
        } else if (evt.getKeyCode() == 38) {
            this.stokFisikField.requestFocus();
            this.stokFisikField.selectAll();
        }
    }

    private void saveBtnActionPerformed(ActionEvent evt) {
        this.saveClick();
    }

    private void stokFisikFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (!Helper.getInstance().isInteger(this.stokFisikField.getText())) {
                this.stokFisikField.setBackground(Color.red);
            } else {
                this.stokFisikField.setBackground(Color.WHITE);
                this.saveBtn.requestFocus();
            }
        } else if (evt.getKeyCode() == 27 || evt.getKeyCode() == 38) {
            this.idField.requestFocus();
            this.idField.selectAll();
        }
    }

    protected void escapeClick() {
        this.idField.requestFocus();
        this.idField.selectAll();
    }

    private void saveClick() {
        if (this.barang == null || this.idSoDetailLbl.getText().equals("-")
                || !Helper.getInstance().isInteger(this.stokFisikField.getText())) {
            JOptionPane.showMessageDialog(this, "Pilih barang yang ada di daftar SO dan masukkan stok fisik berupa angka bulat.");
            return;
        }

        // Guard against a stale detail ID being reused after selecting a different product.
        boolean matchingRow = false;
        for (int row = 0; row < this.soTableModel.getRowCount(); ++row) {
            if (this.idSoDetailLbl.getText().equals(this.soTableModel.getValueAt(row, 0).toString())
                    && this.idField.getText().equals(this.soTableModel.getValueAt(row, 1).toString())) {
                matchingRow = true;
                break;
            }
        }
        if (!matchingRow) {
            JOptionPane.showMessageDialog(this, "Barang tidak ditemukan pada daftar SO. Data belum disimpan.");
            return;
        }

        Object[] rowValue = new Object[]{this.idSoDetailLbl.getText(), this.idField.getText(), this.namaField.getText(), "TOKO", this.jenisField.getText(), this.barang.getRakIdToko(), this.barang.getSatuan2() + ":" + this.barang.getKonversi21() + "/" + this.barang.getSatuan3() + ":" + this.barang.getKonversi32(), this.stokFisikField.getText(), "", this.satuanLbl.getText()};
        this.soParsialDialogListener.saveClick(rowValue);
        this.escapeClick();
    }

    protected void loadDataFromTable(int row) {
        this.idSoDetailLbl.setText(this.soTableModel.getValueAt(row, 0).toString());
        this.stokFisikField.setText(this.soTableModel.getValueAt(row, 7).toString());
    }

    private void searchTable(String idBarang) {
        // Clear the previous selection first so a miss cannot reuse another row's detail ID.
        this.idSoDetailLbl.setText("-");
        for (int a = 0; a < this.soTableModel.getRowCount(); ++a) {
            // Column 0 is the SOM detail ID; column 1 is the product ID.
            String barangId = this.soTableModel.getValueAt(a, 1).toString();
            if (!idBarang.equals(barangId)) continue;
            this.loadDataFromTable(a);
            break;
        }
    }

    public void addListener(SoParsialDialogListener soParsialDialogListener) {
        this.soParsialDialogListener = soParsialDialogListener;
    }

    protected void loadKodeBarang(String chooseValue) {
        this.setDefault();
        this.barang = this.barangService.getBarang(chooseValue);
        if (this.barang == null) {
            JOptionPane.showMessageDialog(this, "Data barang tidak ditemukan. Silakan pilih barang yang valid.");
            return;
        }
        this.idField.setText(this.barang.getId());
        this.namaField.setText(this.barang.getNama());
        this.jenisField.setText(this.barang.getJenis());
        this.satuanLbl.setText(this.barang.getSatuan1());
        this.stokFisikField.requestFocus();
        this.stokFisikField.selectAll();
    }

    private void setDefault() {
        this.barang = null;
        this.idField.setText("");
        this.namaField.setText("-");
        this.stokFisikField.setText("0");
        this.satuanLbl.setText("-");
    }

    private class searchPanelBarangEventHandler
    implements SearchPanelListener {
        private searchPanelBarangEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            StokOpnamMandiriDialog.this.idField.setText(chooseValue);
            StokOpnamMandiriDialog.this.loadKodeBarang(chooseValue);
            StokOpnamMandiriDialog.this.searchTable(chooseValue);
            StokOpnamMandiriDialog.this.stokFisikField.requestFocus();
            StokOpnamMandiriDialog.this.stokFisikField.selectAll();
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
            List<Barang> barangs = StokOpnamMandiriDialog.this.barangService.getBarangs("", searchWord, false, "", true);
            for (Barang barang : barangs) {
                Object[] temp = new String[]{barang.getId(), barang.getNama(), barang.getSatuan1(), barang.getJenis()};
                dtm.addRow(temp);
            }
            SearchPanel sp = new SearchPanel(null, true, dtm, "Data Barang");
            sp.addSearchPanelListener(new searchPanelBarangEventHandler());
            sp.setVisible(true);
        }

        public void dispose() {
        }
    }
}

