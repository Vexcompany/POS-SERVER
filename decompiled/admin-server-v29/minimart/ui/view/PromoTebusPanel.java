/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.toedter.calendar.JDateChooser
 */
package minimart.ui.view;

import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import minimart.engine.controller.Helper;
import minimart.engine.model.PromoTebus;
import minimart.engine.model.PromoTebusDetail;
import minimart.engine.service.BarangService;
import minimart.engine.service.PromoTebusDetailService;
import minimart.engine.service.PromoTebusService;
import minimart.ui.listener.ConfirmationDialogListener;
import minimart.ui.listener.DefaultPanelListener;
import minimart.ui.listener.PromoHematDialogListener;
import minimart.ui.view.PromoTebusDialog;

public class PromoTebusPanel
extends JPanel {
    private JCheckBox aktifCheck;
    private JButton baruBtn;
    private JButton detailBtn;
    private JDateChooser endDateField;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JScrollPane jScrollPane1;
    private JSeparator jSeparator1;
    private JButton keluarBtn;
    private JTextField keteranganField;
    private JLabel keteranganLbl;
    private JTextField kodeField;
    private JTable promoTable;
    private JButton simpanBtn;
    private JDateChooser startDateField;
    private String[] promoColumn = new String[]{"ID BARANG", "NAMA BARANG", "JUMLAH", "SATUAN", "PROMO BARANG ID", "NAMA BARANG", "DISCOUNT", "SATUAN PROMO"};
    private DefaultTableModel promoTableModel = new DefaultTableModel(new String[0][1], this.promoColumn){

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private PromoTebusService promoTebusService = new PromoTebusService();
    private PromoTebusDetailService promoTebusDetailService = new PromoTebusDetailService();
    private DefaultPanelListener defaultPanelListener;
    private int screenWidth;
    private int screenHeight;
    private Helper helper = Helper.getInstance();
    private boolean canGoThrough;
    private String status = "CREATE";
    private BarangService barangService = new BarangService();

    public PromoTebusPanel() {
        this.initComponents();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.keteranganField.requestFocus();
    }

    private void loadPromoHemat() {
        this.setDefault();
        PromoTebus promoTebus = this.promoTebusService.getPromoTebus(this.kodeField.getText());
        if (promoTebus != null) {
            this.status = "UPDATE";
            this.startDateField.setDate(promoTebus.getTanggalMulai());
            this.endDateField.setDate(promoTebus.getTanggalBerakhir());
            if (promoTebus.getStatus().equals("AKTIF")) {
                this.aktifCheck.setSelected(true);
            } else if (promoTebus.getStatus().equals("NONAKTIF")) {
                this.aktifCheck.setSelected(false);
            }
            this.keteranganField.setText(promoTebus.getKeterangan());
            List<PromoTebusDetail> promoTebusDetails = this.promoTebusDetailService.getPromoTebusDetailByPromoTebusId(promoTebus.getId() + "");
            for (PromoTebusDetail ptd : promoTebusDetails) {
                Object[] rowValue = new Object[]{ptd.getBarangId(), this.barangService.getBarang(ptd.getBarangId()).getNama(), Float.valueOf(ptd.getJumlah()), ptd.getSatuan(), ptd.getPromoBarangId(), this.barangService.getBarang(ptd.getPromoBarangId()).getNama(), ptd.getDiscount(), ptd.getSatuanPromo()};
                this.promoTableModel.addRow(rowValue);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Data Promo Tidak Ditemukan");
        }
    }

    private void setDefault() {
        this.startDateField.setDate(new Date());
        this.endDateField.setDate(new Date());
        this.keteranganField.setText("");
        this.clearTable();
        this.aktifCheck.setSelected(true);
    }

    private void detailClick() {
        this.promoTable.requestFocus();
        PromoTebusDialog promoTebusDialog = new PromoTebusDialog(null, true, this.promoTableModel, this.startDateField.getDate(), this.endDateField.getDate(), this.kodeField.getText());
        promoTebusDialog.addListener(new promoHematDialogEventHandler());
        int w = promoTebusDialog.getWidth();
        int h = promoTebusDialog.getHeight();
        promoTebusDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
        promoTebusDialog.setVisible(true);
    }

    private void initComponents() {
        this.baruBtn = new JButton();
        this.simpanBtn = new JButton();
        this.keluarBtn = new JButton();
        this.jLabel1 = new JLabel();
        this.jLabel2 = new JLabel();
        this.kodeField = new JTextField();
        this.startDateField = new JDateChooser();
        this.jLabel3 = new JLabel();
        this.endDateField = new JDateChooser();
        this.aktifCheck = new JCheckBox();
        this.jSeparator1 = new JSeparator();
        this.jLabel4 = new JLabel();
        this.keteranganField = new JTextField();
        this.jScrollPane1 = new JScrollPane();
        this.promoTable = new JTable();
        this.detailBtn = new JButton();
        this.keteranganLbl = new JLabel();
        this.setPreferredSize(new Dimension(800, 600));
        this.baruBtn.setText("Baru");
        this.baruBtn.setPreferredSize(new Dimension(73, 23));
        this.baruBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PromoTebusPanel.this.baruBtnActionPerformed(evt);
            }
        });
        this.simpanBtn.setText("Simpan");
        this.simpanBtn.setPreferredSize(new Dimension(73, 23));
        this.simpanBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PromoTebusPanel.this.simpanBtnActionPerformed(evt);
            }
        });
        this.keluarBtn.setText("Keluar");
        this.keluarBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PromoTebusPanel.this.keluarBtnActionPerformed(evt);
            }
        });
        this.jLabel1.setText("Kode Promo");
        this.jLabel2.setText("Tgl Promo");
        this.kodeField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PromoTebusPanel.this.kodeFieldKeyPressed(evt);
            }
        });
        this.jLabel3.setText("s/d");
        this.aktifCheck.setText("Aktifkan Promo");
        this.jLabel4.setText("Keterangan");
        this.keteranganField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PromoTebusPanel.this.keteranganFieldKeyPressed(evt);
            }
        });
        this.promoTable.setModel(this.promoTableModel);
        this.promoTable.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PromoTebusPanel.this.promoTableKeyPressed(evt);
            }
        });
        this.jScrollPane1.setViewportView(this.promoTable);
        this.detailBtn.setText("Detail");
        this.detailBtn.setPreferredSize(new Dimension(73, 23));
        this.detailBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PromoTebusPanel.this.detailBtnActionPerformed(evt);
            }
        });
        this.detailBtn.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PromoTebusPanel.this.detailBtnKeyPressed(evt);
            }
        });
        this.keteranganLbl.setText("-");
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jSeparator1, GroupLayout.Alignment.TRAILING).addGroup(layout.createSequentialGroup().addComponent(this.baruBtn, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.simpanBtn, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.aktifCheck, -2, 113, -2).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.keteranganLbl, -2, 128, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.keluarBtn)).addComponent(this.jScrollPane1, -1, 780, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addComponent(this.jLabel2).addComponent(this.jLabel4)).addGap(28, 28, 28).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.keteranganField, -2, 354, -2).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false).addComponent(this.kodeField, GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addComponent((Component)this.startDateField, -2, 165, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel3))).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent((Component)this.endDateField, -2, 165, -2)))).addComponent(this.detailBtn, -2, -1, -2)).addGap(0, 0, Short.MAX_VALUE))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.baruBtn, -2, -1, -2).addComponent(this.simpanBtn, -2, -1, -2).addComponent(this.keluarBtn).addComponent(this.aktifCheck).addComponent(this.keteranganLbl)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jSeparator1, -2, 10, -2).addGap(2, 2, 2).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.kodeField, -2, -1, -2).addComponent(this.jLabel1)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent((Component)this.startDateField, -2, -1, -2).addComponent(this.jLabel3).addComponent((Component)this.endDateField, -2, -1, -2))).addComponent(this.jLabel2)).addGap(5, 5, 5).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.keteranganField, -2, -1, -2).addComponent(this.jLabel4)).addGap(12, 12, 12).addComponent(this.detailBtn, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane1, -2, 372, -2).addContainerGap(64, Short.MAX_VALUE)));
    }

    private void baruBtnActionPerformed(ActionEvent evt) {
        this.setDefault();
        this.status = "CREATE";
        this.keteranganLbl.setText(this.status);
        this.kodeField.setText(Helper.getInstance().generateCodeGeneral(this.promoTebusService.getLastId()));
        this.kodeField.requestFocus();
    }

    private void keluarBtnActionPerformed(ActionEvent evt) {
        this.defaultPanelListener.exitClick();
    }

    private void detailBtnActionPerformed(ActionEvent evt) {
        if (this.kodeField.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Tentukan Kode Promo Terlebih Dahulu");
        } else {
            this.detailClick();
        }
    }

    private void promoTableKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 127) {
            int a;
            if (this.promoTable.getSelectedRow() != -1 && (a = JOptionPane.showConfirmDialog(null, "Hapus Data Ini?", "Delete Data", 0, 3)) == 0) {
                this.promoTableModel.removeRow(this.promoTable.getSelectedRow());
            }
        } else if (evt.getKeyCode() == 10) {
            if (this.kodeField.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Tentukan Kode Promo Terlebih Dahulu");
            } else {
                PromoTebusDialog promoTebusDialog = new PromoTebusDialog(null, true, this.promoTableModel, this.startDateField.getDate(), this.endDateField.getDate(), this.kodeField.getText());
                if (this.promoTable.getSelectedRow() != -1) {
                    promoTebusDialog.loadKodeBarang(this.promoTableModel.getValueAt(this.promoTable.getSelectedRow(), 0).toString());
                    promoTebusDialog.loadPromoBarang(this.promoTableModel.getValueAt(this.promoTable.getSelectedRow(), 4).toString());
                    promoTebusDialog.loadPromoData(this.promoTable.getSelectedRow());
                }
                promoTebusDialog.addListener(new promoHematDialogEventHandler());
                int w = promoTebusDialog.getWidth();
                int h = promoTebusDialog.getHeight();
                promoTebusDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                promoTebusDialog.setVisible(true);
            }
        }
    }

    private void kodeFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.loadPromoHemat();
            this.keteranganLbl.setText(this.status);
        }
    }

    private void keteranganFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.detailBtn.requestFocus();
        }
    }

    private void detailBtnKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.detailClick();
        }
    }

    private void simpanBtnActionPerformed(ActionEvent evt) {
        PromoTebus promoTebus = new PromoTebus();
        promoTebus.setId(this.kodeField.getText());
        promoTebus.setKeterangan(this.keteranganField.getText());
        if (this.aktifCheck.isSelected()) {
            promoTebus.setStatus("AKTIF");
        } else {
            promoTebus.setStatus("NONAKTIF");
        }
        promoTebus.setTanggalMulai(this.startDateField.getDate());
        promoTebus.setTanggalBerakhir(this.endDateField.getDate());
        promoTebus.setCreated(new Date());
        int result = 0;
        if (this.status.equals("CREATE")) {
            result = this.promoTebusService.createPromoTebus(promoTebus);
        } else if (this.status.equals("UPDATE")) {
            result = this.promoTebusService.updatePromotebus(promoTebus);
        }
        if (result == 1) {
            this.promoTebusDetailService.deletePromoTebusDetailByPromoTebusId(this.kodeField.getText());
            for (int a = 0; a < this.promoTableModel.getRowCount(); ++a) {
                PromoTebusDetail promoTebusDetail = new PromoTebusDetail();
                promoTebusDetail.setPromoTebusId(promoTebus.getId());
                promoTebusDetail.setBarangId(this.promoTableModel.getValueAt(a, 0).toString());
                promoTebusDetail.setJumlah(Float.parseFloat(this.promoTableModel.getValueAt(a, 2).toString()));
                promoTebusDetail.setSatuan(this.promoTableModel.getValueAt(a, 3).toString());
                promoTebusDetail.setPromoBarangId(this.promoTableModel.getValueAt(a, 4).toString());
                promoTebusDetail.setDiscount(Long.parseLong(this.promoTableModel.getValueAt(a, 6).toString()));
                promoTebusDetail.setSatuanPromo(this.promoTableModel.getValueAt(a, 7).toString());
                this.promoTebusDetailService.createPromoTebusDetail(promoTebusDetail);
            }
            JOptionPane.showMessageDialog(null, "Penyimpanan Data Promo Berhasil");
        } else {
            JOptionPane.showMessageDialog(null, "Penyimpanan Data Gagal");
        }
    }

    private void clearTable() {
        int size = this.promoTableModel.getRowCount();
        for (int a = 0; a < size; ++a) {
            this.promoTableModel.removeRow(0);
        }
    }

    public void addListener(DefaultPanelListener listener) {
        this.defaultPanelListener = listener;
    }

    private class confirmDialogEventHandler
    implements ConfirmationDialogListener {
        private confirmDialogEventHandler() {
        }

        @Override
        public void yesClick() {
            PromoTebusPanel.this.canGoThrough = true;
        }

        @Override
        public void noClick() {
            PromoTebusPanel.this.canGoThrough = false;
        }
    }

    private class promoHematDialogEventHandler
    implements PromoHematDialogListener {
        private promoHematDialogEventHandler() {
        }

        @Override
        public void saveClick(Object[] rowValue) {
            int a;
            boolean newData = true;
            for (a = 0; a < PromoTebusPanel.this.promoTableModel.getRowCount(); ++a) {
                if (!PromoTebusPanel.this.promoTableModel.getValueAt(a, 0).equals(rowValue[0])) continue;
                newData = false;
                break;
            }
            if (newData) {
                PromoTebusPanel.this.promoTableModel.addRow(rowValue);
            } else {
                PromoTebusPanel.this.promoTableModel.removeRow(a);
                PromoTebusPanel.this.promoTableModel.addRow(rowValue);
            }
        }
    }
}

