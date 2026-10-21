/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.toedter.calendar.JDateChooser
 *  org.json.simple.JSONObject
 */
package minimart.ui.view;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.DocumentFilter;
import javax.swing.text.NumberFormatter;
import minimart.engine.component.UppercaseDocumentFilter;
import minimart.engine.controller.Helper;
import minimart.engine.dao.KonstantaDao;
import minimart.engine.daoimpl.KonstantaDaoImpl;
import minimart.engine.model.Customer;
import minimart.engine.model.Karyawan;
import minimart.engine.restengine.CommonEngine;
import minimart.engine.service.CustomerCommitService;
import minimart.engine.service.CustomerService;
import minimart.engine.service.KonstantaService;
import minimart.ui.listener.PasswordInputListener;
import minimart.ui.listener.SearchDialogListener;
import minimart.ui.listener.SearchPanelListener;
import minimart.ui.listener.WilayahPanelListener;
import minimart.ui.view.AlamatOnline;
import minimart.ui.view.PasswordInputDialog;
import minimart.ui.view.SearchDialog;
import minimart.ui.view.SearchPanel;
import org.json.simple.JSONObject;

public class CustomerMaster
extends JDialog {
    private JButton alamatButton;
    private JTextField alamatField;
    private JLabel alamatLbl;
    private JDateChooser birthDateField;
    private ButtonGroup buttonGroup1;
    private ButtonGroup buttonGroup2;
    private ButtonGroup buttonGroup3;
    private JButton dataBaruButton;
    private JButton deleteButton;
    private JButton exitButton;
    private JRadioButton goldRadio;
    private JLabel jLabel1;
    private JLabel jLabel10;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JComboBox<String> keteranganCmb;
    private JTextField kodeCustField;
    private JLabel kodeCustLbl;
    private JTextField kotaField;
    private JLabel kotaLbl;
    private JTextField ktpField;
    private JFormattedTextField lamaKreditField;
    private JFormattedTextField limitKreditField;
    private JTextField namaCustField;
    private JLabel namaCustLbl;
    private JLabel noTelpLbl;
    private JTextField perusahaanField;
    private JRadioButton regularRadio;
    private JButton saveButton;
    private JTextField telpField;
    private DocumentFilter filter = new UppercaseDocumentFilter();
    private String action = "update";
    private CustomerCommitService customerCommitService = new CustomerCommitService();
    private CustomerService customerService = new CustomerService();
    private Customer customer = new Customer();
    private int screenWidth;
    private int screenHeight;
    private Helper helper = Helper.getInstance();
    private ButtonGroup bg = new ButtonGroup();
    KonstantaDao kd = new KonstantaDaoImpl();
    boolean checkedKode = true;
    boolean checkKtp = true;
    boolean checkNama = true;
    boolean checkAlamat = true;
    boolean checkKota = true;
    boolean checkTelp = true;
    boolean checkUsedTelp = true;
    boolean checkBirth = true;
    boolean checkTgl = true;
    boolean checkLamaKredit = true;
    boolean saveCust = true;
    boolean checkKeterangan = true;
    private KonstantaService konstantaService = new KonstantaService();

    public CustomerMaster(Frame parent, boolean modal, String tipeOutlet) {
        super(parent, modal);
        this.initComponents();
        this.initComponents2();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.setTitle("Entry Tabel Customer");
        this.bg.add(this.regularRadio);
        this.bg.add(this.goldRadio);
        this.lamaKreditField.setText("0");
        this.keteranganCmb.setBackground(Color.LIGHT_GRAY);
        this.regularRadio.setSelected(true);
        this.telpField.setEditable(true);
        this.telpField.setEnabled(true);
        if (tipeOutlet.equals("ATK")) {
            this.keteranganCmb.addItem("");
            this.keteranganCmb.addItem("Toko ATK/ Fotocopy");
            this.keteranganCmb.addItem("Sekolah");
            this.keteranganCmb.addItem("Pabrik");
            this.keteranganCmb.addItem("Modern Market");
            this.keteranganCmb.addItem("Kantor");
            this.keteranganCmb.addItem("Restoran");
            this.keteranganCmb.addItem("End User");
        } else if (tipeOutlet.equals("MART")) {
            this.keteranganCmb.addItem("");
            this.keteranganCmb.addItem("Toko/ Warung");
            this.keteranganCmb.addItem("Sekolah");
            this.keteranganCmb.addItem("Pabrik");
            this.keteranganCmb.addItem("Kantor");
            this.keteranganCmb.addItem("Restoran");
            this.keteranganCmb.addItem("End User");
        }
    }

    private void initComponents2() {
        ((AbstractDocument)this.kodeCustField.getDocument()).setDocumentFilter(this.filter);
        ((AbstractDocument)this.namaCustField.getDocument()).setDocumentFilter(this.filter);
        ((AbstractDocument)this.alamatField.getDocument()).setDocumentFilter(this.filter);
        ((AbstractDocument)this.kotaField.getDocument()).setDocumentFilter(this.filter);
        ((AbstractDocument)this.telpField.getDocument()).setDocumentFilter(this.filter);
        ((AbstractDocument)this.perusahaanField.getDocument()).setDocumentFilter(this.filter);
        Karyawan karyawan = this.helper.getKaryawan();
        if (karyawan.getRole().equals("KASIR") || karyawan.getRole().equalsIgnoreCase("ADMIN")) {
            this.deleteButton.setEnabled(false);
            this.lamaKreditField.setEnabled(false);
            this.limitKreditField.setEnabled(false);
        }
    }

    private void setDefault() {
        this.regularRadio.setSelected(true);
        this.ktpField.setText("");
        this.namaCustField.setText("");
        this.alamatField.setText("");
        this.kotaField.setText("");
        this.perusahaanField.setText("");
        this.telpField.setText("");
        this.birthDateField.setDate(null);
        this.limitKreditField.setValue(0);
        this.lamaKreditField.setValue(0);
        this.kodeCustField.setBackground(Color.white);
        this.ktpField.setBackground(Color.white);
        this.namaCustField.setBackground(Color.white);
        this.alamatField.setBackground(Color.white);
        this.kotaField.setBackground(Color.white);
        this.telpField.setBackground(Color.white);
        this.telpField.setEditable(true);
        this.telpField.setEnabled(true);
        this.birthDateField.setBackground(Color.white);
        this.limitKreditField.setBackground(Color.white);
        this.lamaKreditField.setBackground(Color.white);
        this.keteranganCmb.setSelectedIndex(0);
        this.keteranganCmb.setBackground(Color.lightGray);
    }

    private void escapeClick() {
        this.kodeCustField.requestFocus();
        this.kodeCustField.selectAll();
    }

    private void checkField() {
        if (this.kodeCustField.getText().equals("")) {
            this.checkedKode = false;
            this.kodeCustField.setBackground(Color.red);
        } else if (!this.kodeCustField.getText().equals("")) {
            this.checkedKode = true;
            this.kodeCustField.setBackground(Color.white);
        }
        if (this.ktpField.getText().equals("")) {
            this.checkKtp = false;
            this.ktpField.setBackground(Color.red);
        } else if (!this.ktpField.getText().equals("")) {
            this.checkKtp = true;
            this.ktpField.setBackground(Color.white);
        }
        if (this.namaCustField.getText().equals("")) {
            this.checkNama = false;
            this.namaCustField.setBackground(Color.red);
        } else if (!this.namaCustField.getText().equals("")) {
            this.checkNama = true;
            this.namaCustField.setBackground(Color.white);
        }
        if (this.alamatField.getText().equals("")) {
            this.checkAlamat = false;
            this.alamatField.setBackground(Color.red);
        } else if (!this.alamatField.getText().equals("")) {
            this.checkAlamat = true;
            this.alamatField.setBackground(Color.white);
        }
        if (this.kotaField.getText().equals("")) {
            this.checkKota = false;
            this.kotaField.setBackground(Color.red);
        } else if (!this.kotaField.getText().equals("")) {
            this.checkKota = true;
            this.kotaField.setBackground(Color.white);
        }
        if (this.telpField.getText().equals("")) {
            this.checkTelp = false;
            this.telpField.setBackground(Color.red);
        } else if (!this.telpField.getText().equals("")) {
            this.checkTelp = true;
            this.telpField.setBackground(Color.white);
        }
        if (!this.checkUsedTelp) {
            this.telpField.setBackground(Color.red);
        } else {
            this.telpField.setBackground(Color.white);
        }
        if (this.birthDateField.getDate() == null) {
            this.checkBirth = false;
            System.out.println(this.checkBirth);
            this.birthDateField.setBackground(Color.red);
        } else if (this.birthDateField.getDate() != null) {
            this.checkBirth = true;
            this.birthDateField.setBackground(Color.white);
        }
        if (Integer.parseInt(this.lamaKreditField.getText()) > 30) {
            this.checkLamaKredit = false;
            this.lamaKreditField.setBackground(Color.red);
            JOptionPane.showMessageDialog(null, "Maksimal lama kredit adalah 30 hari");
        } else {
            this.checkLamaKredit = true;
            this.lamaKreditField.setBackground(Color.white);
        }
        if (this.keteranganCmb.getSelectedIndex() == 0) {
            this.checkKeterangan = false;
            this.keteranganCmb.setBackground(Color.RED);
        } else {
            this.checkKeterangan = true;
            this.keteranganCmb.setBackground(Color.LIGHT_GRAY);
        }
    }

    private void saveCustomer() {
        if (this.action.equals("create")) {
            this.customer = new Customer();
            this.customer.setHutang(0L);
        }
        this.customer.setId(this.kodeCustField.getText());
        this.customer.setKtp(this.ktpField.getText());
        this.customer.setNama(this.namaCustField.getText());
        this.customer.setAlamat(this.alamatField.getText());
        this.customer.setKota(this.kotaField.getText());
        this.customer.setTelepon(this.telpField.getText());
        this.customer.setTanggalLahir(this.birthDateField.getDate());
        if (this.regularRadio.isSelected()) {
            this.customer.setTipe("REGULAR");
        } else if (this.goldRadio.isSelected()) {
            this.customer.setTipe("GOLD");
        }
        this.customer.setKeterangan(this.keteranganCmb.getSelectedItem().toString());
        this.customer.setNamaPerusahaan(this.perusahaanField.getText());
        this.customer.setAvailable(true);
        this.customer.setUpdated(new Date());
        if (this.limitKreditField.getText().equals("")) {
            this.customer.setLimitKredit(0);
        } else {
            this.customer.setLimitKredit(Integer.parseInt(this.limitKreditField.getValue().toString()));
        }
        if (this.lamaKreditField.getText().equals("")) {
            this.customer.setTop(0);
        } else {
            this.customer.setTop(Integer.parseInt(this.lamaKreditField.getValue().toString()));
        }
        int result = 0;
        CommonEngine comm = new CommonEngine();
        ObjectMapper objectMapper = new ObjectMapper();
        String succ_msg = new String();
        if (this.action.equals("create")) {
            this.customer.setCreated(new Date());
            result = this.customerService.createCustomer(this.customer);
            if (result == 1) {
                try {
                    System.out.println(this.jsonObjectCreate());
                    String cString = comm.post("/insert/customer", this.jsonObjectCreate());
                    succ_msg = "Data berhasil dikirim di online";
                    result &= 1;
                }
                catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Fail to Directly Commit - Coba sinkronisasi data terlebih dahulu");
                    result &= this.customerCommitService.insertCustomerCommit(this.customer, "INSERT") != false ? 1 : 0;
                    succ_msg = "Data sudah tersimpan di local";
                }
            }
        } else if (this.action.equals("update") && (result = this.customerService.updateCustomer2(this.customer, this.kodeCustField.getText())) == 1) {
            result &= this.customerCommitService.insertCustomerCommit(this.customer, "UPDATE") != false ? 1 : 0;
            succ_msg = "Data berhasil di update di lokal, mohon sinkronisasi data terlebih dahulu";
        }
        if (result == 1) {
            JOptionPane.showMessageDialog(null, succ_msg);
            this.escapeClick();
            this.setDefault();
            this.kodeCustField.setText("");
        } else {
            JOptionPane.showMessageDialog(null, "Penyimpanan Data Gagal");
            this.escapeClick();
        }
    }

    private void deleteCustomer() {
        int result;
        int confirm = JOptionPane.showConfirmDialog(null, "Data Akan Dihapus?", "Delete Data Confirmation", 2);
        if (confirm == 0 && (result = this.customerService.deleteCustomer(this.kodeCustField.getText())) == 1) {
            if ((result &= this.customerCommitService.insertCustomerCommit(this.customer, "DELETE") != false ? 1 : 0) == 1) {
                JOptionPane.showMessageDialog(null, "Data Telah Terhapus");
                this.escapeClick();
                this.setDefault();
                this.kodeCustField.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "Penghapusan Data Gagal");
                this.escapeClick();
            }
        }
    }

    private void loadKodeCustomer() {
        this.action = "update";
        this.saveButton.setEnabled(true);
        this.customer = this.customerService.getCustomerById(this.kodeCustField.getText());
        if (this.customer == null) {
            JOptionPane.showMessageDialog(null, "Data Tidak Ditemukan");
        } else {
            this.checkUsedTelp = true;
            this.ktpField.setText(this.customer.getKtp());
            this.namaCustField.setText(this.customer.getNama());
            this.alamatField.setText(this.customer.getAlamat());
            this.kotaField.setText(this.customer.getKota());
            this.telpField.setText(this.customer.getTelepon());
            this.telpField.setEditable(false);
            this.ktpField.requestFocus();
            this.birthDateField.setDate(this.customer.getTanggalLahir());
            this.limitKreditField.setValue(this.customer.getLimitKredit());
            this.lamaKreditField.setValue(this.customer.getTop());
            this.perusahaanField.setText(this.customer.getNamaPerusahaan());
            if (this.customer.getTipe().equals("REGULAR")) {
                this.regularRadio.setSelected(true);
            } else if (this.customer.getTipe().equals("GOLD")) {
                this.goldRadio.setSelected(true);
            }
            if (this.customer.getKeterangan() == null || this.customer.getKeterangan().equals("UNIDENTIFIED")) {
                this.keteranganCmb.setSelectedItem("");
            } else {
                this.keteranganCmb.setSelectedItem(this.customer.getKeterangan());
            }
        }
    }

    private void newData() {
        this.action = "create";
        this.customer = new Customer();
        this.setDefault();
        this.kodeCustField.setText(Helper.getInstance().generateCodeGeneral(this.customerService.getLastId()) + "_" + this.kd.getConstantValue("idCabang").toString());
        this.ktpField.requestFocus();
    }

    private void initComponents() {
        this.jLabel3 = new JLabel();
        this.buttonGroup1 = new ButtonGroup();
        this.buttonGroup2 = new ButtonGroup();
        this.buttonGroup3 = new ButtonGroup();
        this.kodeCustLbl = new JLabel();
        this.namaCustLbl = new JLabel();
        this.alamatLbl = new JLabel();
        this.kotaLbl = new JLabel();
        this.noTelpLbl = new JLabel();
        this.kodeCustField = new JTextField();
        this.namaCustField = new JTextField();
        this.alamatField = new JTextField();
        this.kotaField = new JTextField();
        this.telpField = new JTextField();
        this.saveButton = new JButton();
        this.exitButton = new JButton();
        this.dataBaruButton = new JButton();
        this.deleteButton = new JButton();
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.ktpField = new JTextField();
        this.jLabel4 = new JLabel();
        this.birthDateField = new JDateChooser();
        this.jLabel5 = new JLabel();
        this.limitKreditField = new JFormattedTextField();
        this.jLabel6 = new JLabel();
        this.jLabel7 = new JLabel();
        this.lamaKreditField = new JFormattedTextField();
        this.jLabel8 = new JLabel();
        this.regularRadio = new JRadioButton();
        this.goldRadio = new JRadioButton();
        this.jLabel9 = new JLabel();
        this.keteranganCmb = new JComboBox();
        this.jLabel10 = new JLabel();
        this.perusahaanField = new JTextField();
        this.alamatButton = new JButton();
        this.jLabel3.setText("jLabel3");
        this.setDefaultCloseOperation(2);
        this.kodeCustLbl.setText("Kode Cust");
        this.namaCustLbl.setText("Nama Cust");
        this.alamatLbl.setText("Alamat");
        this.kotaLbl.setText("Kota");
        this.noTelpLbl.setText("No. Telp");
        this.kodeCustField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                CustomerMaster.this.kodeCustFieldKeyPressed(evt);
            }
        });
        this.namaCustField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                CustomerMaster.this.namaCustFieldKeyPressed(evt);
            }
        });
        this.alamatField.setEditable(false);
        this.alamatField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                CustomerMaster.this.alamatFieldKeyPressed(evt);
            }
        });
        this.kotaField.setEditable(false);
        this.kotaField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                CustomerMaster.this.kotaFieldKeyPressed(evt);
            }
        });
        this.telpField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                CustomerMaster.this.telpFieldKeyPressed(evt);
            }
        });
        this.saveButton.setText("Save");
        this.saveButton.setPreferredSize(new Dimension(73, 23));
        this.saveButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CustomerMaster.this.saveButtonActionPerformed(evt);
            }
        });
        this.saveButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                CustomerMaster.this.saveButtonKeyPressed(evt);
            }
        });
        this.exitButton.setText("Exit");
        this.exitButton.setPreferredSize(new Dimension(73, 23));
        this.exitButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CustomerMaster.this.exitButtonActionPerformed(evt);
            }
        });
        this.exitButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                CustomerMaster.this.exitButtonKeyPressed(evt);
            }
        });
        this.dataBaruButton.setText("Data Baru");
        this.dataBaruButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CustomerMaster.this.dataBaruButtonActionPerformed(evt);
            }
        });
        this.deleteButton.setText("Delete");
        this.deleteButton.setPreferredSize(new Dimension(73, 23));
        this.deleteButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CustomerMaster.this.deleteButtonActionPerformed(evt);
            }
        });
        this.deleteButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                CustomerMaster.this.deleteButtonKeyPressed(evt);
            }
        });
        this.jLabel1.setText("F1");
        this.jLabel2.setText("No.KTP");
        this.ktpField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                CustomerMaster.this.ktpFieldKeyPressed(evt);
            }
        });
        this.jLabel4.setText("Tanggal Lahir");
        this.jLabel5.setText("Limit Kredit");
        this.limitKreditField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getIntegerInstance())));
        this.limitKreditField.setText("0");
        this.limitKreditField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                CustomerMaster.this.limitKreditFieldKeyPressed(evt);
            }
        });
        this.jLabel6.setText("Lama Kredit");
        this.jLabel7.setText("Hari");
        this.lamaKreditField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getIntegerInstance())));
        this.lamaKreditField.setText("0");
        this.lamaKreditField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                CustomerMaster.this.lamaKreditFieldKeyPressed(evt);
            }
        });
        this.jLabel8.setText("Tipe");
        this.regularRadio.setText("REGULAR");
        this.goldRadio.setText("GOLD");
        this.jLabel9.setText("Keterangan");
        this.jLabel10.setText("Perusahaan");
        this.perusahaanField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                CustomerMaster.this.perusahaanFieldKeyPressed(evt);
            }
        });
        this.alamatButton.setText("Isi Alamat");
        this.alamatButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CustomerMaster.this.alamatButtonActionPerformed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addGap(24, 24, 24).addComponent(this.alamatLbl).addGap(0, 0, Short.MAX_VALUE)).addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addGap(25, 25, 25).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel4).addComponent(this.jLabel5).addComponent(this.noTelpLbl).addComponent(this.kotaLbl)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.kotaField, -1, 215, Short.MAX_VALUE).addComponent(this.telpField).addComponent((Component)this.birthDateField, -1, 215, Short.MAX_VALUE).addComponent(this.limitKreditField).addComponent(this.alamatField))).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel6).addComponent(this.jLabel8)).addGap(26, 26, 26).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(layout.createSequentialGroup().addComponent(this.regularRadio).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.goldRadio)).addGroup(layout.createSequentialGroup().addComponent(this.lamaKreditField, -2, 112, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel7)))).addGroup(layout.createSequentialGroup().addComponent(this.jLabel9).addGap(26, 26, 26).addComponent(this.keteranganCmb, -2, 141, -2))).addGap(0, 0, Short.MAX_VALUE)))).addGroup(layout.createSequentialGroup().addGap(26, 26, 26).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.namaCustLbl).addComponent(this.jLabel2).addComponent(this.jLabel10).addComponent(this.kodeCustLbl)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.kodeCustField).addComponent(this.namaCustField, -1, 221, Short.MAX_VALUE).addComponent(this.ktpField).addComponent(this.perusahaanField)))).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(40, 40, 40).addComponent(this.jLabel1)).addGroup(layout.createSequentialGroup().addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.alamatButton).addComponent(this.dataBaruButton)))).addGap(10, 10, 10)).addGroup(layout.createSequentialGroup().addGap(63, 63, 63).addComponent(this.saveButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.deleteButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.exitButton, -2, -1, -2).addContainerGap(-1, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.kodeCustLbl).addComponent(this.kodeCustField, -2, -1, -2).addComponent(this.dataBaruButton)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.ktpField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.namaCustLbl).addComponent(this.namaCustField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel10).addComponent(this.perusahaanField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.alamatLbl).addComponent(this.alamatField, -2, -1, -2).addComponent(this.alamatButton)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.kotaLbl).addComponent(this.kotaField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.noTelpLbl).addComponent(this.telpField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jLabel4).addComponent((Component)this.birthDateField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel5).addComponent(this.limitKreditField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel6).addComponent(this.jLabel7).addComponent(this.lamaKreditField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel8).addComponent(this.regularRadio).addComponent(this.goldRadio)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel9).addComponent(this.keteranganCmb, -2, -1, -2)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.saveButton, -2, -1, -2).addComponent(this.deleteButton, -2, -1, -2).addComponent(this.exitButton, -2, -1, -2)).addGap(82, 82, 82)).addGroup(layout.createSequentialGroup().addComponent(this.jLabel1).addContainerGap(-1, Short.MAX_VALUE)))));
        this.pack();
    }

    private void kodeCustFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 113) {
            SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Customer");
            sd.addSearchDialogListener(new searchCustomerDialogEventHandler());
            int w = sd.getWidth();
            int h = sd.getHeight();
            sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
            sd.setVisible(true);
        } else if (evt.getKeyCode() == 10) {
            if (this.kodeCustField.getText().equals("")) {
                SearchDialog sd = new SearchDialog(null, true, "Pencarian Data Customer");
                sd.addSearchDialogListener(new searchCustomerDialogEventHandler());
                int w = sd.getWidth();
                int h = sd.getHeight();
                sd.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                sd.setVisible(true);
            } else {
                this.loadKodeCustomer();
            }
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        } else if (evt.getKeyCode() == 112) {
            this.newData();
        }
    }

    private void namaCustFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 10) {
            this.perusahaanField.requestFocus();
            this.perusahaanField.selectAll();
        } else if (evt.getKeyCode() == 38) {
            this.ktpField.requestFocus();
            this.ktpField.selectAll();
        }
    }

    private void alamatFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 10) {
            this.kotaField.requestFocus();
        } else if (evt.getKeyCode() == 38) {
            this.perusahaanField.requestFocus();
            this.perusahaanField.selectAll();
        }
    }

    private void kotaFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 10) {
            this.telpField.requestFocus();
        } else if (evt.getKeyCode() == 38) {
            this.alamatField.requestFocus();
            this.alamatField.selectAll();
        }
    }

    private void dataBaruButtonActionPerformed(ActionEvent evt) {
        this.newData();
    }

    private void telpFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 10) {
            List<Customer> customers = this.customerService.getCustomersByTelepon(this.telpField.getText());
            if (this.action.equals("create")) {
                if (customers.size() > 0) {
                    String tempId = "";
                    for (int a = 0; a < customers.size(); ++a) {
                        tempId = tempId + customers.get(a).getId() + ",";
                    }
                    JOptionPane.showMessageDialog(null, "Telp telah terpakai customer dengan iD = " + tempId);
                    this.checkUsedTelp = false;
                    this.telpField.setBackground(Color.red);
                } else if (customers.size() == 0) {
                    this.checkUsedTelp = true;
                    this.telpField.setBackground(Color.white);
                    this.saveButton.requestFocus();
                }
            } else if (this.action.equals("update")) {
                if (customers.size() > 0) {
                    if (!customers.get(0).getId().equals(this.customer.getId())) {
                        String tempId = "";
                        for (int a = 0; a < customers.size(); ++a) {
                            tempId = tempId + customers.get(a).getId() + ",";
                        }
                        JOptionPane.showMessageDialog(null, "Telp telah terpakai customer dengan iD = " + tempId);
                        this.checkUsedTelp = false;
                        this.telpField.setBackground(Color.red);
                    } else {
                        this.checkUsedTelp = true;
                        this.telpField.setBackground(Color.white);
                        this.saveButton.requestFocus();
                    }
                } else if (customers.size() == 0) {
                    this.checkUsedTelp = true;
                    this.telpField.setBackground(Color.white);
                    this.saveButton.requestFocus();
                }
            }
        } else if (evt.getKeyCode() == 38) {
            this.kotaField.requestFocus();
            this.kotaField.selectAll();
        }
    }

    private void saveButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 10) {
            this.checkField();
            if (this.checkedKode && this.checkAlamat && this.checkKota && this.checkKtp && this.checkLamaKredit && this.checkNama && this.checkTelp && this.checkTgl && this.checkedKode && this.checkBirth && this.checkUsedTelp && this.checkKeterangan) {
                String testIdCabang;
                String idCabang;
                String idCust = this.customer.getId();
                int leng = idCust.length();
                if (leng == 10) {
                    this.saveCust = true;
                } else if (leng > 10 && !(idCabang = this.konstantaService.getConstantValue("idCabang")).equals(testIdCabang = idCust.substring(11))) {
                    this.saveCust = false;
                }
                if (this.saveCust) {
                    this.saveCustomer();
                } else {
                    PasswordInputDialog passwordInputDialog2 = new PasswordInputDialog(null, true, "Input Password");
                    passwordInputDialog2.addPasswordInputDialogListener(new passwordInputDialog());
                    int w = passwordInputDialog2.getWidth();
                    int h = passwordInputDialog2.getHeight();
                    passwordInputDialog2.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                    passwordInputDialog2.setJudulText("Tidak Dapat Merubah Data Customer Dari Cabang Lain");
                    passwordInputDialog2.setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(null, "ISI DATA DENGAN BENAR");
            }
        } else if (evt.getKeyCode() == 39) {
            this.deleteButton.requestFocus();
        } else if (evt.getKeyCode() == 38) {
            this.telpField.requestFocus();
            this.telpField.selectAll();
        }
    }

    private void deleteButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 10) {
            this.deleteCustomer();
        } else if (evt.getKeyCode() == 39) {
            this.exitButton.requestFocus();
        } else if (evt.getKeyCode() == 37) {
            this.saveButton.requestFocus();
        }
    }

    private void exitButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 10) {
            this.dispose();
        } else if (evt.getKeyCode() == 37) {
            this.deleteButton.requestFocus();
        }
    }

    private void saveButtonActionPerformed(ActionEvent evt) {
        this.checkField();
        if (this.checkedKode && this.checkAlamat && this.checkKota && this.checkKtp && this.checkLamaKredit && this.checkNama && this.checkTelp && this.checkTgl && this.checkedKode && this.checkBirth && this.checkUsedTelp && this.checkKeterangan) {
            if (this.action.equals("update")) {
                String idCust = this.customer.getId();
                int leng = idCust.length();
                if (leng == 10) {
                    this.saveCust = true;
                } else if (leng > 10) {
                    String testIdCabang;
                    String idCabang = this.konstantaService.getConstantValue("idCabang");
                    this.saveCust = idCabang.equals(testIdCabang = idCust.substring(11));
                }
            }
            if (this.saveCust) {
                this.saveCustomer();
            } else {
                PasswordInputDialog passwordInputDialog2 = new PasswordInputDialog(null, true, "Input Password");
                passwordInputDialog2.addPasswordInputDialogListener(new passwordInputDialog());
                int w = passwordInputDialog2.getWidth();
                int h = passwordInputDialog2.getHeight();
                passwordInputDialog2.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                passwordInputDialog2.setJudulText("Tidak Dapat Merubah Data Customer Dari Cabang Lain");
                passwordInputDialog2.setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(null, "ISI DATA DENGAN BENAR");
        }
    }

    private void deleteButtonActionPerformed(ActionEvent evt) {
        this.deleteCustomer();
    }

    private void exitButtonActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void ktpFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 10) {
            this.namaCustField.requestFocus();
        } else if (evt.getKeyCode() == 38) {
            this.kodeCustField.requestFocus();
            this.kodeCustField.selectAll();
        }
    }

    private void limitKreditFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 10) {
            this.lamaKreditField.requestFocus();
            this.lamaKreditField.setText(this.lamaKreditField.getText().replace(".", ""));
            this.lamaKreditField.selectAll();
        } else if (evt.getKeyCode() == 38) {
            this.telpField.requestFocus();
            this.telpField.selectAll();
        }
    }

    private void lamaKreditFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 38) {
            this.lamaKreditField.requestFocus();
            this.lamaKreditField.getText().replace(".", "");
            this.lamaKreditField.selectAll();
        } else if (evt.getKeyCode() == 10) {
            this.saveButton.requestFocus();
        }
    }

    private void perusahaanFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 10) {
            this.alamatField.requestFocus();
            this.alamatField.selectAll();
        } else if (evt.getKeyCode() == 38) {
            this.namaCustField.requestFocus();
            this.kodeCustField.selectAll();
        }
    }

    private void alamatButtonActionPerformed(ActionEvent evt) {
        boolean canGo = true;
        String idCustomer = this.kodeCustField.getText();
        String namaCustomer = this.namaCustField.getText();
        String telepon = this.telpField.getText();
        if (idCustomer.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Harap isi Kode Customer");
            return;
        }
        if (namaCustomer.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Harap isi Nama Customer");
            return;
        }
        if (telepon.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Harap isi No Telepon");
            return;
        }
        if (canGo) {
            JDialog dialog = new JDialog((Frame)SwingUtilities.getWindowAncestor(this), "Alamat Customer", true);
            AlamatOnline alamatOnline = new AlamatOnline(idCustomer, namaCustomer, telepon);
            alamatOnline.addListener(new AlamatOnlineDialogListener());
            dialog.getContentPane().add(alamatOnline);
            dialog.pack();
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        }
    }

    private String jsonObjectCreate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(this.birthDateField.getDate());
        long epochTime = 0L;
        try {
            dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Tokyo"));
            epochTime = dateFormat.parse(strDate).getTime();
        }
        catch (Exception e) {
            System.out.println(e);
        }
        String money = this.limitKreditField.getText().replace(".", "");
        JSONObject sampleObject = new JSONObject();
        sampleObject.put((Object)"id", (Object)this.kodeCustField.getText());
        sampleObject.put((Object)"ktp", (Object)this.ktpField.getText());
        sampleObject.put((Object)"idCabang", (Object)Integer.parseInt(this.kd.getConstantValue("idCabang")));
        sampleObject.put((Object)"nama", (Object)this.namaCustField.getText());
        sampleObject.put((Object)"alamat", (Object)this.alamatField.getText());
        sampleObject.put((Object)"telepon", (Object)this.telpField.getText());
        sampleObject.put((Object)"kota", (Object)this.kotaField.getText());
        sampleObject.put((Object)"tanggalLahir", (Object)epochTime);
        sampleObject.put((Object)"limitKredit", (Object)Integer.parseInt(money));
        sampleObject.put((Object)"top", (Object)Integer.parseInt(this.lamaKreditField.getText()));
        sampleObject.put((Object)"keterangan", (Object)this.keteranganCmb.getSelectedItem().toString());
        sampleObject.put((Object)"namaPerusahaan", (Object)this.perusahaanField.getText());
        if (this.regularRadio.isSelected()) {
            sampleObject.put((Object)"tipe", (Object)"REGULAR");
        } else if (this.goldRadio.isSelected()) {
            sampleObject.put((Object)"tipe", (Object)"GOLD");
        }
        System.out.println(sampleObject.toJSONString());
        return sampleObject.toJSONString();
    }

    private class AlamatOnlineDialogListener
    implements WilayahPanelListener {
        private AlamatOnlineDialogListener() {
        }

        @Override
        public void wilayahSave(String alamat, String kota, String telp) {
            CustomerMaster.this.alamatField.setText(alamat);
            CustomerMaster.this.kotaField.setText(kota);
            CustomerMaster.this.telpField.setText(telp);
        }
    }

    private class searchPanelCustomerEventHandler
    implements SearchPanelListener {
        private searchPanelCustomerEventHandler() {
        }

        @Override
        public void tableClick(String chooseValue) {
            CustomerMaster.this.kodeCustField.setText(chooseValue);
            CustomerMaster.this.loadKodeCustomer();
        }
    }

    private class searchCustomerDialogEventHandler
    implements SearchDialogListener {
        private searchCustomerDialogEventHandler() {
        }

        @Override
        public void searchClick(String searchWord) {
            Object[] colName = new String[]{"KODE CUSTOMER", "NAMA CUSTOMER", "ALAMAT", "KOTA"};
            DefaultTableModel dtm = new DefaultTableModel(new String[0][1], colName);
            List<Customer> customers = CustomerMaster.this.customerService.getCustomers(searchWord);
            for (Customer customer : customers) {
                Object[] temp = new String[]{customer.getId(), customer.getNama(), customer.getAlamat(), customer.getKota()};
                dtm.addRow(temp);
            }
            SearchPanel sp = new SearchPanel(null, true, dtm, "Data Customer");
            sp.addSearchPanelListener(new searchPanelCustomerEventHandler());
            sp.setVisible(true);
        }

        public void dispose() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    private class passwordInputDialog
    implements PasswordInputListener {
        private passwordInputDialog() {
        }

        @Override
        public void passwordSucced(Karyawan karyawan) {
            if (karyawan.getRole().equalsIgnoreCase("DIREKTUR") || karyawan.getRole().equalsIgnoreCase("MANAGER")) {
                CustomerMaster.this.saveCustomer();
            }
        }

        @Override
        public void passwordFailed(String password) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}

