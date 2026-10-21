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
import minimart.engine.model.PromoDiskonByJenis;
import minimart.engine.model.PromoDiskonByJenisDetail;
import minimart.engine.service.BarangService;
import minimart.engine.service.PromoDiskonByJenisDetailService;
import minimart.engine.service.PromoDiskonByJenisService;
import minimart.ui.listener.ConfirmationDialogListener;
import minimart.ui.listener.DefaultPanelListener;
import minimart.ui.listener.PromoHematDialogListener;
import minimart.ui.view.PromoDiskonByJenisDialog;

public class PromoDiskonByJenisPanel
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
    private JTextField kodeField;
    private JTable promoTable;
    private JButton simpanBtn;
    private JDateChooser startDateField;
    private String[] promoColumn = new String[]{"JENIS", "DISKON", "KHUSUS PELANGGAN"};
    private DefaultTableModel promoTableModel = new DefaultTableModel(new String[0][1], this.promoColumn){

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private PromoDiskonByJenisService promoDiskonByJenisService = new PromoDiskonByJenisService();
    private PromoDiskonByJenisDetailService promoDiskonByJenisDetailService = new PromoDiskonByJenisDetailService();
    private DefaultPanelListener defaultPanelListener;
    private int screenWidth;
    private int screenHeight;
    private Helper helper = Helper.getInstance();
    private boolean canGoThrough;
    private String status = "CREATE";
    private BarangService barangService = new BarangService();

    public PromoDiskonByJenisPanel() {
        this.initComponents();
        this.screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        this.screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.loadPromo();
        this.keteranganField.requestFocus();
    }

    private void loadPromo() {
        PromoDiskonByJenis promoDiskonByJenis = this.promoDiskonByJenisService.getPromoByJenis(this.kodeField.getText());
        if (promoDiskonByJenis != null) {
            this.status = "UPDATE";
            this.startDateField.setDate(promoDiskonByJenis.getTanggalMulai());
            this.endDateField.setDate(promoDiskonByJenis.getTanggalBerakhir());
            if (promoDiskonByJenis.getStatus().equals("AKTIF")) {
                this.aktifCheck.setSelected(true);
            } else if (promoDiskonByJenis.getStatus().equals("NONAKTIF")) {
                this.aktifCheck.setSelected(false);
            }
            this.keteranganField.setText(promoDiskonByJenis.getKeterangan());
            List<PromoDiskonByJenisDetail> promoDiskonByJenisDetails = this.promoDiskonByJenisDetailService.getPromoDiscJenisDetail(promoDiskonByJenis.getId() + "");
            for (PromoDiskonByJenisDetail ptd : promoDiskonByJenisDetails) {
                Object[] rowValue = new Object[]{ptd.getmJenisJenis(), Float.valueOf(ptd.getDisc()), ptd.getKhususPelanggan()};
                this.promoTableModel.addRow(rowValue);
            }
        } else {
            this.aktifCheck.setSelected(true);
            this.startDateField.setDate(new Date());
            this.endDateField.setDate(new Date());
            this.kodeField.setEditable(false);
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
        PromoDiskonByJenisDialog promoDiskonByJenisDialog = new PromoDiskonByJenisDialog(null, true, this.promoTableModel);
        promoDiskonByJenisDialog.addListener(new promoDiskonByJenisDialogEventHandler());
        int w = promoDiskonByJenisDialog.getWidth();
        int h = promoDiskonByJenisDialog.getHeight();
        promoDiskonByJenisDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
        promoDiskonByJenisDialog.setVisible(true);
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
        this.setPreferredSize(new Dimension(800, 600));
        this.baruBtn.setText("Hapus Semua Data");
        this.baruBtn.setPreferredSize(new Dimension(73, 23));
        this.baruBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PromoDiskonByJenisPanel.this.baruBtnActionPerformed(evt);
            }
        });
        this.simpanBtn.setText("Simpan");
        this.simpanBtn.setPreferredSize(new Dimension(73, 23));
        this.simpanBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PromoDiskonByJenisPanel.this.simpanBtnActionPerformed(evt);
            }
        });
        this.keluarBtn.setText("Keluar");
        this.keluarBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PromoDiskonByJenisPanel.this.keluarBtnActionPerformed(evt);
            }
        });
        this.jLabel1.setText("Kode Promo");
        this.jLabel2.setText("Tgl Promo");
        this.kodeField.setText("1");
        this.kodeField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PromoDiskonByJenisPanel.this.kodeFieldKeyPressed(evt);
            }
        });
        this.jLabel3.setText("s/d");
        this.aktifCheck.setText("Aktifkan Promo");
        this.jLabel4.setText("Keterangan");
        this.keteranganField.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PromoDiskonByJenisPanel.this.keteranganFieldKeyPressed(evt);
            }
        });
        this.promoTable.setModel(this.promoTableModel);
        this.promoTable.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PromoDiskonByJenisPanel.this.promoTableKeyPressed(evt);
            }
        });
        this.jScrollPane1.setViewportView(this.promoTable);
        this.detailBtn.setText("Detail");
        this.detailBtn.setPreferredSize(new Dimension(73, 23));
        this.detailBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent evt) {
                PromoDiskonByJenisPanel.this.detailBtnActionPerformed(evt);
            }
        });
        this.detailBtn.addKeyListener(new KeyAdapter(){

            @Override
            public void keyPressed(KeyEvent evt) {
                PromoDiskonByJenisPanel.this.detailBtnKeyPressed(evt);
            }
        });
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jSeparator1, GroupLayout.Alignment.TRAILING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.jLabel1).addComponent(this.jLabel2).addComponent(this.jLabel4)).addGap(28, 28, 28).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.kodeField, -2, 65, -2).addGroup(layout.createSequentialGroup().addComponent((Component)this.startDateField, -2, 165, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jLabel3).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent((Component)this.endDateField, -2, 165, -2)).addComponent(this.keteranganField, -2, 354, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 228, Short.MAX_VALUE).addComponent(this.aktifCheck, -2, 113, -2)).addGroup(layout.createSequentialGroup().addComponent(this.detailBtn, -2, -1, -2).addGap(0, 0, Short.MAX_VALUE)).addGroup(layout.createSequentialGroup().addComponent(this.baruBtn, -2, 161, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.simpanBtn, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, -1, Short.MAX_VALUE).addComponent(this.keluarBtn)).addComponent(this.jScrollPane1)).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.baruBtn, -2, -1, -2).addComponent(this.simpanBtn, -2, -1, -2).addComponent(this.keluarBtn)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jSeparator1, -2, 10, -2).addGap(2, 2, 2).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.kodeField, -2, -1, -2).addComponent(this.jLabel1)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent((Component)this.startDateField, -2, -1, -2).addComponent(this.jLabel3).addComponent((Component)this.endDateField, -2, -1, -2))).addComponent(this.jLabel2)).addGap(5, 5, 5).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.keteranganField, -2, -1, -2).addComponent(this.jLabel4))).addComponent(this.aktifCheck)).addGap(12, 12, 12).addComponent(this.detailBtn, -2, -1, -2).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.jScrollPane1, -2, 372, -2).addContainerGap(64, Short.MAX_VALUE)));
    }

    private void baruBtnActionPerformed(ActionEvent evt) {
        this.setDefault();
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
                PromoDiskonByJenisDialog promoDiskonByJenisDialog = new PromoDiskonByJenisDialog(null, true, this.promoTableModel);
                if (this.promoTable.getSelectedRow() != -1) {
                    promoDiskonByJenisDialog.loadJenisBarang(this.promoTableModel.getValueAt(this.promoTable.getSelectedRow(), 0).toString());
                    promoDiskonByJenisDialog.loadPromoData(this.promoTable.getSelectedRow());
                }
                promoDiskonByJenisDialog.addListener(new promoDiskonByJenisDialogEventHandler());
                int w = promoDiskonByJenisDialog.getWidth();
                int h = promoDiskonByJenisDialog.getHeight();
                promoDiskonByJenisDialog.setLocation((this.screenWidth - w) / 2, (this.screenHeight - h) / 2);
                promoDiskonByJenisDialog.setVisible(true);
            }
        }
    }

    private void kodeFieldKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == 10) {
            this.keteranganField.requestFocus();
            this.keteranganField.selectAll();
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
        PromoDiskonByJenis promoDiskonByJenis = new PromoDiskonByJenis();
        promoDiskonByJenis.setId(Integer.parseInt(this.kodeField.getText()));
        promoDiskonByJenis.setKaryawanId(this.helper.getKaryawan().getId());
        promoDiskonByJenis.setKeterangan(this.keteranganField.getText());
        if (this.aktifCheck.isSelected()) {
            promoDiskonByJenis.setStatus("AKTIF");
        } else {
            promoDiskonByJenis.setStatus("NONAKTIF");
        }
        promoDiskonByJenis.setTanggalMulai(this.startDateField.getDate());
        promoDiskonByJenis.setTanggalBerakhir(this.endDateField.getDate());
        int result = 0;
        if (this.status.equals("CREATE")) {
            result = this.promoDiskonByJenisService.createPromoDiscJenis(promoDiskonByJenis);
        } else if (this.status.equals("UPDATE")) {
            result = this.promoDiskonByJenisService.updatePromoDiscJenis(promoDiskonByJenis);
        }
        if (result == 1) {
            this.promoDiskonByJenisDetailService.deletePromoDiscJenisDetail("1");
            for (int a = 0; a < this.promoTableModel.getRowCount(); ++a) {
                PromoDiskonByJenisDetail promoDiskonByJenisDetail = new PromoDiskonByJenisDetail();
                promoDiskonByJenisDetail.setPromoDiscJenisId(promoDiskonByJenis.getId());
                promoDiskonByJenisDetail.setmJenisJenis(this.promoTableModel.getValueAt(a, 0).toString());
                promoDiskonByJenisDetail.setDisc(Float.parseFloat(this.promoTableModel.getValueAt(a, 1).toString()));
                promoDiskonByJenisDetail.setKhususPelanggan(this.promoTableModel.getValueAt(a, 2).toString());
                this.promoDiskonByJenisDetailService.createPromoDiscJenisDetail(promoDiskonByJenisDetail);
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
            PromoDiskonByJenisPanel.this.canGoThrough = true;
        }

        @Override
        public void noClick() {
            PromoDiskonByJenisPanel.this.canGoThrough = false;
        }
    }

    private class promoDiskonByJenisDialogEventHandler
    implements PromoHematDialogListener {
        private promoDiskonByJenisDialogEventHandler() {
        }

        @Override
        public void saveClick(Object[] rowValue) {
            int a;
            boolean newData = true;
            for (a = 0; a < PromoDiskonByJenisPanel.this.promoTableModel.getRowCount(); ++a) {
                if (!PromoDiskonByJenisPanel.this.promoTableModel.getValueAt(a, 0).equals(rowValue[0])) continue;
                newData = false;
                break;
            }
            if (newData) {
                PromoDiskonByJenisPanel.this.promoTableModel.addRow(rowValue);
            } else {
                PromoDiskonByJenisPanel.this.promoTableModel.removeRow(a);
                PromoDiskonByJenisPanel.this.promoTableModel.addRow(rowValue);
            }
        }
    }
}

