/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view;

import java.awt.Dimension;
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
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import minimart.ui.listener.PenjualanParsialDialogListener;

public class PenjualanParsialDialog
extends JDialog {
    private JButton cancelBtn;
    private JTextField idField;
    private JLabel jLabel1;
    private JButton okBtn;
    private PenjualanParsialDialogListener dialogListener;

    public PenjualanParsialDialog(Frame parent, boolean modal) {
        super(parent, modal);
        this.initComponents();
    }

    public void addDialogListener(PenjualanParsialDialogListener dialogListener) {
        this.dialogListener = dialogListener;
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.idField = new JTextField();
        this.okBtn = new JButton();
        this.cancelBtn = new JButton();
        this.setDefaultCloseOperation(2);
        this.jLabel1.setText("MASUKKAN ID PENJUALAN PARSIAL");
        this.idField.setFont(new Font("Tahoma", 0, 24));
        this.idField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenjualanParsialDialog.this.idFieldKeyPressed(evt);
            }
        });
        this.okBtn.setText("OK");
        this.okBtn.setPreferredSize(new Dimension(80, 23));
        this.okBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PenjualanParsialDialog.this.okBtnActionPerformed(evt);
            }
        });
        this.okBtn.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenjualanParsialDialog.this.okBtnKeyPressed(evt);
            }
        });
        this.cancelBtn.setText("CANCEL");
        this.cancelBtn.setPreferredSize(new Dimension(80, 23));
        this.cancelBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PenjualanParsialDialog.this.cancelBtnActionPerformed(evt);
            }
        });
        this.cancelBtn.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PenjualanParsialDialog.this.cancelBtnKeyPressed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(113, 113, 113).addComponent(this.jLabel1)).addGroup(layout.createSequentialGroup().addGap(58, 58, 58).addComponent(this.idField, -2, 276, -2)).addGroup(layout.createSequentialGroup().addGap(116, 116, 116).addComponent(this.okBtn, -2, -1, -2).addGap(18, 18, 18).addComponent(this.cancelBtn, -2, -1, -2))).addContainerGap(66, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel1).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.idField, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.okBtn, -2, -1, -2).addComponent(this.cancelBtn, -2, -1, -2)).addContainerGap(-1, Short.MAX_VALUE)));
        this.pack();
    }

    private void okBtnActionPerformed(ActionEvent evt) {
        this.dialogListener.okKlik(this.idField.getText());
    }

    private void cancelBtnActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void idFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.okBtn.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void okBtnKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.dialogListener.okKlik(this.idField.getText());
        } else if (evt.getKeyCode() == 39) {
            this.cancelBtn.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.idField.requestFocus();
            this.idField.selectAll();
        }
    }

    private void cancelBtnKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.dispose();
        } else if (evt.getKeyCode() == 27) {
            this.idField.requestFocus();
            this.idField.selectAll();
        } else if (evt.getKeyCode() == 37) {
            this.okBtn.requestFocus();
        }
    }
}

