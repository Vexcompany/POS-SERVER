/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.toedter.calendar.JDateChooser
 */
package minimart.ui.view.report;

import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.LayoutStyle;
import javax.swing.text.DocumentFilter;
import minimart.engine.component.UppercaseDocumentFilter;
import minimart.engine.service.SupplierService;
import minimart.ui.listener.ReportPenerimaanBsListener;

public class ReportPenerimaanBsDialog
extends JDialog {
    private JDateChooser endDateField;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JButton keluarButton;
    private JComboBox keteranganCmb;
    private JComboBox lokasiCmb;
    private JDateChooser startDateField;
    private JButton tampilkanButton;
    private ReportPenerimaanBsListener reportPenerimaanBsListener;
    private int screenWidth;
    private int screenHeight;
    private DocumentFilter filter = new UppercaseDocumentFilter();
    private SupplierService supplierService = new SupplierService();

    public ReportPenerimaanBsDialog(Frame parent, boolean modal) {
        super(parent, modal);
        this.initComponents();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.startDateField.setDate(new Date());
        this.endDateField.setDate(new Date());
        this.startDateField.setFocusCycleRoot(false);
        this.setTitle("Report Entry BS");
    }

    public void addListener(ReportPenerimaanBsListener reportPenerimaanBsListener) {
        this.reportPenerimaanBsListener = reportPenerimaanBsListener;
    }

    private void initComponents() {
        this.jLabel1 = new JLabel();
        this.startDateField = new JDateChooser();
        this.jLabel2 = new JLabel();
        this.endDateField = new JDateChooser();
        this.tampilkanButton = new JButton();
        this.keluarButton = new JButton();
        this.jLabel4 = new JLabel();
        this.keteranganCmb = new JComboBox();
        this.jLabel5 = new JLabel();
        this.lokasiCmb = new JComboBox();
        this.setDefaultCloseOperation(2);
        this.jLabel1.setText("Dari Tanggal");
        this.jLabel2.setText("Sampai Tanggal");
        this.tampilkanButton.setText("Tampilkan");
        this.tampilkanButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPenerimaanBsDialog.this.tampilkanButtonActionPerformed(evt);
            }
        });
        this.tampilkanButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportPenerimaanBsDialog.this.tampilkanButtonKeyPressed(evt);
            }
        });
        this.keluarButton.setText("Keluar");
        this.keluarButton.setPreferredSize(new Dimension(79, 23));
        this.keluarButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                ReportPenerimaanBsDialog.this.keluarButtonActionPerformed(evt);
            }
        });
        this.keluarButton.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportPenerimaanBsDialog.this.keluarButtonKeyPressed(evt);
            }
        });
        this.jLabel4.setText("Lokasi");
        this.keteranganCmb.setModel(new DefaultComboBoxModel<String>(new String[]{"SEMUA", "SUDAH DIPROSES", "BELUM DIPROSES"}));
        this.jLabel5.setText("Keterangan");
        this.lokasiCmb.setModel(new DefaultComboBoxModel<String>(new String[]{"SEMUA", "GUDANG", "TOKO"}));
        this.lokasiCmb.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                ReportPenerimaanBsDialog.this.lokasiCmbKeyPressed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(31, 31, 31).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGap(53, 53, 53).addComponent(this.tampilkanButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.keluarButton, -2, -1, -2)).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel2, GroupLayout.Alignment.TRAILING).addComponent(this.jLabel1)).addComponent(this.jLabel4).addComponent(this.jLabel5)).addGap(11, 11, 11).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addComponent(this.keteranganCmb, 0, 146, Short.MAX_VALUE).addComponent((Component)this.endDateField, -1, 146, Short.MAX_VALUE).addComponent((Component)this.startDateField, -1, 146, Short.MAX_VALUE).addComponent(this.lokasiCmb, 0, -1, Short.MAX_VALUE)))).addContainerGap(36, Short.MAX_VALUE)));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.jLabel4).addComponent(this.lokasiCmb, -2, -1, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.keteranganCmb, -2, -1, -2).addComponent(this.jLabel5)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent((Component)this.startDateField, -2, -1, -2).addComponent(this.jLabel1)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent((Component)this.endDateField, -2, -1, -2).addComponent(this.jLabel2)).addGap(11, 11, 11).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.tampilkanButton).addComponent(this.keluarButton, -2, -1, -2)).addContainerGap(-1, Short.MAX_VALUE)));
        this.pack();
    }

    private void escapeClick() {
        this.lokasiCmb.requestFocus();
    }

    private void tampilkanButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 38) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 39) {
            this.keluarButton.requestFocus();
        } else if (evt.getKeyCode() == 10) {
            this.reportPenerimaanBsListener.tampilkanClick(this.startDateField.getDate(), this.endDateField.getDate(), this.lokasiCmb.getSelectedItem().toString(), this.keteranganCmb.getSelectedItem().toString());
            this.dispose();
        }
    }

    private void keluarButtonKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 27) {
            this.escapeClick();
        } else if (evt.getKeyCode() == 37) {
            this.keluarButton.requestFocus();
        } else if (evt.getKeyCode() == 10) {
            this.dispose();
        }
    }

    private void tampilkanButtonActionPerformed(ActionEvent evt) {
        this.reportPenerimaanBsListener.tampilkanClick(this.startDateField.getDate(), this.endDateField.getDate(), this.lokasiCmb.getSelectedItem().toString(), this.keteranganCmb.getSelectedItem().toString());
        this.dispose();
    }

    private void keluarButtonActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    private void lokasiCmbKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.keteranganCmb.requestFocus();
        } else if (evt.getKeyCode() == 27) {
            this.dispose();
        }
    }
}

