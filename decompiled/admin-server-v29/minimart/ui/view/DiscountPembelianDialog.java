/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import minimart.engine.controller.Helper;
import minimart.ui.listener.DiscountPembelianDialogListener;

public class DiscountPembelianDialog
extends JDialog {
    private JButton cancelButton;
    private JTextField discount1;
    private JFormattedTextField discountField1;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JTextField ppn;
    private JFormattedTextField ppnField;
    private JButton saveButton;
    private JFormattedTextField totalField;
    private JFormattedTextField totalFinalField;
    private DiscountPembelianDialogListener listener;
    private Helper helper = Helper.getInstance();

    public DiscountPembelianDialog(Frame parent, boolean modal) {
        super(parent, modal);
        this.initComponents();
        this.totalField.requestFocus();
        this.totalField.selectAll();
    }

    public void setData(long totalHarga, float disc, float ppnValue) {
        this.totalField.setValue(totalHarga);
        this.discount1.setText(disc + "");
        float totalDiscount = (float)totalHarga * (disc / 100.0f);
        this.discountField1.setValue(Float.valueOf(totalDiscount));
        this.ppn.setText(ppnValue + "");
        float totalPpn = ((float)totalHarga - totalDiscount) * (ppnValue / 100.0f);
        this.ppnField.setValue(Float.valueOf(totalPpn));
        this.totalFinalField.setValue(Float.valueOf((float)totalHarga - totalDiscount + totalPpn));
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.jLabel4 = new JLabel();
        this.saveButton = new JButton();
        this.cancelButton = new JButton();
        this.totalField = new JFormattedTextField();
        this.discountField1 = new JFormattedTextField();
        this.ppnField = new JFormattedTextField();
        this.discount1 = new JTextField();
        this.ppn = new JTextField();
        this.jLabel5 = new JLabel();
        this.totalFinalField = new JFormattedTextField();
        this.setDefaultCloseOperation(2);
        this.jLabel1.setText("Total Pembelian");
        this.jLabel2.setText("Discount [%]");
        this.jLabel4.setText("PPN [%]");
        this.saveButton.setText("Save");
        this.saveButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                DiscountPembelianDialog.this.saveButtonActionPerformed(evt);
            }
        });
        this.saveButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                DiscountPembelianDialog.this.saveButtonKeyPressed(evt);
            }
        });
        this.cancelButton.setText("Cancel");
        this.cancelButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                DiscountPembelianDialog.this.cancelButtonActionPerformed(evt);
            }
        });
        this.cancelButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                DiscountPembelianDialog.this.cancelButtonKeyPressed(evt);
            }
        });
        this.totalField.setEditable(false);
        this.totalField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getIntegerInstance())));
        this.totalField.setText("0");
        this.totalField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                DiscountPembelianDialog.this.totalFieldKeyPressed(evt);
            }
        });
        this.discountField1.setEditable(false);
        this.discountField1.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getIntegerInstance())));
        this.discountField1.setText("0");
        this.ppnField.setEditable(false);
        this.ppnField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getIntegerInstance())));
        this.ppnField.setText("0");
        this.discount1.setText("0");
        this.discount1.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                DiscountPembelianDialog.this.discount1KeyPressed(evt);
            }
        });
        this.ppn.setText("0");
        this.ppn.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                DiscountPembelianDialog.this.ppnKeyPressed(evt);
            }
        });
        this.jLabel5.setText("Total Akhir");
        this.totalFinalField.setEditable(false);
        this.totalFinalField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getIntegerInstance())));
        this.totalFinalField.setText("0");
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(24, 24, 24).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(this.saveButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.cancelButton)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addComponent(this.jLabel2).addComponent(this.jLabel4).addComponent(this.jLabel5)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.totalFinalField).addGroup(layout.createSequentialGroup().addComponent(this.totalField, -2, 107, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addComponent(this.discount1, -2, 56, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.discountField1)).addGroup(layout.createSequentialGroup().addComponent(this.ppn, -2, 55, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.ppnField))))).addGap(36, 36, 36)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.totalField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.discountField1, -2, -1, -2).addComponent(this.discount1, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel4).addComponent(this.ppnField, -2, -1, -2).addComponent(this.ppn, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel5).addComponent(this.totalFinalField, -2, -1, -2)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.saveButton).addComponent(this.cancelButton)).addContainerGap(20, Short.MAX_VALUE)));
        this.pack();
    }

    private void escapeClick() {
        this.discount1.requestFocus();
        this.discount1.selectAll();
    }

    private void saveClick() {
        this.saveButton.setEnabled(false);
        this.listener.saveClick(Float.valueOf(Float.parseFloat(this.discount1.getText())), Float.valueOf(Float.parseFloat(this.ppn.getText())), (Long)this.totalField.getValue());
    }

    private void discount1KeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (!this.helper.isFloat(this.discount1.getText())) {
                this.discount1.setBackground(Color.red);
            } else {
                this.discount1.setBackground(Color.white);
                float discountValue = Float.parseFloat(this.discount1.getText()) / 100.0f * (float)((Long)this.totalField.getValue()).longValue();
                this.discountField1.setValue(Float.valueOf(discountValue));
                this.totalFinalField.setValue(Float.valueOf((float)((Long)this.totalField.getValue()).longValue() - discountValue));
                this.ppn.requestFocus();
                this.ppn.selectAll();
            }
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void saveButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.saveClick();
        } else if (evt.getKeyCode() == 39) {
            this.cancelButton.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 38) {
            this.ppn.requestFocus();
            this.ppn.selectAll();
        }
    }

    private void ppnKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            if (!this.helper.isFloat(this.ppn.getText())) {
                this.ppn.setBackground(Color.red);
            } else {
                this.ppn.setBackground(Color.white);
                float ppnValue = 0.0f;
                ppnValue = Float.parseFloat(this.ppn.getText()) / 100.0f * (Float.parseFloat(this.totalField.getValue().toString()) - Float.parseFloat(this.discountField1.getValue().toString()));
                this.ppnField.setValue(Float.valueOf(ppnValue));
                this.totalFinalField.setValue(Float.valueOf((float)((Long)this.totalField.getValue()).longValue() - ((Float)this.discountField1.getValue()).floatValue() + ppnValue));
                this.saveButton.requestFocus();
            }
        } else if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 38) {
            this.discount1.requestFocus();
            this.discount1.selectAll();
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

    private void saveButtonActionPerformed(ActionEvent evt) {
        this.saveClick();
    }

    private void totalFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.discount1.requestFocus();
            this.discount1.selectAll();
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    public void addListener(DiscountPembelianDialogListener dialogListener) {
        this.listener = dialogListener;
    }
}

