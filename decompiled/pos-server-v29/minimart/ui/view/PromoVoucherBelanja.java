/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import minimart.engine.model.Konstanta;
import minimart.engine.service.KonstantaService;

public class PromoVoucherBelanja
extends JDialog {
    private JCheckBox aktifkanCheck;
    private JButton exitBtn;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JScrollPane jScrollPane1;
    private JTextArea keteranganField;
    private JFormattedTextField minimumField;
    private JButton saveBtn;
    private JFormattedTextField voucherField;
    private KonstantaService konstantaService = new KonstantaService();
    private Konstanta konstantaAktif = new Konstanta();
    private Konstanta konstantaMinimum = new Konstanta();
    private Konstanta konstantaKeterangan = new Konstanta();
    private Konstanta konstantaVoucher = new Konstanta();

    public PromoVoucherBelanja(Frame parent, boolean modal) {
        super(parent, modal);
        this.initComponents();
        this.loadData();
    }

    private void loadData() {
        if (this.konstantaService.getConstantValue("aktifkanVoucher") == null) {
            this.konstantaAktif.setKonstan("aktifkanVoucher");
            this.konstantaAktif.setValue("NO");
            this.konstantaService.createConstant(this.konstantaAktif);
        } else {
            this.konstantaAktif.setKonstan("aktifkanVoucher");
            this.konstantaAktif.setValue(this.konstantaService.getConstantValue("aktifkanVoucher"));
        }
        if (this.konstantaService.getConstantValue("voucherBelanja") == null) {
            this.konstantaVoucher.setKonstan("voucherBelanja");
            this.konstantaVoucher.setValue("0");
            this.konstantaService.createConstant(this.konstantaVoucher);
        } else {
            this.konstantaVoucher.setKonstan("voucherBelanja");
            this.konstantaVoucher.setValue(this.konstantaService.getConstantValue("voucherBelanja"));
        }
        if (this.konstantaService.getConstantValue("minimumPembelian") == null) {
            this.konstantaMinimum.setKonstan("minimumPembelian");
            this.konstantaMinimum.setValue("0");
            this.konstantaService.createConstant(this.konstantaMinimum);
        } else {
            this.konstantaMinimum.setKonstan("minimumPembelian");
            this.konstantaMinimum.setValue(this.konstantaService.getConstantValue("minimumPembelian"));
        }
        if (this.konstantaService.getConstantValue("keteranganVoucher") == null) {
            this.konstantaKeterangan.setKonstan("keteranganVoucher");
            this.konstantaKeterangan.setValue("");
            this.konstantaService.createConstant(this.konstantaKeterangan);
        } else {
            this.konstantaKeterangan.setKonstan("keteranganVoucher");
            this.konstantaKeterangan.setValue(this.konstantaService.getConstantValue("keteranganVoucher"));
        }
        if (this.konstantaAktif.getValue().equals("YES")) {
            this.aktifkanCheck.setSelected(true);
        } else if (this.konstantaAktif.getValue().equals("NO")) {
            this.aktifkanCheck.setSelected(false);
        }
        this.minimumField.setValue(Integer.parseInt(this.konstantaMinimum.getValue()));
        this.voucherField.setValue(Integer.parseInt(this.konstantaVoucher.getValue()));
        this.keteranganField.setText(this.konstantaKeterangan.getValue());
    }

    private void saveClick() {
        this.konstantaVoucher.setValue(this.voucherField.getValue().toString());
        this.konstantaKeterangan.setValue(this.keteranganField.getText());
        this.konstantaMinimum.setValue(this.minimumField.getValue().toString());
        if (this.aktifkanCheck.isSelected()) {
            this.konstantaAktif.setValue("YES");
        } else {
            this.konstantaAktif.setValue("NO");
        }
        int a = this.konstantaService.updateConstant(this.konstantaAktif);
        int b = this.konstantaService.updateConstant(this.konstantaVoucher);
        int c = this.konstantaService.updateConstant(this.konstantaKeterangan);
        int d = this.konstantaService.updateConstant(this.konstantaMinimum);
        if (a == 1 && b == 1 && c == 1 && d == 1) {
            JOptionPane.showMessageDialog(null, "PENYIMPANAN DATA BERHASIL");
            this.exitBtn.requestFocus();
        }
    }

    private void initComponents() {
        this.aktifkanCheck = new JCheckBox();
        this.jLabel1 = new JLabel();
        this.voucherField = new JFormattedTextField();
        this.jLabel2 = new JLabel();
        this.minimumField = new JFormattedTextField();
        this.exitBtn = new JButton();
        this.saveBtn = new JButton();
        this.jLabel4 = new JLabel();
        this.jScrollPane1 = new JScrollPane();
        this.keteranganField = new JTextArea();
        this.jLabel3 = new JLabel();
        this.setDefaultCloseOperation(2);
        this.aktifkanCheck.setText("Aktifkan");
        this.jLabel1.setText("Voucher Belanja");
        this.voucherField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getIntegerInstance())));
        this.voucherField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PromoVoucherBelanja.this.voucherFieldKeyPressed(evt);
            }
        });
        this.jLabel2.setText("Tiap pembelian");
        this.minimumField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getIntegerInstance())));
        this.minimumField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PromoVoucherBelanja.this.minimumFieldKeyPressed(evt);
            }
        });
        this.exitBtn.setText("Exit");
        this.exitBtn.setPreferredSize(new Dimension(73, 23));
        this.exitBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PromoVoucherBelanja.this.exitBtnActionPerformed(evt);
            }
        });
        this.exitBtn.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PromoVoucherBelanja.this.exitBtnKeyPressed(evt);
            }
        });
        this.saveBtn.setText("Save");
        this.saveBtn.setPreferredSize(new Dimension(73, 23));
        this.saveBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PromoVoucherBelanja.this.saveBtnActionPerformed(evt);
            }
        });
        this.saveBtn.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PromoVoucherBelanja.this.saveBtnKeyPressed(evt);
            }
        });
        this.jLabel4.setText("Keterangan");
        this.keteranganField.setColumns(20);
        this.keteranganField.setRows(5);
        this.keteranganField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PromoVoucherBelanja.this.keteranganFieldKeyPressed(evt);
            }
        });
        this.jScrollPane1.setViewportView(this.keteranganField);
        this.jLabel3.setText("%%%: Jumlah Voucher");
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addComponent(this.saveBtn, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.exitBtn, -2, -1, -2)).addGroup(layout.createSequentialGroup().addGap(87, 87, 87).addComponent(this.aktifkanCheck))).addContainerGap()).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.jLabel2, -1, -1, Short.MAX_VALUE).addComponent(this.jLabel1, -1, -1, Short.MAX_VALUE)).addComponent(this.jLabel4)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.jLabel3, GroupLayout.Alignment.LEADING, -2, 166, -2).addGroup(GroupLayout.Alignment.LEADING, layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.minimumField, GroupLayout.Alignment.LEADING, -1, 139, Short.MAX_VALUE).addComponent(this.voucherField, GroupLayout.Alignment.LEADING))).addGap(0, 28, Short.MAX_VALUE)).addComponent(this.jScrollPane1))))));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.voucherField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.minimumField, -2, -1, -2)).addGap(10, 10, 10).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel4).addComponent(this.jScrollPane1, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel3).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.aktifkanCheck).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.exitBtn, -2, -1, -2).addComponent(this.saveBtn, -2, -1, -2)).addContainerGap()));
        this.pack();
    }

    private void exitBtnActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void saveBtnActionPerformed(ActionEvent evt) {
        this.saveClick();
    }

    private void voucherFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.minimumField.requestFocus();
            this.minimumField.setText(this.minimumField.getText().replace(".", ""));
            this.minimumField.selectAll();
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void minimumFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.keteranganField.requestFocus();
            this.keteranganField.selectAll();
        } else if (evt.getKeyCode() == 38) {
            this.voucherField.requestFocus();
            this.voucherField.setText(this.minimumField.getText().replace(".", ""));
            this.voucherField.selectAll();
        } else if (evt.getKeyCode() == 27) {
            this.voucherField.requestFocus();
            this.voucherField.setText(this.minimumField.getText().replace(".", ""));
            this.voucherField.selectAll();
        }
    }

    private void keteranganFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.voucherField.requestFocus();
            this.voucherField.setText(this.minimumField.getText().replace(".", ""));
            this.voucherField.selectAll();
        }
    }

    private void saveBtnKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.voucherField.requestFocus();
            this.voucherField.setText(this.minimumField.getText().replace(".", ""));
            this.voucherField.selectAll();
        } else if (evt.getKeyCode() == 39) {
            this.exitBtn.requestFocus();
        } else if (evt.getKeyCode() == 10) {
            this.saveClick();
        }
    }

    private void exitBtnKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.voucherField.requestFocus();
            this.voucherField.setText(this.minimumField.getText().replace(".", ""));
            this.voucherField.selectAll();
        } else if (evt.getKeyCode() == 37) {
            this.exitBtn.requestFocus();
        } else if (evt.getKeyCode() == 10) {
            this.dispose();
        }
    }
}

