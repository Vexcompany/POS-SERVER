/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view;

import java.awt.Color;
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
import javax.swing.LayoutStyle;
import minimart.ui.listener.ConfirmationDialogListener;

public class ConfirmationDialog
extends JDialog {
    private JLabel confirmText;
    private JButton noButton;
    private JButton yesButton;
    private ConfirmationDialogListener confirmationDialogListener;

    public ConfirmationDialog(Frame parent, boolean modal, String text) {
        super(parent, modal);
        this.initComponents();
        this.confirmText.setText(text);
        this.getContentPane().setBackground(Color.RED);
    }

    public void addListener(ConfirmationDialogListener confirmationDialogListener) {
        this.confirmationDialogListener = confirmationDialogListener;
    }

    private void initComponents() {
        this.confirmText = new JLabel();
        this.yesButton = new JButton();
        this.noButton = new JButton();
        this.setDefaultCloseOperation(2);
        this.setBackground(new Color(255, 0, 0));
        this.confirmText.setFont(new Font("Tahoma", 1, 14));
        this.confirmText.setForeground(new Color(255, 255, 255));
        this.confirmText.setHorizontalAlignment(0);
        this.confirmText.setText("confirmText");
        this.yesButton.setText("Ya");
        this.yesButton.setPreferredSize(new Dimension(74, 23));
        this.yesButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ConfirmationDialog.this.yesButtonActionPerformed(evt);
            }
        });
        this.yesButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ConfirmationDialog.this.yesButtonKeyPressed(evt);
            }
        });
        this.noButton.setText("Tidak");
        this.noButton.setPreferredSize(new Dimension(74, 23));
        this.noButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ConfirmationDialog.this.noButtonActionPerformed(evt);
            }
        });
        this.noButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ConfirmationDialog.this.noButtonKeyPressed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(0, 333, Short.MAX_VALUE).addComponent(this.noButton, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.yesButton, -2, -1, -2)).addComponent(this.confirmText, -1, -1, Short.MAX_VALUE)).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap(-1, Short.MAX_VALUE).addComponent(this.confirmText, -2, 33, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.yesButton, -2, -1, -2).addComponent(this.noButton, -2, -1, -2)).addGap(5, 5, 5)));
        this.pack();
    }

    private void noButtonActionPerformed(ActionEvent evt) {
        this.confirmationDialogListener.noClick();
        this.dispose();
    }

    private void noButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.confirmationDialogListener.noClick();
            this.dispose();
        } else if (evt.getKeyCode() == 39 || evt.getKeyCode() == 40) {
            this.yesButton.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.confirmationDialogListener.noClick();
            this.dispose();
        }
    }

    private void yesButtonActionPerformed(ActionEvent evt) {
        this.confirmationDialogListener.yesClick();
        this.dispose();
    }

    private void yesButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.confirmationDialogListener.yesClick();
            this.dispose();
        } else if (evt.getKeyCode() == 37 || evt.getKeyCode() == 38) {
            this.noButton.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.confirmationDialogListener.noClick();
            this.dispose();
        }
    }
}

