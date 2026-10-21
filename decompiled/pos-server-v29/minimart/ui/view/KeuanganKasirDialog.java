/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.LayoutStyle;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import minimart.engine.controller.Helper;
import minimart.engine.service.BonusService;
import minimart.engine.service.KeuanganKasirService;

public class KeuanganKasirDialog
extends JDialog {
    long total = 0L;
    private JButton cancelButton;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel kasirField;
    private JFormattedTextField uangKasirField;
    private Helper helper = Helper.getInstance();
    private KeuanganKasirService keuanganKasirService = new KeuanganKasirService();
    private BonusService bonusService = new BonusService();

    public KeuanganKasirDialog(Frame parent, boolean modal) {
        super(parent, modal);
        this.initComponents();
        this.kasirField.setText(this.helper.getKaryawan().getNama() + " / " + this.helper.getKasir());
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        long keuanganKasir = this.keuanganKasirService.getKeuanganKasirByTanggal(format.format(new Date()), this.helper.getKasir());
        this.uangKasirField.setValue(keuanganKasir);
        this.cancelButton.requestFocus();
    }

    private void initComponents() {
        this.cancelButton = new JButton();
        this.jLabel4 = new JLabel();
        this.kasirField = new JLabel();
        this.jLabel5 = new JLabel();
        this.uangKasirField = new JFormattedTextField();
        this.setDefaultCloseOperation(2);
        this.setPreferredSize(new Dimension(350, 150));
        this.cancelButton.setText("Cancel");
        this.cancelButton.setPreferredSize(new Dimension(73, 23));
        this.cancelButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                KeuanganKasirDialog.this.cancelButtonActionPerformed(evt);
            }
        });
        this.cancelButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                KeuanganKasirDialog.this.cancelButtonKeyPressed(evt);
            }
        });
        this.jLabel4.setText("Kasir");
        this.jLabel5.setText("Uang Kasir");
        this.uangKasirField.setEditable(false);
        this.uangKasirField.setBackground(new Color(102, 255, 204));
        this.uangKasirField.setFormatterFactory(new DefaultFormatterFactory(new NumberFormatter(NumberFormat.getIntegerInstance())));
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(35, 35, 35).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.cancelButton, -2, -1, -2).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel4).addComponent(this.jLabel5)).addGap(35, 35, 35).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.uangKasirField).addComponent(this.kasirField, -1, 166, Short.MAX_VALUE)))).addContainerGap(83, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel4, GroupLayout.Alignment.TRAILING).addComponent(this.kasirField, GroupLayout.Alignment.TRAILING, -2, 22, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.uangKasirField, -2, -1, -2).addComponent(this.jLabel5)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.cancelButton, -2, -1, -2).addContainerGap(-1, Short.MAX_VALUE)));
        this.pack();
    }

    private void cancelButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10 || evt.getKeyCode() == 27) {
            this.dispose();
        }
    }

    private void cancelButtonActionPerformed(ActionEvent evt) {
        this.dispose();
    }
}

