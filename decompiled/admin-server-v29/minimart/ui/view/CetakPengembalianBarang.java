/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.util.Date;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import minimart.engine.controller.Helper;
import minimart.engine.model.Customer;
import minimart.engine.model.Karyawan;
import minimart.engine.model.Penjualan;
import minimart.ui.listener.CetakPengembalianListener;
import minimart.ui.listener.PasswordInputListener;

public class CetakPengembalianBarang
extends JDialog {
    private ButtonGroup buttonGroup1;
    private JButton cancelButton;
    private JRadioButton cashBtn;
    private JTextField customerAlamatField;
    private JTextField customerNameField;
    private JTextField hpField;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JTextField karyawanField;
    private JTextField keteranganField;
    private JButton printButton;
    private JFormattedTextField tanggalField;
    private JFormattedTextField totalField;
    private JRadioButton transferBtn;
    private JRadioButton voucherBtn;
    private boolean canGoThrough;
    private int screenHeight;
    private int screenWidth;
    private long totalRokok = 0L;
    private CetakPengembalianListener listener;
    private ButtonGroup buttonGroup = new ButtonGroup();
    private Helper helper = Helper.getInstance();
    private String pembayaran = "VOUCHER";
    private ButtonGroup bg = new ButtonGroup();

    public CetakPengembalianBarang(Frame parent, boolean modal, long totalPengembalian, Customer customer, Penjualan penjualan, String pembayaran) {
        super(parent, modal);
        this.initComponents();
        this.setTitle("Print");
        this.requestDefaultFocus();
        this.hpField.setEditable(false);
        this.karyawanField.setText(this.helper.getKaryawan().getNama());
        this.keteranganField.setText(penjualan.getId());
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.totalField.setValue(totalPengembalian);
        this.tanggalField.setValue(new Date());
        this.customerNameField.setText(customer.getNama());
        this.customerAlamatField.setText(customer.getAlamat());
        this.pembayaran = pembayaran;
        this.bg.add(this.transferBtn);
        this.bg.add(this.voucherBtn);
        this.bg.add(this.cashBtn);
        if (customer.getId().equals("C")) {
            this.hpField.setEditable(true);
            this.hpField.setText("");
        } else {
            this.hpField.setText(customer.getTelepon());
        }
        if (pembayaran.equals("VOUCHER")) {
            this.voucherBtn.setSelected(true);
        }
        if (pembayaran.equals("TRANSFER")) {
            this.transferBtn.setSelected(true);
        }
        if (pembayaran.equals("CASH")) {
            this.cashBtn.setSelected(true);
        }
        this.transferBtn.setEnabled(false);
        this.voucherBtn.setEnabled(false);
        this.cashBtn.setEnabled(false);
    }

    protected void requestDefaultFocus() {
        this.printButton.requestFocus();
    }

    public void addListener(CetakPengembalianListener listener) {
        this.listener = listener;
    }

    private void initComponents() {
        this.buttonGroup1 = new ButtonGroup();
        this.jLabel1 = new JLabel();
        this.jLabel3 = new JLabel();
        this.jLabel4 = new JLabel();
        this.tanggalField = new JFormattedTextField();
        this.karyawanField = new JTextField();
        this.customerNameField = new JTextField();
        this.customerAlamatField = new JTextField();
        this.jLabel6 = new JLabel();
        this.printButton = new JButton();
        this.cancelButton = new JButton();
        this.totalField = new JFormattedTextField();
        this.jLabel2 = new JLabel();
        this.keteranganField = new JTextField();
        this.voucherBtn = new JRadioButton();
        this.transferBtn = new JRadioButton();
        this.jLabel5 = new JLabel();
        this.hpField = new JTextField();
        this.cashBtn = new JRadioButton();
        this.setDefaultCloseOperation(2);
        this.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                CetakPengembalianBarang.this.formKeyPressed(evt);
            }
        });
        this.jLabel1.setText("Tanggal");
        this.jLabel3.setText("Petugas");
        this.jLabel4.setText("Customer");
        this.tanggalField.setEditable(false);
        this.karyawanField.setEditable(false);
        this.customerNameField.setEditable(false);
        this.customerAlamatField.setEditable(false);
        this.jLabel6.setFont(new Font("Tahoma", 1, 18));
        this.jLabel6.setText("Total");
        this.printButton.setText("Print");
        this.printButton.setPreferredSize(new Dimension(73, 23));
        this.printButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CetakPengembalianBarang.this.printButtonActionPerformed(evt);
            }
        });
        this.printButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                CetakPengembalianBarang.this.printButtonKeyPressed(evt);
            }
        });
        this.cancelButton.setText("Cancel");
        this.cancelButton.setPreferredSize(new Dimension(73, 23));
        this.cancelButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                CetakPengembalianBarang.this.cancelButtonActionPerformed(evt);
            }
        });
        this.cancelButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                CetakPengembalianBarang.this.cancelButtonKeyPressed(evt);
            }
        });
        this.totalField.setEditable(false);
        this.totalField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(new DecimalFormat("\u00a4#,##0.--"))));
        this.totalField.setFont(new Font("Tahoma", 1, 18));
        this.jLabel2.setFont(new Font("Tahoma", 1, 14));
        this.jLabel2.setText("ID Penjualan");
        this.keteranganField.setEditable(false);
        this.keteranganField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                CetakPengembalianBarang.this.keteranganFieldKeyPressed(evt);
            }
        });
        this.voucherBtn.setText("Retur Voucher");
        this.transferBtn.setText("Retur Transfer");
        this.jLabel5.setText("No HP Customer");
        this.hpField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                CetakPengembalianBarang.this.hpFieldKeyPressed(evt);
            }
        });
        this.cashBtn.setText("Retur Cash");
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.transferBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.voucherBtn).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.cashBtn).addGap(0, 0, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addGap(20, 20, 20).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addComponent(this.jLabel3).addComponent(this.jLabel4).addComponent(this.jLabel6).addComponent(this.jLabel2).addComponent(this.jLabel5)).addGap(67, 67, 67).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createSequentialGroup().addGap(0, 137, Short.MAX_VALUE).addComponent(this.printButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.cancelButton, -2, -1, -2)).addComponent(this.customerAlamatField, GroupLayout.Alignment.LEADING, -1, 286, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.karyawanField, GroupLayout.Alignment.LEADING, -1, 163, Short.MAX_VALUE).addComponent(this.tanggalField, GroupLayout.Alignment.LEADING, -1, 163, Short.MAX_VALUE).addComponent(this.customerNameField, -1, 163, Short.MAX_VALUE)).addGap(123, 123, 123))).addGap(23, 23, 23)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.keteranganField, GroupLayout.Alignment.LEADING).addComponent(this.totalField, GroupLayout.Alignment.LEADING, -1, 222, Short.MAX_VALUE).addComponent(this.hpField)).addContainerGap(-1, Short.MAX_VALUE)))));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.tanggalField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3).addComponent(this.karyawanField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel4).addComponent(this.customerNameField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.customerAlamatField, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.keteranganField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.totalField, -2, -1, -2).addComponent(this.jLabel6)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.hpField, -2, -1, -2).addComponent(this.jLabel5)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.voucherBtn).addComponent(this.transferBtn).addComponent(this.cashBtn)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.printButton, -2, -1, -2).addComponent(this.cancelButton, -2, -1, -2)).addContainerGap()));
        this.pack();
    }

    private void printButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (this.hpField.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Masukkan no Telp Customer yang bisa dihubungi");
            } else if (!(this.voucherBtn.isSelected() || this.transferBtn.isSelected() || this.cashBtn.isSelected())) {
                JOptionPane.showMessageDialog(null, "Pilih Tipe Returan Voucher/Transfer");
            } else {
                this.printButton.setEnabled(false);
                this.cancelButton.requestFocus();
                if (this.transferBtn.isSelected()) {
                    this.listener.printClick(this.keteranganField.getText(), false, this.hpField.getText());
                } else if (this.voucherBtn.isSelected()) {
                    this.listener.printClick(this.keteranganField.getText(), true, this.hpField.getText());
                } else if (this.cashBtn.isSelected()) {
                    this.listener.printClick(this.keteranganField.getText(), false, this.hpField.getText());
                }
            }
        } else if (evt.getKeyCode() == 39) {
            this.cancelButton.requestFocus();
        } else if (evt.getKeyCode() == 38 || evt.getKeyCode() == 27) {
            this.keteranganField.requestFocus();
            this.keteranganField.selectAll();
        }
    }

    private void cancelButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.dispose();
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void cancelButtonActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void printButtonActionPerformed(ActionEvent evt) {
        if (this.hpField.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Masukkan no Telp Customer yang bisa dihubungi");
        } else if (!(this.voucherBtn.isSelected() || this.transferBtn.isSelected() || this.cashBtn.isSelected())) {
            JOptionPane.showMessageDialog(null, "Pilih Tipe Returan Voucher/Transfer/Cash");
        } else {
            this.printButton.setEnabled(false);
            this.cancelButton.requestFocus();
            if (this.transferBtn.isSelected()) {
                this.listener.printClick(this.keteranganField.getText(), false, this.hpField.getText());
            } else if (this.voucherBtn.isSelected()) {
                this.listener.printClick(this.keteranganField.getText(), true, this.hpField.getText());
            } else if (this.cashBtn.isSelected()) {
                this.listener.printClick(this.keteranganField.getText(), false, this.hpField.getText());
            }
        }
    }

    private void keteranganFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.printButton.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void formKeyPressed(KeyEvent evt) {
    }

    private void hpFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.printButton.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private class passwordSaveEventHandler
    implements PasswordInputListener {
        private passwordSaveEventHandler() {
        }

        @Override
        public void passwordSucced(Karyawan karyawan) {
            if (karyawan.getRole().equalsIgnoreCase("DIREKTUR")) {
                CetakPengembalianBarang.this.listener.printClick(CetakPengembalianBarang.this.keteranganField.getText(), false, CetakPengembalianBarang.this.hpField.getText());
            }
        }

        @Override
        public void passwordFailed(String password) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}

