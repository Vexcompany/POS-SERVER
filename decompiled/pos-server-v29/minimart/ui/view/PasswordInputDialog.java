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
import javax.swing.JPasswordField;
import javax.swing.LayoutStyle;
import minimart.engine.model.Karyawan;
import minimart.engine.service.KaryawanService;
import minimart.ui.listener.PasswordInputListener;

public class PasswordInputDialog
extends JDialog {
    private JButton cancelButton;
    private JLabel jLabel1;
    private JLabel judulField;
    private JButton okButton;
    private JPasswordField passwordField;
    private PasswordInputListener listener;
    private KaryawanService karyawanService = new KaryawanService();

    public PasswordInputDialog(Frame parent, boolean modal, String title) {
        super(parent, modal);
        this.setTitle(title);
        this.initComponents();
    }

    public void addPasswordInputDialogListener(PasswordInputListener listener) {
        this.listener = listener;
    }

    public void setJudulText(String text) {
        this.judulField.setText(text);
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.passwordField = new JPasswordField();
        this.cancelButton = new JButton();
        this.okButton = new JButton();
        this.judulField = new JLabel();
        this.setDefaultCloseOperation(2);
        this.jLabel1.setText("Password :");
        this.passwordField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PasswordInputDialog.this.passwordFieldKeyPressed(evt);
            }
        });
        this.cancelButton.setLabel("Cancel");
        this.cancelButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PasswordInputDialog.this.cancelButtonActionPerformed(evt);
            }
        });
        this.okButton.setLabel("Ok");
        this.okButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PasswordInputDialog.this.okButtonActionPerformed(evt);
            }
        });
        this.okButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PasswordInputDialog.this.okButtonKeyPressed(evt);
            }
        });
        this.judulField.setFont(new Font("Tahoma", 1, 11));
        this.judulField.setText("Caption");
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.judulField, -1, -1, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addComponent(this.jLabel1).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.passwordField)).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGap(0, 253, Short.MAX_VALUE).addComponent(this.okButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.cancelButton))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap().addComponent(this.judulField).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.passwordField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.cancelButton).addComponent(this.okButton)).addContainerGap()));
        this.pack();
    }

    private void okButtonActionPerformed(ActionEvent evt) {
        Karyawan karyawan = this.karyawanService.getKaryawanByPassword(this.passwordField.getText());
        if (karyawan != null) {
            this.listener.passwordSucced(karyawan);
            this.dispose();
        } else {
            this.listener.passwordFailed(this.passwordField.getText());
            this.dispose();
        }
    }

    private void cancelButtonActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void passwordFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.okButton.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void okButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            Karyawan karyawan = this.karyawanService.getKaryawanByPassword(this.passwordField.getText());
            if (karyawan != null) {
                this.listener.passwordSucced(karyawan);
                this.dispose();
            } else {
                this.listener.passwordFailed(this.passwordField.getText());
                this.dispose();
            }
        } else if (evt.getKeyCode() == 39) {
            this.cancelButton.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.passwordField.requestFocus();
            this.passwordField.selectAll();
        }
    }
}

