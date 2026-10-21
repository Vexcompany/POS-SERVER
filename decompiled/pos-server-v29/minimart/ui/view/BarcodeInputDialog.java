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
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import minimart.engine.service.KonstantaService;
import minimart.ui.listener.BarcodeInputDialogListener;

public class BarcodeInputDialog
extends JDialog {
    private JTextField barcodeField;
    private JButton cancelButton;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JButton okButton;
    private BarcodeInputDialogListener listener;
    private KonstantaService konstantaService = new KonstantaService();
    private int satuan;
    private String auto = "NO";

    public BarcodeInputDialog(Frame parent, boolean modal, int satuan) {
        super(parent, modal);
        this.satuan = satuan;
        this.initComponents();
        this.setTitle("Barcode Barang");
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.barcodeField = new JTextField();
        this.okButton = new JButton();
        this.cancelButton = new JButton();
        this.jLabel2 = new JLabel();
        this.setDefaultCloseOperation(2);
        this.jLabel1.setText("Masukkan Barcode");
        this.barcodeField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                BarcodeInputDialog.this.barcodeFieldKeyPressed(evt);
            }
        });
        this.okButton.setText("Ok");
        this.okButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                BarcodeInputDialog.this.okButtonActionPerformed(evt);
            }
        });
        this.okButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                BarcodeInputDialog.this.okButtonKeyPressed(evt);
            }
        });
        this.cancelButton.setLabel("Cancel");
        this.cancelButton.setMaximumSize(new Dimension(73, 23));
        this.cancelButton.setPreferredSize(new Dimension(73, 23));
        this.cancelButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                BarcodeInputDialog.this.cancelButtonActionPerformed(evt);
            }
        });
        this.cancelButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                BarcodeInputDialog.this.cancelButtonKeyPressed(evt);
            }
        });
        this.jLabel2.setText("F1: Auto Generate");
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel1).addGap(18, 18, 18).addComponent(this.jLabel2)).addGroup(layout.createSequentialGroup().addComponent(this.barcodeField, -2, 206, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.okButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.cancelButton, -2, -1, -2))).addContainerGap(-1, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.jLabel2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.barcodeField, -2, -1, -2).addComponent(this.okButton).addComponent(this.cancelButton, -2, -1, -2)).addContainerGap(-1, Short.MAX_VALUE)));
        this.pack();
    }

    private void okButtonActionPerformed(ActionEvent evt) {
        this.listener.okClick(this.barcodeField.getText());
    }

    private void barcodeFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.okButton.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        } else if (evt.getKeyCode() == 112) {
            if (this.satuan == 1) {
                int barcode = Integer.parseInt(this.konstantaService.getConstantValue("barcode1"));
                this.barcodeField.setText(barcode + 1 + "");
            } else if (this.satuan == 2) {
                int barcode = Integer.parseInt(this.konstantaService.getConstantValue("barcode2"));
                this.barcodeField.setText(barcode + 1 + "");
            } else if (this.satuan == 3) {
                int barcode = Integer.parseInt(this.konstantaService.getConstantValue("barcode3"));
                this.barcodeField.setText(barcode + 1 + "");
            }
            this.auto = "YES";
        }
    }

    private void okButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (!this.barcodeField.getText().equals("")) {
                this.listener.okClick(this.barcodeField.getText());
                this.barcodeField.requestFocus();
                this.barcodeField.selectAll();
            } else {
                JOptionPane.showMessageDialog(null, "Kode Barcode Harus Diisi");
            }
        } else if (evt.getKeyCode() == 27 || evt.getKeyCode() == 37 || evt.getKeyCode() == 38) {
            this.barcodeField.requestFocus();
            this.barcodeField.selectAll();
        } else if (evt.getKeyCode() == 39) {
            this.cancelButton.requestFocus();
        }
    }

    private void cancelButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.dispose();
        } else if (evt.getKeyCode() == 27) {
            this.barcodeField.requestFocus();
            this.barcodeField.selectAll();
        } else if (evt.getKeyCode() == 37 || evt.getKeyCode() == 38) {
            this.okButton.requestFocus();
        }
    }

    private void cancelButtonActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    public void addBarcodeInputDialogListener(BarcodeInputDialogListener listener) {
        this.listener = listener;
    }
}

