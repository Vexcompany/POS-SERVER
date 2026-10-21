/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.LayoutStyle;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import minimart.engine.controller.Helper;
import minimart.engine.model.ShiftKasir;
import minimart.engine.service.ShiftKasirService;
import minimart.ui.listener.OpenedKasirListener;

public class OpenedKasir
extends JDialog {
    private JButton batalBtn;
    private JLabel dibukaLbl;
    private JLabel jLabel;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel4;
    private JButton lanjutkanBtn;
    private JLabel openingLbl;
    private JLabel statusLbl;
    private JFormattedTextField tanggalField;
    private ShiftKasir shiftKasir;
    private ShiftKasirService shiftKasirService = new ShiftKasirService();
    private OpenedKasirListener listener;
    private Helper helper = Helper.getInstance();

    public OpenedKasir(Frame parent, boolean modal) {
        super(parent, modal);
        this.initComponents();
        this.shiftKasir = this.shiftKasirService.getOpenedShiftKasir(this.helper.getKasir());
        this.dibukaLbl.setText(this.shiftKasir.getOpenedBy());
        this.tanggalField.setValue(this.shiftKasir.getOpening());
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        this.openingLbl.setText(formatter.format(this.shiftKasir.getOpening()));
        this.statusLbl.setText("AKTIF");
    }

    public void setOpenedKasirListener(OpenedKasirListener openedKasirListener) {
        this.listener = openedKasirListener;
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.jLabel = new JLabel();
        this.jLabel4 = new JLabel();
        this.dibukaLbl = new JLabel();
        this.statusLbl = new JLabel();
        this.lanjutkanBtn = new JButton();
        this.batalBtn = new JButton();
        this.tanggalField = new JFormattedTextField();
        this.openingLbl = new JLabel();
        this.setDefaultCloseOperation(2);
        this.jLabel1.setText("Dibuka Oleh");
        this.jLabel2.setText("Tanggal");
        this.jLabel.setText("Opening");
        this.jLabel4.setText("Status");
        this.dibukaLbl.setText("-");
        this.statusLbl.setText("-");
        this.lanjutkanBtn.setText("Lanjutkan");
        this.lanjutkanBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                OpenedKasir.this.lanjutkanBtnActionPerformed(evt);
            }
        });
        this.batalBtn.setText("Batal");
        this.batalBtn.setPreferredSize(new Dimension(73, 23));
        this.batalBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                OpenedKasir.this.batalBtnActionPerformed(evt);
            }
        });
        this.tanggalField.setEditable(false);
        this.tanggalField.setFormatterFactory(new DefaultFormatterFactory(new DateFormatter()));
        this.openingLbl.setText("-");
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addComponent(this.jLabel2).addComponent(this.jLabel).addComponent(this.jLabel4)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.dibukaLbl, -1, -1, Short.MAX_VALUE).addComponent(this.statusLbl, -1, -1, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.tanggalField, -1, 158, Short.MAX_VALUE).addComponent(this.openingLbl, -1, -1, Short.MAX_VALUE)).addGap(0, 0, Short.MAX_VALUE)))).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGap(0, 222, Short.MAX_VALUE).addComponent(this.batalBtn, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.lanjutkanBtn))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel1).addComponent(this.dibukaLbl)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.tanggalField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel).addComponent(this.openingLbl)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel4).addComponent(this.statusLbl)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.lanjutkanBtn).addComponent(this.batalBtn, -2, -1, -2)).addContainerGap()));
        this.pack();
    }

    private void lanjutkanBtnActionPerformed(ActionEvent evt) {
        this.listener.lanjutkan();
        this.dispose();
    }

    private void batalBtnActionPerformed(ActionEvent evt) {
        this.dispose();
    }
}

