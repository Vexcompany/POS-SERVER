/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view;

import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import minimart.engine.model.OTPUbahHarga;
import minimart.engine.service.OTPUbahHargaService;
import minimart.ui.listener.RequestOTPUbahHargaListener;

public class RequestOTPUbahHargaDialog
extends JDialog {
    private JButton cancelButton;
    private JTextField hargaField;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JButton okButton;
    private JTextField otpField;
    private JButton requestOTPButton;
    private JLabel subtitleLabel;
    private RequestOTPUbahHargaListener listener;
    private String nama_barang;
    private OTPUbahHarga ouh;
    private OTPUbahHarga ouhGet;
    private OTPUbahHargaService ouhService = new OTPUbahHargaService();

    public RequestOTPUbahHargaDialog(Frame parent, boolean modal, String title) {
        super(parent, modal);
        this.setTitle(title);
        this.initComponents();
    }

    public void addRequestOTPUbahHargaDialogListener(RequestOTPUbahHargaListener listener) {
        this.listener = listener;
    }

    public void setJudulText(String text) {
        this.subtitleLabel.setText(text);
    }

    public void setOTPUbahHarga(OTPUbahHarga ouh) {
        this.ouh = ouh;
    }

    public void setNamaBarang(String nama_barang) {
        this.nama_barang = nama_barang;
    }

    private void initComponents() {
        this.subtitleLabel = new JLabel();
        this.hargaField = new JTextField();
        this.jLabel1 = new JLabel();
        this.otpField = new JTextField();
        this.jLabel2 = new JLabel();
        this.requestOTPButton = new JButton();
        this.cancelButton = new JButton();
        this.okButton = new JButton();
        this.setDefaultCloseOperation(2);
        this.subtitleLabel.setFont(new Font("Tahoma", 1, 11));
        this.subtitleLabel.setText("Masukkan Perubahan harga untuk OTP Request");
        this.hargaField.setText("0");
        this.hargaField.setToolTipText("Harga Jual");
        this.hargaField.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                RequestOTPUbahHargaDialog.this.hargaFieldActionPerformed(evt);
            }
        });
        this.hargaField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                RequestOTPUbahHargaDialog.this.hargaFieldKeyPressed(evt);
            }
        });
        this.jLabel1.setText("Harga");
        this.otpField.setToolTipText("OTP");
        this.jLabel2.setText("OTP");
        this.requestOTPButton.setText("Request OTP");
        this.requestOTPButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                RequestOTPUbahHargaDialog.this.requestOTPButtonActionPerformed(evt);
            }
        });
        this.cancelButton.setText("Cancel");
        this.cancelButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                RequestOTPUbahHargaDialog.this.cancelButtonActionPerformed(evt);
            }
        });
        this.okButton.setText("OK");
        this.okButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                RequestOTPUbahHargaDialog.this.okButtonActionPerformed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.subtitleLabel, -1, -1, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addComponent(this.jLabel2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.otpField).addComponent(this.hargaField, -1, 126, Short.MAX_VALUE)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.requestOTPButton).addGap(0, 0, Short.MAX_VALUE))).addContainerGap()).addGroup(layout.createSequentialGroup().addGap(75, 75, 75).addComponent(this.cancelButton).addGap(27, 27, 27).addComponent(this.okButton).addContainerGap(-1, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.subtitleLabel).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.hargaField, -2, -1, -2).addComponent(this.jLabel1)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.otpField, -2, -1, -2).addComponent(this.jLabel2).addComponent(this.requestOTPButton)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.cancelButton).addComponent(this.okButton)).addContainerGap()));
        this.pack();
    }

    private void hargaFieldActionPerformed(ActionEvent evt) {
    }

    private void cancelButtonActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void requestOTPButtonActionPerformed(ActionEvent evt) {
        long harga = Long.parseLong(this.hargaField.getText());
        this.ouh.setHarga(harga);
        int result = this.ouhService.requestOTPUbahHarga(this.ouh);
        if (result > 0) {
            JOptionPane.showMessageDialog(null, "OTP berhasil di request, hubungi koordinator untuk mendapatkan OTP");
        } else {
            JOptionPane.showMessageDialog(null, "Gagal request OTP, coba lagi");
        }
    }

    private void okButtonActionPerformed(ActionEvent evt) {
        String uc = this.otpField.getText();
        if (uc.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Harap masukkan kode OTP");
        } else {
            this.ouhGet = this.ouhService.getOTPUbahHargaByUniqueCode(uc);
            if (this.ouhGet != null) {
                if (this.ouhGet.isUsed()) {
                    JOptionPane.showMessageDialog(null, "OTP sudah pernah dipakai, harap hubungi koordinator atau request OTP Baru");
                } else if (!this.ouhGet.getId_sku().equals(this.ouh.getId_sku())) {
                    JOptionPane.showMessageDialog(null, "ID Barang OTP dan ID Barang Aktif tidak sama, silahkan gunakan OTP Lain");
                } else if (!this.ouhGet.getM_customer_id().equals(this.ouh.getM_customer_id())) {
                    JOptionPane.showMessageDialog(null, "Customer pada OTP dan Customer Aktif tidak sama, silahkan gunakan OTP Lain");
                } else {
                    this.listener.requestOTPUbahHargaSucceed(this.ouhGet);
                    this.ouhService.updateUserOTPUbahHarga(this.ouhGet.getId());
                    this.dispose();
                }
            } else {
                JOptionPane.showMessageDialog(null, "OTP tidak ditemukan");
            }
        }
    }

    private void hargaFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }
}

