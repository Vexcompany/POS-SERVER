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
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import minimart.engine.controller.Helper;
import minimart.engine.model.Barang;
import minimart.engine.model.Karyawan;
import minimart.engine.model.KelebihanStok;
import minimart.engine.service.BarangService;
import minimart.engine.service.KelebihanStokService;
import minimart.engine.service.StokOpnamMandiriDetailService;
import minimart.ui.listener.MutasiBarangDialogListener;
import minimart.ui.listener.PasswordInputListener;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.view.PasswordInputDialog;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;

public class MutasiBarangDialog
extends JDialog {
    private JButton cancelButton;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel8;
    private JSeparator jSeparator1;
    private JTextField kodeBarangField;
    private JTextField namaBarangField;
    private JTextField quantity1Field;
    private JTextField quantity2Field;
    private JTextField quantity3Field;
    private JLabel satuan1;
    private JLabel satuan2;
    private JLabel satuan3;
    private JLabel satuanGudang1;
    private JLabel satuanGudang2;
    private JLabel satuanGudang3;
    private JLabel satuanToko1;
    private JLabel satuanToko2;
    private JLabel satuanToko3;
    private JButton saveButton;
    private JTextField stokGudangField1;
    private JTextField stokGudangField2;
    private JTextField stokGudangField3;
    private JTextField stokTokoField1;
    private JTextField stokTokoField2;
    private JTextField stokTokoField3;
    private BarangService barangService = new BarangService();
    private SearchDialog sd;
    private int screenHeight;
    private int screenWidth;
    private String mutasi;
    private JTextField tempField;
    private Helper helper = Helper.getInstance();
    private DefaultTableModel mutasiTableModel;
    private MutasiBarangDialogListener mutasiBarangDialogListener;
    private String satuan = "";
    Barang barang;
    private String tempSatuan;
    private KelebihanStokService kelebihanStokService = new KelebihanStokService();
    private float stokInput = 0.0f;
    private StokOpnamMandiriDetailService stokOpnamMandiriDetailService = new StokOpnamMandiriDetailService();
    private static final int SOM_HIDE_N_DAYS = 1;

    public MutasiBarangDialog(Frame parent, boolean modal, DefaultTableModel tableModel, String mutasi) {
        super(parent, modal);
        this.initComponents();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.setDefault();
        this.mutasiTableModel = tableModel;
        this.mutasi = mutasi;
    }

    private void setDefault() {
        this.namaBarangField.setText("");
        this.quantity1Field.setText("0");
        this.quantity2Field.setText("0");
        this.quantity3Field.setText("0");
        this.quantity1Field.setBackground(Color.white);
        this.quantity2Field.setBackground(Color.white);
        this.quantity3Field.setBackground(Color.white);
        this.satuan1.setText("");
        this.satuan2.setText("");
        this.satuan3.setText("");
        this.satuan = "";
    }

    public void addListener(MutasiBarangDialogListener barangDialogListener) {
        this.mutasiBarangDialogListener = barangDialogListener;
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.jLabel3 = new JLabel();
        this.namaBarangField = new JTextField();
        this.kodeBarangField = new JTextField();
        this.quantity1Field = new JTextField();
        this.satuan1 = new JLabel();
        this.quantity2Field = new JTextField();
        this.satuan2 = new JLabel();
        this.quantity3Field = new JTextField();
        this.satuan3 = new JLabel();
        this.saveButton = new JButton();
        this.cancelButton = new JButton();
        this.jSeparator1 = new JSeparator();
        this.jLabel4 = new JLabel();
        this.stokTokoField1 = new JTextField();
        this.satuanToko1 = new JLabel();
        this.stokTokoField2 = new JTextField();
        this.stokTokoField3 = new JTextField();
        this.satuanToko2 = new JLabel();
        this.satuanToko3 = new JLabel();
        this.jLabel8 = new JLabel();
        this.stokGudangField1 = new JTextField();
        this.stokGudangField2 = new JTextField();
        this.stokGudangField3 = new JTextField();
        this.satuanGudang1 = new JLabel();
        this.satuanGudang2 = new JLabel();
        this.satuanGudang3 = new JLabel();
        this.setDefaultCloseOperation(2);
        this.jLabel1.setText("Kode Barang");
        this.jLabel2.setText("Nama Barang");
        this.jLabel3.setText("Quantity");
        this.namaBarangField.setEditable(false);
        this.kodeBarangField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                MutasiBarangDialog.this.kodeBarangFieldKeyPressed(evt);
            }
        });
        this.quantity1Field.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                MutasiBarangDialog.this.quantity1FieldKeyPressed(evt);
            }
        });
        this.quantity2Field.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                MutasiBarangDialog.this.quantity2FieldKeyPressed(evt);
            }
        });
        this.quantity3Field.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                MutasiBarangDialog.this.quantity3FieldKeyPressed(evt);
            }
        });
        this.saveButton.setText("Save");
        this.saveButton.setPreferredSize(new Dimension(73, 23));
        this.saveButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                MutasiBarangDialog.this.saveButtonActionPerformed(evt);
            }
        });
        this.saveButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                MutasiBarangDialog.this.saveButtonKeyPressed(evt);
            }
        });
        this.cancelButton.setText("Cancel");
        this.cancelButton.setPreferredSize(new Dimension(73, 23));
        this.cancelButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                MutasiBarangDialog.this.cancelButtonActionPerformed(evt);
            }
        });
        this.cancelButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                MutasiBarangDialog.this.cancelButtonKeyPressed(evt);
            }
        });
        this.jSeparator1.setOrientation(1);
        this.jLabel4.setText("Stok Toko");
        this.jLabel8.setText("Stok  Gudang");
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(24, 24, 24).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(layout.createSequentialGroup().addComponent(this.jLabel1).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.kodeBarangField, -2, 160, -2)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel3).addGap(0, 0, Short.MAX_VALUE).addComponent(this.satuan1, -2, 61, -2)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel2).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.namaBarangField, -2, 160, -2).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.quantity1Field, -1, 93, Short.MAX_VALUE).addComponent(this.quantity2Field).addComponent(this.quantity3Field)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.satuan2, -2, 61, -2).addComponent(this.satuan3, -2, 63, -2))))))).addGroup(layout.createSequentialGroup().addGap(71, 71, 71).addComponent(this.saveButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.cancelButton, -2, -1, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.jSeparator1, -2, 2, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel4).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.stokTokoField1).addComponent(this.stokTokoField2).addComponent(this.stokTokoField3, -2, 69, -2))).addGroup(layout.createSequentialGroup().addComponent(this.jLabel8).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.stokGudangField1).addComponent(this.stokGudangField2).addComponent(this.stokGudangField3)))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.satuanToko1, -1, 44, Short.MAX_VALUE).addComponent(this.satuanToko2, -1, -1, Short.MAX_VALUE).addComponent(this.satuanToko3, -1, -1, Short.MAX_VALUE).addComponent(this.satuanGudang2, -1, -1, Short.MAX_VALUE).addComponent(this.satuanGudang1, -1, -1, Short.MAX_VALUE).addComponent(this.satuanGudang3, -1, -1, Short.MAX_VALUE)).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(2, 2, 2).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel4).addComponent(this.stokTokoField1)).addComponent(this.satuanToko1, -1, -1, Short.MAX_VALUE)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.stokTokoField2).addComponent(this.satuanToko2, -1, -1, Short.MAX_VALUE)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.stokTokoField3, -2, -1, -2).addComponent(this.satuanToko3, -2, 18, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel8).addComponent(this.stokGudangField1)).addComponent(this.satuanGudang1, -1, -1, Short.MAX_VALUE)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.stokGudangField2).addComponent(this.satuanGudang2, -1, -1, Short.MAX_VALUE)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.stokGudangField3, -2, -1, -2).addComponent(this.satuanGudang3, -2, 20, -2)).addGap(0, 0, Short.MAX_VALUE)).addComponent(this.jSeparator1).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.kodeBarangField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.namaBarangField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.quantity1Field, -1, 24, Short.MAX_VALUE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.jLabel3).addComponent(this.satuan1, -1, 20, Short.MAX_VALUE)).addGap(10, 10, 10))).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.quantity2Field).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)).addGroup(layout.createSequentialGroup().addComponent(this.satuan2, -2, 20, -2).addGap(10, 10, 10))).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.satuan3, -2, 20, -2).addGap(0, 0, Short.MAX_VALUE)).addComponent(this.quantity3Field, -1, 24, Short.MAX_VALUE)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.saveButton, -2, -1, -2).addComponent(this.cancelButton, -2, -1, -2)))).addContainerGap()));
        this.pack();
    }

    protected void loadKodeBarang(String id) {
        this.setDefault();
        this.barang = this.barangService.getBarang(id);
        this.kodeBarangField.setText(this.barang.getId());
        this.namaBarangField.setText(this.barang.getNama());
        this.satuan1.setText(this.barang.getSatuan1());
        this.satuan2.setText(this.barang.getSatuan2());
        this.satuan3.setText(this.barang.getSatuan3());
        float stokGudang1 = 0.0f;
        float stokGudang2 = 0.0f;
        float stokGudang3 = 0.0f;
        float stok1 = 0.0f;
        float stok2 = 0.0f;
        float stok3 = 0.0f;
        if (this.barang.getSatuan2().equals("") && this.barang.getSatuan3().equals("")) {
            stok1 = this.barang.getStokToko();
        } else if (this.barang.getSatuan3().equals("")) {
            stok2 = (long)this.barang.getStokToko() / (long)this.barang.getKonversi21();
            stok1 = this.helper.round(this.barang.getStokToko() % (float)this.barang.getKonversi21(), 2);
        } else {
            stok3 = (long)this.barang.getStokToko() / (long)(this.barang.getKonversi21() * this.barang.getKonversi32());
            stok2 = (long)(this.barang.getStokToko() % (float)(this.barang.getKonversi21() * this.barang.getKonversi32())) / (long)this.barang.getKonversi21();
            stok1 = this.helper.round(this.barang.getStokToko() % (float)(this.barang.getKonversi21() * this.barang.getKonversi32()) % (float)this.barang.getKonversi21(), 2);
        }
        if (this.barang.getSatuan2().equals("") && this.barang.getSatuan3().equals("")) {
            stokGudang1 = this.barang.getStokGudang();
        } else if (this.barang.getSatuan3().equals("")) {
            stokGudang2 = (long)this.barang.getStokGudang() / (long)this.barang.getKonversi21();
            stokGudang1 = this.helper.round(this.barang.getStokGudang() % (float)this.barang.getKonversi21(), 2);
        } else {
            stokGudang3 = (long)this.barang.getStokGudang() / (long)(this.barang.getKonversi21() * this.barang.getKonversi32());
            stokGudang2 = (long)(this.barang.getStokGudang() % (float)(this.barang.getKonversi21() * this.barang.getKonversi32())) / (long)this.barang.getKonversi21();
            stokGudang1 = this.helper.round(this.barang.getStokGudang() % (float)(this.barang.getKonversi21() * this.barang.getKonversi32()) % (float)this.barang.getKonversi21(), 2);
        }
        String result = this.stokOpnamMandiriDetailService.getStokOpnamMandiriDetailOnProgress(this.barang.getId(), 1);
        if (result.equals("")) {
            this.stokTokoField1.setText(stok1 + "");
            this.stokTokoField2.setText(stok2 + "");
            this.stokTokoField3.setText(stok3 + "");
            this.stokGudangField1.setText(stokGudang1 + "");
            this.stokGudangField2.setText(stokGudang2 + "");
            this.stokGudangField3.setText(stokGudang3 + "");
        }
        this.satuanToko1.setText(this.barang.getSatuan1());
        this.satuanToko2.setText(this.barang.getSatuan2());
        this.satuanToko3.setText(this.barang.getSatuan3());
        this.satuanGudang1.setText(this.barang.getSatuan1());
        this.satuanGudang2.setText(this.barang.getSatuan2());
        this.satuanGudang3.setText(this.barang.getSatuan3());
        this.quantity1Field.requestFocus();
        this.quantity1Field.selectAll();
    }

    protected void escapeClick() {
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

    private void quantity1FieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (this.quantity1Field.getText().equals("") || this.quantity1Field.getText().equals("0")) {
                this.quantity2Field.requestFocus();
                this.quantity2Field.selectAll();
                this.quantity1Field.setBackground(Color.white);
                this.quantity1Field.setText("0");
            } else if (!Helper.getInstance().isInteger(this.quantity1Field.getText())) {
                this.quantity1Field.setBackground(Color.red);
            } else {
                float jumlah;
                this.stokInput = jumlah = Float.parseFloat(this.quantity1Field.getText());
                if (this.mutasi.equalsIgnoreCase("gudang")) {
                    if (jumlah > this.barang.getStokGudang()) {
                        this.quantity1Field.setBackground(Color.red);
                        this.tempSatuan = "SAT1";
                        PasswordInputDialog passwordInputDialog2 = new PasswordInputDialog(null, true, "Input Password");
                        passwordInputDialog2.addPasswordInputDialogListener(new passwordInputDialogForQuantity());
                        int w = passwordInputDialog2.getWidth();
                        int h = passwordInputDialog2.getHeight();
                        passwordInputDialog2.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                        passwordInputDialog2.setJudulText("Jumlah Input Melebihi Stok, hubungi manajemen");
                        passwordInputDialog2.setVisible(true);
                    } else {
                        this.sat1Press();
                    }
                } else if (this.mutasi.equalsIgnoreCase("toko")) {
                    if (jumlah > this.barang.getStokToko()) {
                        this.quantity1Field.setBackground(Color.red);
                        this.tempSatuan = "SAT1";
                        PasswordInputDialog passwordInputDialog3 = new PasswordInputDialog(null, true, "Input Password");
                        passwordInputDialog3.addPasswordInputDialogListener(new passwordInputDialogForQuantity());
                        int w = passwordInputDialog3.getWidth();
                        int h = passwordInputDialog3.getHeight();
                        passwordInputDialog3.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                        passwordInputDialog3.setJudulText("Jumlah Input Melebihi Stok, hubungi manajemen");
                        passwordInputDialog3.setVisible(true);
                    } else {
                        this.sat1Press();
                    }
                }
            }
        } else if (evt.getKeyCode() == 27 || evt.getKeyCode() == 38) {
            this.escapeClick();
        }
    }

    private void quantity2FieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (this.quantity2Field.getText().equals("") || this.quantity2Field.getText().equals("0")) {
                this.quantity3Field.requestFocus();
                this.quantity3Field.selectAll();
                this.quantity2Field.setBackground(Color.white);
                this.quantity2Field.setText("0");
            } else if (!Helper.getInstance().isInteger(this.quantity1Field.getText())) {
                this.quantity2Field.setBackground(Color.red);
            } else {
                float jumlah;
                this.stokInput = jumlah = Float.parseFloat(this.quantity2Field.getText()) * (float)this.barang.getKonversi21();
                if (this.mutasi.equalsIgnoreCase("gudang")) {
                    if (jumlah > this.barang.getStokGudang()) {
                        this.quantity2Field.setBackground(Color.red);
                        this.tempSatuan = "SAT2";
                        PasswordInputDialog passwordInputDialog2 = new PasswordInputDialog(null, true, "Input Password");
                        passwordInputDialog2.addPasswordInputDialogListener(new passwordInputDialogForQuantity());
                        int w = passwordInputDialog2.getWidth();
                        int h = passwordInputDialog2.getHeight();
                        passwordInputDialog2.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                        passwordInputDialog2.setJudulText("Jumlah Input Melebihi Stok, hubungi manajemen");
                        passwordInputDialog2.setVisible(true);
                    } else {
                        this.sat2Press();
                    }
                } else if (this.mutasi.equalsIgnoreCase("toko")) {
                    if (jumlah > this.barang.getStokToko()) {
                        this.quantity2Field.setBackground(Color.red);
                        this.tempSatuan = "SAT2";
                        PasswordInputDialog passwordInputDialog3 = new PasswordInputDialog(null, true, "Input Password");
                        passwordInputDialog3.addPasswordInputDialogListener(new passwordInputDialogForQuantity());
                        int w = passwordInputDialog3.getWidth();
                        int h = passwordInputDialog3.getHeight();
                        passwordInputDialog3.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                        passwordInputDialog3.setJudulText("Jumlah Input Melebihi Stok, hubungi manajemen");
                        passwordInputDialog3.setVisible(true);
                    } else {
                        this.sat2Press();
                    }
                }
            }
        } else if (evt.getKeyCode() == 27 || evt.getKeyCode() == 38) {
            this.escapeClick();
        }
    }

    private void quantity3FieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (!this.quantity3Field.getText().equals("") && !this.quantity3Field.getText().equals("0")) {
                if (!Helper.getInstance().isInteger(this.quantity1Field.getText())) {
                    this.quantity2Field.setBackground(Color.red);
                } else {
                    float jumlah;
                    this.stokInput = jumlah = Float.parseFloat(this.quantity3Field.getText()) * (float)this.barang.getKonversi21() * (float)this.barang.getKonversi32();
                    if (this.mutasi.equalsIgnoreCase("gudang")) {
                        if (jumlah > this.barang.getStokGudang()) {
                            this.quantity3Field.setBackground(Color.red);
                            this.tempSatuan = "SAT3";
                            PasswordInputDialog passwordInputDialog2 = new PasswordInputDialog(null, true, "Input Password");
                            passwordInputDialog2.addPasswordInputDialogListener(new passwordInputDialogForQuantity());
                            int w = passwordInputDialog2.getWidth();
                            int h = passwordInputDialog2.getHeight();
                            passwordInputDialog2.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                            passwordInputDialog2.setJudulText("Jumlah Input Melebihi Stok, hubungi manajemen");
                            passwordInputDialog2.setVisible(true);
                        } else {
                            this.sat3Press();
                        }
                    } else if (this.mutasi.equalsIgnoreCase("toko")) {
                        if (jumlah > this.barang.getStokToko()) {
                            this.quantity3Field.setBackground(Color.red);
                            this.tempSatuan = "SAT3";
                            PasswordInputDialog passwordInputDialog3 = new PasswordInputDialog(null, true, "Input Password");
                            passwordInputDialog3.addPasswordInputDialogListener(new passwordInputDialogForQuantity());
                            int w = passwordInputDialog3.getWidth();
                            int h = passwordInputDialog3.getHeight();
                            passwordInputDialog3.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                            passwordInputDialog3.setJudulText("Jumlah Input Melebihi Stok, hubungi manajemen");
                            passwordInputDialog3.setVisible(true);
                        } else {
                            this.sat3Press();
                        }
                    }
                }
            }
        } else if (evt.getKeyCode() == 27 || evt.getKeyCode() == 38) {
            this.escapeClick();
        }
    }

    private void saveClick() {
        Object[] rowValue = new Object[]{this.kodeBarangField.getText(), this.namaBarangField.getText(), this.tempField.getText(), this.satuan};
        this.mutasiBarangDialogListener.saveClick(rowValue);
        this.escapeClick();
    }

    private void saveButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 38) {
            this.tempField.requestFocus();
            this.tempField.selectAll();
        } else if (evt.getKeyCode() == 39) {
            this.cancelButton.requestFocus();
        } else if (evt.getKeyCode() == 10) {
            this.saveClick();
        }
    }

    private void cancelButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 37) {
            this.saveButton.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 10) {
            this.dispose();
        }
    }

    private void saveButtonActionPerformed(ActionEvent evt) {
        this.saveClick();
    }

    private void cancelButtonActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    protected void loadDataFromTable(int row) {
        if (this.mutasiTableModel.getValueAt(row, 3).equals(this.barang.getSatuan1())) {
            this.quantity1Field.setText(this.mutasiTableModel.getValueAt(row, 2).toString());
        } else if (this.mutasiTableModel.getValueAt(row, 3).equals(this.barang.getSatuan2())) {
            this.quantity2Field.setText(this.mutasiTableModel.getValueAt(row, 2).toString());
        } else if (this.mutasiTableModel.getValueAt(row, 3).equals(this.barang.getSatuan3())) {
            this.quantity3Field.setText(this.mutasiTableModel.getValueAt(row, 2).toString());
        }
        this.satuan = this.mutasiTableModel.getValueAt(row, 3).toString();
    }

    private void searchTable(String idBarang) {
        for (int a = 0; a < this.mutasiTableModel.getRowCount(); ++a) {
            String barangId = this.mutasiTableModel.getValueAt(a, 0).toString();
            if (!idBarang.equals(barangId)) continue;
            this.loadDataFromTable(a);
            break;
        }
    }

    private void sat3Press() {
        this.quantity3Field.setBackground(Color.white);
        this.tempField = this.quantity3Field;
        this.satuan = this.barang.getSatuan3();
        this.saveButton.requestFocus();
        this.quantity2Field.setText("0");
        this.quantity1Field.setText("0");
    }

    private void sat2Press() {
        this.quantity2Field.setBackground(Color.white);
        this.tempField = this.quantity2Field;
        this.satuan = this.barang.getSatuan2();
        this.saveButton.requestFocus();
        this.quantity1Field.setText("0");
        this.quantity3Field.setText("0");
    }

    private void sat1Press() {
        this.quantity1Field.setBackground(Color.white);
        this.tempField = this.quantity1Field;
        this.satuan = this.barang.getSatuan1();
        this.saveButton.requestFocus();
        this.quantity2Field.setText("0");
        this.quantity3Field.setText("0");
    }

    private class searchPanelBarangEventHandler
    implements SearchPanelListener {
        private searchPanelBarangEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            MutasiBarangDialog.this.kodeBarangField.setText(chooseValue);
            MutasiBarangDialog.this.loadKodeBarang(chooseValue);
            MutasiBarangDialog.this.searchTable(chooseValue);
        }
    }

    private class passwordInputDialogForQuantity
    implements PasswordInputListener {
        private passwordInputDialogForQuantity() {
        }

        @Override
        public void passwordSucced(Karyawan karyawan) {
            if (karyawan.getRole().equalsIgnoreCase("DIREKTUR") || karyawan.getRole().equalsIgnoreCase("INVENTORY") || karyawan.getRole().equalsIgnoreCase("STAF INVENTORY")) {
                KelebihanStok kelebihanStok = new KelebihanStok();
                if (MutasiBarangDialog.this.tempSatuan.equalsIgnoreCase("SAT1")) {
                    MutasiBarangDialog.this.sat2Press();
                } else if (MutasiBarangDialog.this.tempSatuan.equalsIgnoreCase("SAT2")) {
                    MutasiBarangDialog.this.sat2Press();
                } else if (MutasiBarangDialog.this.tempSatuan.equalsIgnoreCase("SAT3")) {
                    MutasiBarangDialog.this.sat3Press();
                }
                kelebihanStok.setBarangId(MutasiBarangDialog.this.barang.getId());
                kelebihanStok.setStokSystem(MutasiBarangDialog.this.barang.getStokToko());
                kelebihanStok.setStokInput(MutasiBarangDialog.this.stokInput);
                kelebihanStok.setInputOleh(MutasiBarangDialog.this.helper.getKaryawan().getNama());
                kelebihanStok.setOtorisasiOleh(karyawan.getNama());
                kelebihanStok.setCreated(new Date());
                kelebihanStok.setKeterangan("MUTASI");
                MutasiBarangDialog.this.kelebihanStokService.createKelebihanStok(kelebihanStok);
            }
        }

        @Override
        public void passwordFailed(String password) {
            throw new UnsupportedOperationException("Not supported yet.");
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
            List<Barang> barangs = MutasiBarangDialog.this.barangService.getBarangs("", searchWord, false, "", true);
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

