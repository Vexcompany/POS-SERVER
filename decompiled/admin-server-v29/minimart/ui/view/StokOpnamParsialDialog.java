/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view;

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
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import minimart.engine.model.Barang;
import minimart.engine.service.BarangService;
import minimart.engine.service.PaketService;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.listener.SoParsialDialogListener;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;

public class StokOpnamParsialDialog
extends JDialog {
    private JButton cancelBtn;
    private JTextField idField;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel9;
    private JLabel jenisField;
    private JLabel lokasiField;
    private JLabel namaField;
    private JLabel satuanLbl;
    private JButton saveBtn;
    private JLabel selisihField;
    private JTextField stokFisikField;
    private JTextField stokKompField;
    private JTextField stokRepackField;
    private SearchDialog sd;
    private int screenHeight;
    private int screenWidth;
    private BarangService barangService = new BarangService();
    private DefaultTableModel soTableModel;
    private SoParsialDialogListener soParsialDialogListener;
    private Barang barang;
    private String lokasi;
    private PaketService paketService = new PaketService();
    private int stokPaket;

    public StokOpnamParsialDialog(Frame parent, boolean modal, DefaultTableModel stokOpnamTM, String lokasi) {
        super(parent, modal);
        this.initComponents();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.lokasi = lokasi;
        this.soTableModel = stokOpnamTM;
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.jLabel3 = new JLabel();
        this.jLabel4 = new JLabel();
        this.jLabel5 = new JLabel();
        this.stokKompField = new JTextField();
        this.stokFisikField = new JTextField();
        this.satuanLbl = new JLabel();
        this.idField = new JTextField();
        this.namaField = new JLabel();
        this.lokasiField = new JLabel();
        this.jLabel9 = new JLabel();
        this.selisihField = new JLabel();
        this.cancelBtn = new JButton();
        this.saveBtn = new JButton();
        this.jLabel6 = new JLabel();
        this.jenisField = new JLabel();
        this.jLabel7 = new JLabel();
        this.stokRepackField = new JTextField();
        this.setDefaultCloseOperation(2);
        this.jLabel1.setText("ID Barang");
        this.jLabel2.setText("Nama Barang");
        this.jLabel3.setText("Lokasi");
        this.jLabel4.setText("Stok Komputer");
        this.jLabel5.setText("Stok Fisik");
        this.stokKompField.setEditable(false);
        this.stokFisikField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                StokOpnamParsialDialog.this.stokFisikFieldKeyPressed(evt);
            }
        });
        this.satuanLbl.setText("Satuan");
        this.idField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                StokOpnamParsialDialog.this.idFieldKeyPressed(evt);
            }
        });
        this.namaField.setText("-");
        this.lokasiField.setText("-");
        this.jLabel9.setText("Selisih");
        this.selisihField.setText("-");
        this.cancelBtn.setText("Cancel");
        this.cancelBtn.setPreferredSize(new Dimension(73, 23));
        this.cancelBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                StokOpnamParsialDialog.this.cancelBtnActionPerformed(evt);
            }
        });
        this.cancelBtn.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                StokOpnamParsialDialog.this.cancelBtnKeyPressed(evt);
            }
        });
        this.saveBtn.setText("Save");
        this.saveBtn.setPreferredSize(new Dimension(73, 23));
        this.saveBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                StokOpnamParsialDialog.this.saveBtnActionPerformed(evt);
            }
        });
        this.saveBtn.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                StokOpnamParsialDialog.this.saveBtnKeyPressed(evt);
            }
        });
        this.jLabel6.setText("Jenis Barang");
        this.jenisField.setText("-");
        this.jLabel7.setText("Stok Repack");
        this.stokRepackField.setEditable(false);
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel4).addComponent(this.jLabel5).addComponent(this.jLabel1).addComponent(this.jLabel2).addComponent(this.jLabel3).addComponent(this.jLabel9)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.selisihField, -2, 138, -2).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createSequentialGroup().addComponent(this.saveBtn, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.cancelBtn, -2, -1, -2).addGap(12, 12, 12)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.namaField, -1, -1, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.stokRepackField, GroupLayout.Alignment.LEADING).addComponent(this.stokKompField, GroupLayout.Alignment.LEADING).addComponent(this.stokFisikField, GroupLayout.Alignment.LEADING).addComponent(this.idField, GroupLayout.Alignment.LEADING, -1, 138, Short.MAX_VALUE)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.satuanLbl)))).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.jenisField, GroupLayout.Alignment.LEADING, -1, 138, Short.MAX_VALUE).addComponent(this.lokasiField, GroupLayout.Alignment.LEADING, -1, -1, Short.MAX_VALUE)))).addComponent(this.jLabel6).addComponent(this.jLabel7)).addContainerGap(-1, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.idField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.namaField)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel6).addComponent(this.jenisField)).addGap(10, 10, 10).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3).addComponent(this.lokasiField)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel4).addComponent(this.stokKompField, -2, -1, -2).addComponent(this.satuanLbl)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel7).addComponent(this.stokRepackField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel5).addComponent(this.stokFisikField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel9).addComponent(this.selisihField)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.cancelBtn, -2, -1, -2).addComponent(this.saveBtn, -2, -1, -2)).addGap(21, 21, 21)));
        this.pack();
    }

    private void idFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 113) {
            this.sd = new SearchDialog(null, true, "Pencarian Data Barang");
            this.sd.addSearchDialogListener(new searchBarangDialogEventHandler());
            int w = this.sd.getWidth();
            int h = this.sd.getHeight();
            this.sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            this.sd.setVisible(true);
        } else if (evt.getKeyCode() == 10 || evt.getKeyCode() == 9) {
            if (this.idField.getText().equals("")) {
                this.sd = new SearchDialog(null, true, "Pencarian Data Barang");
                this.sd.addSearchDialogListener(new searchBarangDialogEventHandler());
                int w = this.sd.getWidth();
                int h = this.sd.getHeight();
                this.sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                this.sd.setVisible(true);
            } else {
                this.loadKodeBarang(this.idField.getText());
            }
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
            float selisih = 0.0f;
            if (this.lokasi.equals("Toko")) {
                selisih = Float.parseFloat(this.stokFisikField.getText()) - ((float)this.stokPaket + this.barang.getStokToko());
            } else if (this.lokasi.equals("Gudang")) {
                selisih = Float.parseFloat(this.stokFisikField.getText()) - this.barang.getStokGudang();
            }
            this.selisihField.setText(selisih + "");
            this.saveBtn.requestFocus();
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
        Object[] rowValue = new Object[]{this.idField.getText(), this.namaField.getText(), this.lokasiField.getText(), this.jenisField.getText(), this.barang.getRakIdToko(), this.barang.getSatuan2() + ":" + this.barang.getKonversi21() + "/" + this.barang.getSatuan3() + ":" + this.barang.getKonversi32(), this.stokKompField.getText(), this.stokRepackField.getText(), this.stokFisikField.getText(), this.selisihField.getText(), this.satuanLbl.getText(), Float.valueOf(Float.parseFloat(this.selisihField.getText()) * (float)this.barang.getHpp())};
        this.soParsialDialogListener.saveClick(rowValue);
        this.escapeClick();
    }

    protected void loadDataFromTable(int row) {
        this.stokKompField.setText(this.soTableModel.getValueAt(row, 6).toString());
        this.stokRepackField.setText(this.soTableModel.getValueAt(row, 7).toString());
        this.stokFisikField.setText(this.soTableModel.getValueAt(row, 8).toString());
        this.selisihField.setText(this.soTableModel.getValueAt(row, 9).toString());
    }

    private void searchTable(String idBarang) {
        for (int a = 0; a < this.soTableModel.getRowCount(); ++a) {
            String barangId = this.soTableModel.getValueAt(a, 0).toString();
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
        this.idField.setText(this.barang.getId());
        this.namaField.setText(this.barang.getNama());
        this.jenisField.setText(this.barang.getJenis());
        this.lokasiField.setText(this.lokasi);
        if (this.lokasi.equals("Toko")) {
            this.stokPaket = this.paketService.getStokBarangByDetailPaket(chooseValue);
            this.stokRepackField.setText(this.stokPaket + "");
            this.stokKompField.setText(this.barang.getStokToko() + "");
            this.stokFisikField.setText((float)this.stokPaket + this.barang.getStokToko() + "");
        } else if (this.lokasi.equals("Gudang")) {
            this.stokKompField.setText(this.barang.getStokGudang() + "");
            this.stokFisikField.setText(this.barang.getStokGudang() + "");
        }
        this.selisihField.setText("0");
        this.satuanLbl.setText(this.barang.getSatuan1());
        this.stokFisikField.requestFocus();
        this.stokFisikField.selectAll();
    }

    private void setDefault() {
        this.idField.setText("");
        this.namaField.setText("-");
        this.lokasiField.setText("-");
        this.stokKompField.setText("0");
        this.stokFisikField.setText("0");
        this.selisihField.setText("0");
        this.satuanLbl.setText("-");
    }

    private class searchPanelBarangEventHandler
    implements SearchPanelListener {
        private searchPanelBarangEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            StokOpnamParsialDialog.this.idField.setText(chooseValue);
            StokOpnamParsialDialog.this.loadKodeBarang(chooseValue);
            StokOpnamParsialDialog.this.searchTable(chooseValue);
            StokOpnamParsialDialog.this.stokFisikField.requestFocus();
            StokOpnamParsialDialog.this.stokFisikField.selectAll();
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
            List<Barang> barangs = StokOpnamParsialDialog.this.barangService.getBarangs("", searchWord, false, "", true);
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

