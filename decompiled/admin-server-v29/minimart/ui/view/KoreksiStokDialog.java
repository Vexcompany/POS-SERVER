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
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import minimart.engine.controller.Helper;
import minimart.engine.model.Barang;
import minimart.engine.model.KoreksiStok;
import minimart.engine.model.StockCommit;
import minimart.engine.service.BarangService;
import minimart.engine.service.KoreksiStokService;
import minimart.engine.service.SinkronisasiBarangService;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;

public class KoreksiStokDialog
extends JDialog {
    private JComboBox actionCmb;
    private JButton cancelButton;
    private JLabel jLabel1;
    private JLabel jLabel11;
    private JLabel jLabel12;
    private JLabel jLabel16;
    private JLabel jLabel17;
    private JLabel jLabel2;
    private JLabel jLabel20;
    private JLabel jLabel21;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JSeparator jSeparator1;
    private JTextField jTextField1;
    private JTextField kodeBarangField;
    private JTextField konversiField1;
    private JTextField konversiField2;
    private JLabel konversiLbl1;
    private JLabel konversiLbl2;
    private JLabel konversiLbl3;
    private JLabel konversiLbl4;
    private JTextField namaBarangField;
    private JTextField quantity1Field;
    private JTextField quantity2Field;
    private JTextField quantity3Field;
    private JLabel satuanQuantity1;
    private JLabel satuanQuantity2;
    private JLabel satuanQuantity3;
    private JButton saveButton;
    private JLabel stokLbl;
    private JTextField stokTokoField1;
    private JTextField stokTokoField2;
    private JTextField stokTokoField3;
    private JLabel stokTokoLbl1;
    private JLabel stokTokoLbl2;
    private JLabel stokTokoLbl3;
    private SearchDialog sd;
    private int screenHeight;
    private int screenWidth;
    private BarangService barangService = new BarangService();
    private KoreksiStokService koreksiStokService = new KoreksiStokService();
    private Barang barang = new Barang();
    private Helper helper = Helper.getInstance();
    private SinkronisasiBarangService sinkronisasiBarangService = new SinkronisasiBarangService();

    public KoreksiStokDialog(Frame parent, boolean modal) {
        super(parent, modal);
        this.initComponents();
        if (this.helper.getStatusProgram().equals("GUDANG")) {
            this.stokLbl.setText("Gudang");
        }
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.setDefault();
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.stokLbl = new JLabel();
        this.kodeBarangField = new JTextField();
        this.namaBarangField = new JTextField();
        this.stokTokoField1 = new JTextField();
        this.stokTokoLbl1 = new JLabel();
        this.stokTokoField2 = new JTextField();
        this.stokTokoField3 = new JTextField();
        this.stokTokoLbl2 = new JLabel();
        this.stokTokoLbl3 = new JLabel();
        this.jLabel11 = new JLabel();
        this.jLabel12 = new JLabel();
        this.quantity1Field = new JTextField();
        this.quantity2Field = new JTextField();
        this.quantity3Field = new JTextField();
        this.satuanQuantity1 = new JLabel();
        this.satuanQuantity2 = new JLabel();
        this.satuanQuantity3 = new JLabel();
        this.saveButton = new JButton();
        this.cancelButton = new JButton();
        this.actionCmb = new JComboBox();
        this.jSeparator1 = new JSeparator();
        this.jLabel17 = new JLabel();
        this.jLabel16 = new JLabel();
        this.konversiLbl1 = new JLabel();
        this.konversiLbl3 = new JLabel();
        this.jLabel20 = new JLabel();
        this.konversiLbl4 = new JLabel();
        this.konversiField1 = new JTextField();
        this.jLabel21 = new JLabel();
        this.konversiLbl2 = new JLabel();
        this.konversiField2 = new JTextField();
        this.jLabel3 = new JLabel();
        this.jLabel4 = new JLabel();
        this.jTextField1 = new JTextField();
        this.setDefaultCloseOperation(2);
        this.jLabel1.setText("Kode Barang");
        this.jLabel2.setText("Nama Barang");
        this.stokLbl.setText("Stok Toko");
        this.kodeBarangField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                KoreksiStokDialog.this.kodeBarangFieldKeyPressed(evt);
            }
        });
        this.namaBarangField.setEditable(false);
        this.stokTokoField1.setEditable(false);
        this.stokTokoField2.setEditable(false);
        this.stokTokoField3.setEditable(false);
        this.jLabel11.setText("STOK");
        this.jLabel12.setText("Quantity");
        this.quantity1Field.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                KoreksiStokDialog.this.quantity1FieldKeyPressed(evt);
            }
        });
        this.quantity2Field.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                KoreksiStokDialog.this.quantity2FieldKeyPressed(evt);
            }
        });
        this.quantity3Field.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                KoreksiStokDialog.this.quantity3FieldKeyPressed(evt);
            }
        });
        this.saveButton.setText("Save");
        this.saveButton.setPreferredSize(new Dimension(73, 23));
        this.saveButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                KoreksiStokDialog.this.saveButtonActionPerformed(evt);
            }
        });
        this.saveButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                KoreksiStokDialog.this.saveButtonKeyPressed(evt);
            }
        });
        this.cancelButton.setText("Cancel");
        this.cancelButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                KoreksiStokDialog.this.cancelButtonActionPerformed(evt);
            }
        });
        this.cancelButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                KoreksiStokDialog.this.cancelButtonKeyPressed(evt);
            }
        });
        this.actionCmb.setModel(new DefaultComboBoxModel<String>(new String[]{"PENAMBAHAN", "PENGURANGAN"}));
        this.actionCmb.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                KoreksiStokDialog.this.actionCmbKeyPressed(evt);
            }
        });
        this.jLabel17.setText("1");
        this.jLabel16.setText("1");
        this.konversiLbl1.setText(" ");
        this.konversiLbl3.setText(" ");
        this.jLabel20.setText("=");
        this.konversiLbl4.setText(" ");
        this.konversiField1.setEditable(false);
        this.jLabel21.setText("=");
        this.konversiLbl2.setText(" ");
        this.konversiField2.setEditable(false);
        this.jLabel3.setText("Konversi");
        this.jLabel4.setText("Keterangan:");
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel2).addComponent(this.stokLbl)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.stokTokoField1, -2, 59, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.stokTokoLbl1, -2, 34, -2)).addGroup(layout.createSequentialGroup().addComponent(this.stokTokoField3, -2, 59, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.stokTokoLbl3, -2, 34, -2)).addGroup(layout.createSequentialGroup().addComponent(this.stokTokoField2, -2, 59, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.stokTokoLbl2, -2, 34, -2)).addComponent(this.namaBarangField, -2, 206, -2))).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.jSeparator1, GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel1).addGap(21, 21, 21).addComponent(this.kodeBarangField, -2, 151, -2)).addGroup(layout.createSequentialGroup().addGap(149, 149, 149).addComponent(this.saveButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.cancelButton)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel12).addGap(58, 58, 58).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.quantity1Field).addComponent(this.quantity2Field).addComponent(this.quantity3Field, -2, 89, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.satuanQuantity2, -1, -1, Short.MAX_VALUE).addComponent(this.satuanQuantity1, -1, -1, Short.MAX_VALUE).addComponent(this.satuanQuantity3, -2, 50, -2))).addGroup(layout.createSequentialGroup().addComponent(this.actionCmb, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jLabel11))))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jLabel3).addComponent(this.jLabel4).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel16).addComponent(this.jLabel17)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.konversiLbl1, -1, -1, Short.MAX_VALUE).addComponent(this.konversiLbl3, -2, 38, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel20).addComponent(this.jLabel21, GroupLayout.Alignment.TRAILING)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.konversiField1).addComponent(this.konversiField2, -2, 51, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.konversiLbl2, -1, 43, Short.MAX_VALUE).addComponent(this.konversiLbl4, -1, -1, Short.MAX_VALUE))).addComponent(this.jTextField1)).addContainerGap(58, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.kodeBarangField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.namaBarangField, -2, -1, -2).addComponent(this.jLabel3)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel2).addGap(12, 12, 12))).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel16).addComponent(this.konversiLbl1).addComponent(this.jLabel20).addComponent(this.konversiField1, -2, -1, -2).addComponent(this.konversiLbl2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel17).addComponent(this.konversiLbl3).addComponent(this.jLabel21).addComponent(this.konversiField2, -2, -1, -2).addComponent(this.konversiLbl4))).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.stokTokoField1).addComponent(this.stokLbl)).addComponent(this.stokTokoLbl1, -1, -1, Short.MAX_VALUE)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.stokTokoField2).addComponent(this.stokTokoLbl2, -1, -1, Short.MAX_VALUE)))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.stokTokoField3).addComponent(this.stokTokoLbl3, -1, -1, Short.MAX_VALUE)).addGap(18, 18, 18).addComponent(this.jSeparator1, -2, 10, -2).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel11).addComponent(this.actionCmb, -2, -1, -2).addComponent(this.jLabel4)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel12).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.quantity1Field).addComponent(this.satuanQuantity1, -2, 20, -2)).addComponent(this.jTextField1, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.quantity2Field).addComponent(this.satuanQuantity2, -2, 20, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.quantity3Field, -2, -1, -2).addComponent(this.satuanQuantity3, -2, 20, -2)))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.saveButton, -2, -1, -2).addComponent(this.cancelButton)).addContainerGap()));
        this.pack();
    }

    private void setDefault() {
        this.kodeBarangField.setText("");
        this.namaBarangField.setText("");
        this.stokTokoField1.setText("0");
        this.stokTokoField2.setText("0");
        this.stokTokoField3.setText("0");
        this.stokTokoLbl1.setText("");
        this.stokTokoLbl2.setText("");
        this.stokTokoLbl3.setText("");
        this.quantity1Field.setText("0");
        this.quantity2Field.setText("0");
        this.quantity3Field.setText("0");
        this.satuanQuantity1.setText("");
        this.satuanQuantity2.setText("");
        this.satuanQuantity3.setText("");
        this.actionCmb.setSelectedIndex(0);
        this.barang = null;
        this.kodeBarangField.requestFocus();
    }

    private void saveClick() {
        StockCommit stockCommit2;
        float totalItem = 0.0f;
        totalItem = Float.parseFloat(this.quantity1Field.getText()) + Float.parseFloat(this.quantity2Field.getText()) * (float)this.barang.getKonversi21() + Float.parseFloat(this.quantity3Field.getText()) * (float)this.barang.getKonversi21() * (float)this.barang.getKonversi32();
        float stokAwalToko = this.barang.getStokToko();
        float stokAwalGudang = this.barang.getStokGudang();
        int updateBarangStatus = 0;
        if (this.actionCmb.getSelectedItem().equals("PENAMBAHAN")) {
            if (this.helper.getStatusProgram().equals("TOKO")) {
                stockCommit2 = new StockCommit();
                stockCommit2.setIdBarang(this.barang.getId());
                stockCommit2.setIdTransaksi("koreksi-stok" + this.barang.getId());
                stockCommit2.setQueryType("tambah");
                stockCommit2.setStockType("toko");
                stockCommit2.setTransactionType("Koreksi Stok");
                stockCommit2.setJumlahBarang(totalItem);
                this.sinkronisasiBarangService.insertCommit(stockCommit2);
                updateBarangStatus = this.barangService.updateStokBarangToko(totalItem, this.barang.getId());
            } else if (this.helper.getStatusProgram().equals("GUDANG")) {
                stockCommit2 = new StockCommit();
                stockCommit2.setIdBarang(this.barang.getId());
                stockCommit2.setIdTransaksi("koreksi-stok" + this.barang.getId());
                stockCommit2.setQueryType("tambah");
                stockCommit2.setStockType("gudang");
                stockCommit2.setTransactionType("Koreksi Stok");
                stockCommit2.setJumlahBarang(totalItem);
                this.sinkronisasiBarangService.insertCommit(stockCommit2);
                updateBarangStatus = this.barangService.updateStokBarangGudang(totalItem, this.barang.getId());
            }
        } else if (this.actionCmb.getSelectedItem().equals("PENGURANGAN")) {
            if (this.helper.getStatusProgram().equals("TOKO")) {
                stockCommit2 = new StockCommit();
                stockCommit2.setIdBarang(this.barang.getId());
                stockCommit2.setIdTransaksi("koreksi-stok" + this.barang.getId());
                stockCommit2.setQueryType("kurang");
                stockCommit2.setStockType("toko");
                stockCommit2.setTransactionType("Koreksi Stok");
                stockCommit2.setJumlahBarang(totalItem);
                this.sinkronisasiBarangService.insertCommit(stockCommit2);
                updateBarangStatus = this.barangService.updateStokBarangToko(-totalItem, this.barang.getId());
            } else if (this.helper.getStatusProgram().equals("GUDANG")) {
                stockCommit2 = new StockCommit();
                stockCommit2.setIdBarang(this.barang.getId());
                stockCommit2.setIdTransaksi("koreksi-stok" + this.barang.getId());
                stockCommit2.setQueryType("kurang");
                stockCommit2.setStockType("gudang");
                stockCommit2.setTransactionType("Koreksi Stok");
                stockCommit2.setJumlahBarang(totalItem);
                this.sinkronisasiBarangService.insertCommit(stockCommit2);
                updateBarangStatus = this.barangService.updateStokBarangGudang(-totalItem, this.barang.getId());
            }
        }
        if (updateBarangStatus == 0) {
            JOptionPane.showMessageDialog(null, "Penyimpanan Data Gagal");
        } else if (updateBarangStatus == 1) {
            KoreksiStok koreksiStok = new KoreksiStok();
            if (this.helper.getStatusProgram().equals("TOKO")) {
                koreksiStok.setStatus("TOKO");
                koreksiStok.setStokAwal(stokAwalToko);
            } else if (this.helper.getStatusProgram().equals("GUDANG")) {
                koreksiStok.setStatus("GUDANG");
                koreksiStok.setStokAwal(stokAwalGudang);
            }
            koreksiStok.setBarangId(this.barang.getId());
            koreksiStok.setDate(new Date());
            koreksiStok.setJumlah(totalItem);
            koreksiStok.setSatuan(this.barang.getSatuan1());
            koreksiStok.setJenis(this.actionCmb.getSelectedItem().toString());
            koreksiStok.setKaryawanId(this.helper.getKaryawan().getId());
            this.koreksiStokService.createKoreksiStok(koreksiStok);
            JOptionPane.showMessageDialog(null, "Penyimpanan Data Berhasil");
            this.setDefault();
        }
    }

    private void loadKodeBarang(String kodeBarang) {
        this.barang = this.barangService.getBarang(kodeBarang);
        if (this.barang != null) {
            this.konversiLbl1.setText(this.barang.getSatuan2());
            this.konversiLbl2.setText(this.barang.getSatuan1());
            this.konversiLbl3.setText(this.barang.getSatuan3());
            this.konversiLbl4.setText(this.barang.getSatuan2());
            this.konversiField1.setText(this.barang.getKonversi21() + "");
            this.konversiField2.setText(this.barang.getKonversi32() + "");
            this.kodeBarangField.setText(kodeBarang);
            this.namaBarangField.setText(this.barang.getNama());
            float stokTotal = 0.0f;
            if (this.helper.getStatusProgram().equals("TOKO")) {
                stokTotal = this.barang.getStokToko();
            } else if (this.helper.getStatusProgram().equals("GUDANG")) {
                stokTotal = this.barang.getStokGudang();
            }
            float stok1 = 0.0f;
            float stok2 = 0.0f;
            float stok3 = 0.0f;
            if (this.barang.getSatuan2().equals("") && this.barang.getSatuan3().equals("")) {
                stok1 = stokTotal;
            } else if (this.barang.getSatuan3().equals("")) {
                stok2 = (long)stokTotal / (long)this.barang.getKonversi21();
                stok1 = this.helper.round(stokTotal % (float)this.barang.getKonversi21(), 2);
            } else {
                stok3 = (long)stokTotal / (long)(this.barang.getKonversi21() * this.barang.getKonversi32());
                stok2 = (long)(stokTotal % (float)(this.barang.getKonversi21() * this.barang.getKonversi32())) / (long)this.barang.getKonversi21();
                stok1 = this.helper.round(stokTotal % (float)(this.barang.getKonversi21() * this.barang.getKonversi32()) % (float)this.barang.getKonversi21(), 2);
            }
            this.stokTokoField1.setText(stok1 + "");
            this.stokTokoField2.setText((long)stok2 + "");
            this.stokTokoField3.setText((long)stok3 + "");
            this.stokTokoLbl1.setText(this.barang.getSatuan1());
            this.stokTokoLbl2.setText(this.barang.getSatuan2());
            this.stokTokoLbl3.setText(this.barang.getSatuan3());
            this.satuanQuantity1.setText(this.barang.getSatuan1());
            this.satuanQuantity2.setText(this.barang.getSatuan2());
            this.satuanQuantity3.setText(this.barang.getSatuan3());
            this.actionCmb.requestFocus();
        } else {
            JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan");
        }
    }

    private void escapeClick() {
        this.kodeBarangField.requestFocus();
        this.kodeBarangField.selectAll();
    }

    private void kodeBarangFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 113) {
            this.sd = new SearchDialog(null, true, "Pencarian Data Barang");
            this.sd.addSearchDialogListener(new searchBarangDialogEventHandler());
            int w = this.sd.getWidth();
            int h = this.sd.getHeight();
            this.sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            this.sd.setVisible(true);
        } else if (evt.getKeyCode() == 10 || evt.getKeyCode() == 9) {
            if (this.kodeBarangField.getText().equals("")) {
                this.sd = new SearchDialog(null, true, "Pencarian Data Barang");
                this.sd.addSearchDialogListener(new searchBarangDialogEventHandler());
                int w = this.sd.getWidth();
                int h = this.sd.getHeight();
                this.sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                this.sd.setVisible(true);
            } else {
                this.loadKodeBarang(this.kodeBarangField.getText());
            }
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void actionCmbKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == 39) {
            this.quantity1Field.requestFocus();
            this.quantity1Field.selectAll();
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        }
    }

    private void quantity1FieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (this.helper.isFloat(this.quantity1Field.getText())) {
                this.quantity2Field.requestFocus();
                this.quantity2Field.selectAll();
                this.quantity1Field.setBackground(Color.white);
            } else {
                this.quantity1Field.setBackground(Color.red);
            }
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 38) {
            this.actionCmb.requestFocus();
        }
    }

    private void quantity2FieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (this.helper.isFloat(this.quantity2Field.getText())) {
                this.quantity3Field.requestFocus();
                this.quantity3Field.selectAll();
                this.quantity2Field.setBackground(Color.white);
            } else {
                this.quantity2Field.setBackground(Color.red);
            }
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 38) {
            this.quantity1Field.requestFocus();
            this.quantity1Field.selectAll();
        }
    }

    private void quantity3FieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (this.helper.isFloat(this.quantity3Field.getText())) {
                this.saveButton.requestFocus();
                this.quantity3Field.setBackground(Color.white);
            } else {
                this.quantity3Field.setBackground(Color.red);
            }
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 38) {
            this.quantity2Field.requestFocus();
            this.quantity2Field.selectAll();
        }
    }

    private void saveButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.saveClick();
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 38) {
            this.quantity3Field.requestFocus();
            this.quantity3Field.selectAll();
        } else if (evt.getKeyCode() == 39) {
            this.cancelButton.requestFocus();
        }
    }

    private void cancelButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.dispose();
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 37) {
            this.cancelButton.requestFocus();
        }
    }

    private void saveButtonActionPerformed(ActionEvent evt) {
        this.saveClick();
    }

    private void cancelButtonActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private class searchPanelBarangEventHandler
    implements SearchPanelListener {
        private searchPanelBarangEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            KoreksiStokDialog.this.kodeBarangField.setText(chooseValue);
            KoreksiStokDialog.this.loadKodeBarang(chooseValue);
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
            List<Barang> barangs = KoreksiStokDialog.this.barangService.getBarangs("", searchWord, false, "", true);
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

