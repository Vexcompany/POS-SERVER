/*
 * Decompiled with CFR 0.152.
 */
package minimart.ui.view;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.Date;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import minimart.engine.controller.Helper;
import minimart.ui.listener.NewKasirListener;

public class NewKasir
extends JDialog {
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JTextField kasirField;
    private JTextField namaField;
    private JFormattedTextField tanggalFIeld;
    private JButton tidakBtn;
    private JFormattedTextField waktuField;
    private JButton yaBtn;
    Helper helper = Helper.getInstance();
    private NewKasirListener newKasirListener;

    public NewKasir(Frame parent, boolean modal) {
        super(parent, modal);
        this.initComponents();
        this.namaField.setText(this.helper.getKaryawan().getNama());
        this.kasirField.setText(this.helper.getKasir() + "");
        this.tanggalFIeld.setValue(new Date());
        this.waktuField.setValue(new Date());
        this.yaBtn.requestFocus();
    }

    public void setListener(NewKasirListener newKasirListener) {
        this.newKasirListener = newKasirListener;
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.tidakBtn = new JButton();
        this.yaBtn = new JButton();
        this.jLabel2 = new JLabel();
        this.jLabel3 = new JLabel();
        this.jLabel4 = new JLabel();
        this.kasirField = new JTextField();
        this.namaField = new JTextField();
        this.tanggalFIeld = new JFormattedTextField();
        this.waktuField = new JFormattedTextField();
        this.setDefaultCloseOperation(2);
        this.jLabel1.setText("Shift Kasir Belum Aktif, Buka Kasir?");
        this.tidakBtn.setText("Tidak");
        this.tidakBtn.setPreferredSize(new Dimension(73, 23));
        this.tidakBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                NewKasir.this.tidakBtnActionPerformed(evt);
            }
        });
        this.yaBtn.setText("Ya");
        this.yaBtn.setPreferredSize(new Dimension(73, 23));
        this.yaBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                NewKasir.this.yaBtnActionPerformed(evt);
            }
        });
        this.jLabel2.setText("Nama");
        this.jLabel3.setText("No Kasir");
        this.jLabel4.setText("Jam Mulai");
        this.kasirField.setEditable(false);
        this.namaField.setEditable(false);
        this.tanggalFIeld.setEditable(false);
        this.tanggalFIeld.setFormatterFactory(new DefaultFormatterFactory(new DateFormatter()));
        this.waktuField.setEditable(false);
        this.waktuField.setFormatterFactory(new DefaultFormatterFactory(new DateFormatter(DateFormat.getTimeInstance())));
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addComponent(this.jLabel1).addGap(0, 0, Short.MAX_VALUE)).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel4).addComponent(this.jLabel3).addComponent(this.jLabel2)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(this.namaField, -2, 160, -2).addComponent(this.kasirField, GroupLayout.Alignment.LEADING, -2, 87, -2)).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.waktuField, GroupLayout.Alignment.LEADING, -1, 87, Short.MAX_VALUE).addComponent(this.tanggalFIeld, GroupLayout.Alignment.LEADING))).addGap(0, 77, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addGap(0, 0, Short.MAX_VALUE).addComponent(this.yaBtn, -2, -1, -2))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.tidakBtn, -2, -1, -2))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(this.jLabel1).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel2).addComponent(this.namaField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel3).addComponent(this.kasirField, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel4).addComponent(this.tanggalFIeld, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.waktuField, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.tidakBtn, -2, -1, -2).addComponent(this.yaBtn, -2, -1, -2)).addContainerGap()));
        this.pack();
    }

    private void tidakBtnActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void yaBtnActionPerformed(ActionEvent evt) {
        this.newKasirListener.yaListener();
        this.dispose();
    }
}

